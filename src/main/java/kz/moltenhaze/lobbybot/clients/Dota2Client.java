package kz.moltenhaze.lobbybot.clients;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import in.dragonbra.javasteam.base.*;
import in.dragonbra.javasteam.enums.EMsg;
import in.dragonbra.javasteam.protobufs.steamclient.SteammessagesClientserver2.CMsgClientPlayingSessionState;
import in.dragonbra.javasteam.steam.handlers.ClientMsgHandler;
import in.dragonbra.javasteam.steam.handlers.steamgamecoordinator.SteamGameCoordinator;
import in.dragonbra.javasteam.steam.handlers.steamuser.callback.LoggedOnCallback;
import in.dragonbra.javasteam.steam.steamclient.callbacks.DisconnectedCallback;
import in.dragonbra.javasteam.steam.steamclient.configuration.SteamConfiguration;
import in.dragonbra.javasteam.util.MsgUtil;
import in.dragonbra.javasteam.util.event.ScheduledFunction;
import in.dragonbra.javasteam.util.log.LogManager;
import in.dragonbra.javasteam.util.log.Logger;
import kz.moltenhaze.lobbybot.callbacks.common.ConnectionStatusCallback;
import kz.moltenhaze.lobbybot.callbacks.common.NotReadyCallback;
import kz.moltenhaze.lobbybot.callbacks.common.ReadyCallback;
import kz.moltenhaze.lobbybot.handlers.dota2.*;
import kz.moltenhaze.lobbybot.handlers.steam.IGCMsgHandler;
import kz.moltenhaze.lobbybot.models.SteamUser;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTAWelcome;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTAWelcome.CExtraMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommon.EDOTAGCSessionNeed;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.ESourceEngine;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.PartnerAccountType;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgClientHello;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgConnectionStatus;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgClientWelcome;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.GCConnectionStatus;
import kz.moltenhaze.proto.dota.Gcsystemmsgs.EGCBaseClientMsg;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class Dota2Client extends CommonSteamClient implements IGCMsgHandler {
    private static final Logger logger = LogManager.getLogger(Dota2Client.class);
    public static final int APP_ID = 570;

    @Getter
    private boolean ready = false;

    private GCConnectionStatus connectionStatus = GCConnectionStatus.GCConnectionStatus_NO_SESSION;
    private SteamConfiguration steamConfiguration;

    @Getter
    private Dota2GameCoordinator gameCoordinator;

    private Dota2SharedObjects dota2SharedObjects;

    @Getter
    private Dota2Chat chatHandler;

    private SteamUser steamUser;

    @Getter
    private Dota2Lobby lobbyHandler;

    @Getter
    private Dota2Match matchHandler;

    private final Map<Integer, Consumer<IPacketGCMsg>> dispatchMap;
    private final ScheduledFunction welcomeFunc;

    public Dota2Client(String username, String password) {
        super(username, password);

        dispatchMap = new HashMap<>();
        dispatchMap.put(EGCBaseClientMsg.k_EMsgGCClientWelcome_VALUE, this::handleWelcome);
        dispatchMap.put(EGCBaseClientMsg.k_EMsgGCClientConnectionStatus_VALUE, this::handleStatus);
        gameCoordinator.getDispatchMap().put(EMsg.ClientPlayingSessionState, this::handlePlaySessState);

        welcomeFunc = new ScheduledFunction(() -> {
            if (!ready)
                greet();
        }, 10000);
    }

    protected void init() {
        super.init();

        SteamGameCoordinator steamGameCoordinator = getSteamClient().getHandler(SteamGameCoordinator.class);

        if (steamGameCoordinator != null) {
            removeHandler(steamGameCoordinator);
            log.info("Default SteamGameCoordinator was removed");
        }

        addHandler(gameCoordinator = new Dota2GameCoordinator());
        addHandler(steamUser = new SteamUser());
        gameCoordinator.addDota2Handler(dota2SharedObjects = new Dota2SharedObjects(), this);
        gameCoordinator.addDota2Handler(lobbyHandler = new Dota2Lobby(), this);
        gameCoordinator.addDota2Handler(chatHandler = new Dota2Chat(), this);
        gameCoordinator.addDota2Handler(matchHandler = new Dota2Match(), this);
        gameCoordinator.addHandler(this);
    }


    @Override
    public void handleGCMsg(IPacketGCMsg packetGCMsg) {
        log.debug("handleMsg: {}", packetGCMsg.getMsgType());
        Consumer<IPacketGCMsg> dispatcher = dispatchMap.get(packetGCMsg.getMsgType());

        if (dispatcher != null) {
            dispatcher.accept(packetGCMsg);
        }
    }

    protected void addHandler(ClientMsgHandler handler) {
        log.debug("Adding {} handler to the SteamClient list of handlers", handler.getClass());
        getSteamClient().addHandler(handler);
    }

    protected void removeHandler(ClientMsgHandler handler) {
        log.debug("Removing {} handler from the SteamClient list of handlers", handler.getClass());
        getSteamClient().removeHandler(handler);
    }

    public void handleWelcome(IPacketGCMsg msg) {
        try {
            log.debug("handleWelcome :{}", msg.getMsgType());

            ClientGCMsgProtobuf<CMsgClientWelcome.Builder> welcome = new ClientGCMsgProtobuf<>(CMsgClientWelcome.class,
                    msg);
            CMsgDOTAWelcome dotaWelcome = CMsgDOTAWelcome.parseFrom(welcome.getBody().getGameData());

            setConnectionStatus(GCConnectionStatus.GCConnectionStatus_HAVE_SESSION);

            for (CExtraMsg extraMsg : dotaWelcome.getExtraMessagesList()) {
                processGCMessage(extraMsg.getId(), extraMsg.getContents());
            }
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleStatus(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgConnectionStatus.Builder> status = new ClientGCMsgProtobuf<>(CMsgConnectionStatus.class,
               msg);
        log.debug("handleStatus {}", status.getBody());

        if (GCConnectionStatus.GCConnectionStatus_NO_SESSION_IN_LOGON_QUEUE.equals(status.getBody().getStatus())) {
            log.debug("QueuePosition/QueueSize/WaitSeconds/EstimatedWaitSecondsRemaining: {}/{}/{}/{}",
                    status.getBody().getQueuePosition(),
                    status.getBody().getQueueSize(), status.getBody().getWaitSeconds(),
                    status.getBody().getEstimatedWaitSecondsRemaining());
        }

        setConnectionStatus(status.getBody().getStatus());
    }

    private void handlePlaySessState(IPacketMsg msg) {
        ClientMsgProtobuf<CMsgClientPlayingSessionState.Builder> sessionState = new ClientMsgProtobuf<>(
                CMsgClientPlayingSessionState.class, msg);
        log.debug("handlePlaySessState: {}/{}", sessionState.getBody().getPlayingApp(),
                sessionState.getBody().getPlayingBlocked());

        if (ready && sessionState.getBody().getPlayingApp() != APP_ID) {
            setConnectionStatus(GCConnectionStatus.GCConnectionStatus_NO_SESSION);
        }
    }


    public void processGCMessage(int id, ByteString contents) {
        IPacketGCMsg packetGCMsg = getPacketGCMsg(id, contents.toByteArray());
        gameCoordinator.handleGCMsg(packetGCMsg);
    }

    private static IPacketGCMsg getPacketGCMsg(int eMsg, byte[] data) {
        int realEMsg = MsgUtil.getGCMsg(eMsg);

        if (MsgUtil.isProtoBuf(eMsg)) {
            return new PacketClientGCMsgProtobuf(realEMsg, data);
        } else {
            return new PacketClientGCMsg(realEMsg, data);
        }
    }

    private void setConnectionStatus(GCConnectionStatus gcConnectionStatus) {
        GCConnectionStatus prevStatus = this.connectionStatus;
        this.connectionStatus = gcConnectionStatus;

        if (!this.connectionStatus.equals(prevStatus)) {
            postCallback(new ConnectionStatusCallback(gcConnectionStatus));
        }

        if (gcConnectionStatus.equals(GCConnectionStatus.GCConnectionStatus_HAVE_SESSION) && !ready) {
            ready = true;
            postCallback(new ReadyCallback());
        } else if (!gcConnectionStatus.equals(GCConnectionStatus.GCConnectionStatus_HAVE_SESSION) && ready) {
            ready = false;
            postCallback(new NotReadyCallback());
        }

        log.info("Dota2Client {}", ready ? "ready" : "not ready");
    }

    public void greet() {
        log.debug("Sending hello request to Dota2 GC");
        ClientGCMsgProtobuf<CMsgClientHello.Builder> hello = new ClientGCMsgProtobuf<>(CMsgClientHello.class,
                EGCBaseClientMsg.k_EMsgGCClientHello_VALUE);

        hello.getBody().setClientSessionNeed(EDOTAGCSessionNeed.k_EDOTAGCSessionNeed_UserInUINeverConnected_VALUE);
        hello.getBody().setClientLauncher(PartnerAccountType.PARTNER_NONE);
        hello.getBody().setEngine(ESourceEngine.k_ESE_Source2);
        hello.getBody().setSecretKey("");
        gameCoordinator.send(hello);
    }

    public void launch() {
        if (GCConnectionStatus.GCConnectionStatus_NO_SESSION_IN_LOGON_QUEUE.equals(connectionStatus)) {
            log.info("Already in logon queue");
        } else if (!logged) {
            log.info("Not logged");
        } else if (!GCConnectionStatus.GCConnectionStatus_HAVE_SESSION.equals(connectionStatus)) {
            log.info("Launching game coordinator");
            steamUser.setCurrentGamePlayed(APP_ID);
            welcomeFunc.start();
        } else {
            log.debug("DOTA is already online");
        }
    }

    protected void onLoggedOn(LoggedOnCallback callback) {
        super.onLoggedOn(callback);
        launch();
    }

    public void stop() {
        welcomeFunc.stop();
        steamUser.getCurrentGamesPlayed().remove(APP_ID);
        setConnectionStatus(GCConnectionStatus.GCConnectionStatus_NO_SESSION);
    }

    public void disconnect() {
        stop();
        super.disconnect();
    }

    @Override
    public void onDisconnected(DisconnectedCallback callback) {
        super.onDisconnected(callback);
    }

    private void handleDisconnect() {
        log.debug("handleDisconnect: {}", welcomeFunc);
        stop();
    }

}
