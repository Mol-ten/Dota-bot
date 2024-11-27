package kz.moltenhaze.lobbybot.handlers.dota2;

import com.google.protobuf.ByteString;
import in.dragonbra.javasteam.base.ClientMsgProtobuf;
import in.dragonbra.javasteam.base.IClientGCMsg;
import in.dragonbra.javasteam.base.IPacketGCMsg;
import in.dragonbra.javasteam.base.IPacketMsg;
import in.dragonbra.javasteam.enums.EMsg;
import in.dragonbra.javasteam.protobufs.steamclient.SteammessagesClientserver2.CMsgGCClient;
import in.dragonbra.javasteam.steam.handlers.ClientMsgHandler;
import in.dragonbra.javasteam.steam.handlers.steamgamecoordinator.callback.MessageCallback;
import in.dragonbra.javasteam.steam.steamclient.SteamClient;
import in.dragonbra.javasteam.util.MsgUtil;
import kz.moltenhaze.lobbybot.clients.Dota2Client;
import kz.moltenhaze.lobbybot.handlers.steam.IGCMsgHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class Dota2GameCoordinator extends ClientMsgHandler {
    private final int appId = Dota2Client.APP_ID;
    @Getter
    private final Map<EMsg, Consumer<IPacketMsg>> dispatchMap;
    private final Map<Class<? extends IGCMsgHandler>, IGCMsgHandler> handlers = new HashMap<>();

    public Dota2GameCoordinator() {
        dispatchMap = new HashMap<>();
        dispatchMap.put(EMsg.ClientFromGC, this::handleGCMsg);
    }

    public void send(IClientGCMsg clientGCMsg) {
        ClientMsgProtobuf<CMsgGCClient.Builder> clientMsgProtobuf = new ClientMsgProtobuf<>(
                CMsgGCClient.class, EMsg.ClientToGC);

        clientMsgProtobuf.getProtoHeader().setRoutingAppid(appId);
        clientMsgProtobuf.getBody().setMsgtype(MsgUtil.makeGCMsg(clientGCMsg.getMsgType(), clientGCMsg.isProto()));
        clientMsgProtobuf.getBody().setAppid(appId);

        clientMsgProtobuf.getBody().setPayload(ByteString.copyFrom(clientGCMsg.serialize()));

        client.send(clientMsgProtobuf);
    }

    @Override
    public void handleMsg(IPacketMsg packetMsg) {
        log.debug("handleMsg: {}", packetMsg.getMsgType());
        Consumer<IPacketMsg> dispatcher = dispatchMap.get(packetMsg.getMsgType());

        if (dispatcher != null) {
            dispatcher.accept(packetMsg);
        }
    }

    private void handleGCMsg(IPacketMsg packetMsg) {
        MessageCallback callback = new MessageCallback(packetMsg);
        log.debug("Dota2GameCoordinator handleGCMsg: {}", callback.getEMsg());

        if (callback.getAppID() != appId) {
            return;
        }

        handleGCMsg(callback.getMessage());
    }

    public void handleGCMsg(IPacketGCMsg packetGCMsg) {
        for (Map.Entry<Class<? extends IGCMsgHandler>, IGCMsgHandler> entry : handlers.entrySet()) {
            entry.getValue().handleGCMsg(packetGCMsg);
        }
    }

    public void addDota2Handler(Dota2GCMsgHandler handler, Dota2Client dota2Client) {
        handler.setup(dota2Client);
        addHandler(handler);
    }

    public void addHandler(IGCMsgHandler gcMsgHandler) {
        if (handlers.containsKey(gcMsgHandler.getClass())) {
            throw new IllegalArgumentException("A handler of type " + gcMsgHandler.getClass() + " is already registered");
        }

        handlers.put(gcMsgHandler.getClass(), gcMsgHandler);
    }

    public void removeHandler(Class<? extends IGCMsgHandler> handler) {
        handlers.remove(handler);
    }

    public void removeHandler(IGCMsgHandler handler) {
        handlers.remove(handler.getClass());
    }

    public SteamClient getSteamClient() {
        return client;
    }

}
