package kz.moltenhaze.lobbybot.handlers.dota2;

import com.google.protobuf.ByteString;
import in.dragonbra.javasteam.base.ClientGCMsgProtobuf;
import in.dragonbra.javasteam.base.IPacketGCMsg;
import kz.moltenhaze.lobbybot.callbacks.common.lobby.*;
import kz.moltenhaze.lobbybot.callbacks.common.shared.SingleObjectNewLobby;
import kz.moltenhaze.lobbybot.callbacks.common.shared.SingleObjectRemovedLobby;
import kz.moltenhaze.lobbybot.callbacks.common.shared.SingleObjectUpdatedLobby;
import kz.moltenhaze.lobbybot.clients.Dota2Client;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Chat.ChatChannel;
import kz.moltenhaze.proto.dota.BaseGcmessages.CMsgInviteToLobby;
import kz.moltenhaze.proto.dota.BaseGcmessages.EGCBaseMsg;
import kz.moltenhaze.proto.dota.BaseGcmessages.CMsgInvitationCreated;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTADestroyLobbyRequest;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbySetTeamSlot;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbyLaunch;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgAbandonCurrentGame;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbyLeave;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbyCreate;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbySetDetails;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgLobbyListResponse;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgFriendPracticeLobbyListResponse;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbyListResponse;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbyJoinResponse;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobbyInvite;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CMsgLobbyEventPoints;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobby;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobby.CExtraMsg;
import kz.moltenhaze.lobbybot.util.CSOTypes;
import kz.moltenhaze.proto.dota.DotaGcmessagesMsgid.EDOTAGCMsg;
import kz.moltenhaze.proto.dota.DotaSharedEnums;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
public class Dota2Lobby extends Dota2GCMsgHandler{
    private final Map<Integer, Consumer<IPacketGCMsg>> dispatchMap;
    @Getter
    private CSODOTALobby lobby;

    public Dota2Lobby() {
        dispatchMap = new HashMap<>();
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCPracticeLobbyListResponse_VALUE, this::handlePracticeLobbyList);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCFriendPracticeLobbyListResponse_VALUE, this::handleFriendPracticeLobby);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCLobbyListResponse_VALUE, this::handleLobby);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCPracticeLobbyResponse_VALUE, this::handlePracticeLobby);
        dispatchMap.put(EGCBaseMsg.k_EMsgGCInvitationCreated_VALUE, this::handleInvitationCreated);
        dispatchMap.put(EDOTAGCMsg.k_EMsgLobbyEventPoints_VALUE, this::handleEventPoints);
    }

    public void setup(Dota2Client dota2Client) {
        super.setup(dota2Client);
        dota2Client.getManager().subscribe(SingleObjectNewLobby.class, this::onSingleObjectNew);
        dota2Client.getManager().subscribe(SingleObjectUpdatedLobby.class, this::onSingleObjectUpdated);
        dota2Client.getManager().subscribe(SingleObjectRemovedLobby.class, this::onSingleObjectRemoved);
    }


    @Override
    public void handleGCMsg(IPacketGCMsg packetGCMsg) {
        Consumer<IPacketGCMsg> dispatch = dispatchMap.get(packetGCMsg.getMsgType());

        if (dispatch != null) {
            dispatch.accept(packetGCMsg);
        }
    }


    private void handleEventPoints(IPacketGCMsg data) {
        ClientGCMsgProtobuf<CMsgLobbyEventPoints.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgLobbyEventPoints.class, data);
        log.debug("handleEventPoints: {}", protobuf.getBody());
        dota2Client.postCallback(new LobbyEventPointsCallback(protobuf.getBody()));
    }

    private void handlePracticeLobby(IPacketGCMsg data) {
        ClientGCMsgProtobuf<CMsgPracticeLobbyJoinResponse.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgPracticeLobbyJoinResponse.class, data);
        log.debug("handlePracticeLobby: {}/{}", protobuf.getBody(), data.getTargetJobID());
        dota2Client.postCallback(new PracticeLobbyCallback(data.getTargetJobID(), protobuf.getBody().getResult()));
    }

    private void handlePracticeLobbyList(IPacketGCMsg data) {
        ClientGCMsgProtobuf<CMsgPracticeLobbyListResponse.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgPracticeLobbyListResponse.class, data);
        log.debug("handlePracticeLobbyList: {}/{}", protobuf.getBody(), data.getTargetJobID());
        dota2Client.postCallback(new LobbyCallback(data.getTargetJobID(), protobuf.getBody().getLobbiesList()));
    }

    private void handleFriendPracticeLobby(IPacketGCMsg data) {
        ClientGCMsgProtobuf<CMsgFriendPracticeLobbyListResponse.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgFriendPracticeLobbyListResponse.class, data);
        log.debug("handleFriendPracticeLobby: {}", protobuf.getBody());
        dota2Client.postCallback(data.getMsgType(), new LobbyCallback(protobuf.getBody().getLobbiesList()));
    }

    private void handleLobby(IPacketGCMsg data) {
        ClientGCMsgProtobuf<CMsgLobbyListResponse.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgLobbyListResponse.class, data);
        log.debug("handleLobby: {}", protobuf.getBody());
        dota2Client.postCallback(data.getMsgType(), new LobbyCallback(protobuf.getBody().getLobbiesList()));
    }

    private void onSingleObjectNew(SingleObjectNewLobby callback) {
        switch (callback.getTypeId()) {
            case CSOTypes.LOBBY_INVITE_VALUE:
                handleLobbyInvite(callback.getData());
                break;
            case CSOTypes.LOBBY_VALUE:
                handleLobbyNew(callback.getData());
                break;
            default:
                log.debug("onSingleObjectNew with type: {}", callback.getTypeId());
                break;
        }
    }

    private void onSingleObjectUpdated(SingleObjectUpdatedLobby callback) {
        switch (callback.getTypeId()) {
            case CSOTypes.LOBBY_VALUE:
                handleLobbyUpdated(callback.getData());
                break;
            default:
                log.debug("onSingleObjectUpdated with type: {}", callback.getTypeId());
                break;
        }
    }

    private void onSingleObjectRemoved(SingleObjectRemovedLobby callback) {
        log.debug("onSingleObjectRemoved with type: {}", callback.getTypeId());
        switch (callback.getTypeId()) {
            case CSOTypes.LOBBY_INVITE_VALUE:
                handleLobbyInviteRemoved(callback.getData());
                break;
            case CSOTypes.LOBBY_VALUE:
                handleLobbyRemoved(callback.getData());
                break;
            default:
                log.debug("onSingleObjectRemoved with type: {}", callback.getTypeId());
                break;
        }
    }

    private void handleLobbyInvite(ByteString data) {
        try {
            CSODOTALobbyInvite lobbyInvite = CSODOTALobbyInvite.parseFrom(data);
            log.debug("handleLobbyInvite: {}", lobbyInvite);
            dota2Client.postCallback(new LobbyInviteCallback(lobbyInvite));
        } catch (Exception e) {
            log.error("handleLobbyInvite: ", e);
        }
    }

    private void handleLobbyInviteRemoved(ByteString data) {
        try {
            CSODOTALobbyInvite lobbyInvite = CSODOTALobbyInvite.parseFrom(data);
            log.debug("handleLobbyInviteRemoved: {}", lobbyInvite);
            dota2Client.postCallback(new LobbyInviteRemovedCallback(lobbyInvite));
        } catch (Exception e) {
            log.error("handleLobbyInviteRemoved: ", e);
        }
    }

    private void handleLobbyNew(ByteString data) {
        try {
            CSODOTALobby lobby = CSODOTALobby.parseFrom(data);
            log.debug("handleLobbyNew: {}", lobby);
            if (this.lobby != null) {
                if (lobby.getLobbyId() != this.lobby.getLobbyId()) {
                    log.debug("handleLobbyNew: {} vs {}", lobby, this.lobby);
                }
            }
            this.lobby = lobby;
            dota2Client.postCallback(CSOTypes.LOBBY_VALUE, new LobbyNewCallback(lobby));
        } catch (Exception e) {
            log.error("handleLobbyNew: ", e);
        }
    }

    private void handleLobbyUpdated(ByteString data) {
        try {
            CSODOTALobby lobby = CSODOTALobby.parseFrom(data);
            log.debug("handleLobbyUpdated: {}", lobby);
            if (this.lobby != null) {
                if (lobby.getLobbyId() != this.lobby.getLobbyId()) {
                    log.debug("!!handleLobbyUpdated: {} vs {}", lobby, this.lobby);
                }
            }

            this.lobby = lobby;
            dota2Client.postCallback(new LobbyUpdatedCallback(lobby));
            for (CExtraMsg extraMessage : lobby.getExtraMessagesList()) {
                dota2Client.processGCMessage(extraMessage.getId(), extraMessage.getContents());
            }
        } catch (Exception e) {
            log.error("handleLobbyUpdated: ", e);
        }
    }


    private void handleLobbyRemoved(ByteString data) {
        try {
            CSODOTALobby lobby = CSODOTALobby.parseFrom(data);
            log.debug("handleLobbyRemoved: {}", lobby);
            if (lobby.hasLobbyId()) {
                dota2Client.postCallback(new LobbyRemovedCallback(lobby));
            } else {
                dota2Client.postCallback(new LobbyRemovedCallback(this.lobby));
            }
            this.lobby = null;
        } catch (Exception e) {
            log.error("handleLobbyRemoved: ", e);
        }
    }

    private void handleInvitationCreated(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgInvitationCreated.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgInvitationCreated.class, msg);
        if (lobby != null && lobby.getLobbyId() == protobuf.getBody().getGroupId()) {
            log.debug("handleInvitationCreated: {}", protobuf.getBody());
            dota2Client.postCallback(new LobbyInvitationCreatedCallback(protobuf.getBody()));
        }
    }

    public void createPracticeLobby(CMsgPracticeLobbySetDetails lobbyDetails) {
        log.debug("create practice lobby start");
        ClientGCMsgProtobuf<CMsgPracticeLobbyCreate.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgPracticeLobbyCreate.class, EDOTAGCMsg.k_EMsgGCPracticeLobbyCreate_VALUE);
        protobuf.getBody().setLobbyDetails(lobbyDetails);

        try {
            log.debug("createPracticeLobby: {}", protobuf.getBody());
            sendJobAndWait(protobuf, 10L);
        } finally {
            log.debug("create practice lobby end: {}", protobuf.getBody());
        }
    }

    public void inviteToLobbySteam(long steamId) {
        log.info("Inviting {} to the lobby", steamId);
        if (lobby == null) {
            return;
        }

        ClientGCMsgProtobuf<CMsgInviteToLobby.Builder> protobuf = new ClientGCMsgProtobuf<>(CMsgInviteToLobby.class,
                EGCBaseMsg.k_EMsgGCInviteToLobby_VALUE);
        protobuf.getBody().setSteamId(steamId);
        log.debug("inviteToLobby: {}", protobuf.getBody());
        send(protobuf);
    }


    public void configPracticeLobby(CMsgPracticeLobbySetDetails options) {
        ClientGCMsgProtobuf<CMsgPracticeLobbySetDetails.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgPracticeLobbySetDetails.class, EDOTAGCMsg.k_EMsgGCPracticeLobbySetDetails_VALUE);

        if (lobby == null) {
            log.warn("configPracticeLobby: lobby is null" );
        }

        protobuf.getBody().mergeFrom(options);
        protobuf.getBody().setLobbyId(lobby.getLobbyId());

        log.info("configPracticeLobby: {}", protobuf.getBody());
        send(protobuf);
    }

    public void leaveLobby() {
        if (lobby == null) {
            log.warn("leaveLobby: lobby is null");
        }

        ClientGCMsgProtobuf<CMsgPracticeLobbyLeave.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgPracticeLobbyLeave.class, EDOTAGCMsg.k_EMsgGCPracticeLobbyLeave_VALUE);

        log.info("leaveLobby: {}", protobuf.getBody());
        send(protobuf);
    }

    public void abandonCurrentGame() {
        if (lobby == null) {
            log.warn("abandonCurrentGame: lobby is null");
        }

        ClientGCMsgProtobuf<CMsgAbandonCurrentGame.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgAbandonCurrentGame.class, EDOTAGCMsg.k_EMsgGCAbandonCurrentGame_VALUE);
        log.info("abandonCurrentGame: {}", protobuf.getBody());
        send(protobuf);
    }

    public void launchPracticeLobby() {
        if (lobby == null) {
            log.warn("launchPracticeLobby: lobby is null");
        }

        ClientGCMsgProtobuf<CMsgPracticeLobbyLaunch.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgPracticeLobbyLaunch.class, EDOTAGCMsg.k_EMsgGCPracticeLobbyLaunch_VALUE);
        log.info("launchPracticeLobby: {}", protobuf.getBody());
        send(protobuf);
    }

    public void destroyLobby() {
        ClientGCMsgProtobuf<CMsgDOTADestroyLobbyRequest.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgDOTADestroyLobbyRequest.class, EDOTAGCMsg.k_EMsgDestroyLobbyRequest_VALUE);
        log.info("destroyLobby: {}", protobuf.getBody());
        send(protobuf);
    }

    public void sendLobbyMessage(String text) {
        if (lobby == null || lobby.getState() != CSODOTALobby.State.UI) {
            log.warn("sendLobbyMessage: lobby is null");
        }

        ChatChannel channel = dota2Client.getChatHandler().getLobbyChatChanel(String.valueOf(lobby.getLobbyId()));

        if (channel == null) {
            log.warn("sendLobbyMessage: channel by lobbyID: {} is null", lobby.getLobbyId());
            return;
        }

        channel.send(text);
    }

    public void changeBotSlot(DotaSharedEnums.DOTA_GC_TEAM team, Integer slot) {
        if (lobby == null) {
            log.warn("ChangeBotSlot: Lobby is null");
            return;
        }


        log.info("CHANGE BOT SLOT");
        ClientGCMsgProtobuf<CMsgPracticeLobbySetTeamSlot.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgPracticeLobbySetTeamSlot.class, EDOTAGCMsg.k_EMsgGCPracticeLobbySetTeamSlot_VALUE);

        protobuf.getBody().setTeam(team);
        protobuf.getBody().setSlot(slot);
        sendJobAndWait(protobuf);
    }
}
