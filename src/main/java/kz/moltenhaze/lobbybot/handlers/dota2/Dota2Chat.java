package kz.moltenhaze.lobbybot.handlers.dota2;

import in.dragonbra.javasteam.base.ClientGCMsgProtobuf;
import in.dragonbra.javasteam.base.IPacketGCMsg;
import kz.moltenhaze.lobbybot.callbacks.common.chat.*;
import kz.moltenhaze.lobbybot.util.Tuple2;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientChat.*;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby;
import kz.moltenhaze.proto.dota.DotaGcmessagesMsgid.EDOTAGCMsg;
import kz.moltenhaze.proto.dota.DotaSharedEnums.DOTAChatChannelType_t;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public class Dota2Chat extends Dota2GCMsgHandler{
    private final Map<Integer, Consumer<IPacketGCMsg>> dispatchMap;
    @Getter
    private final Map<Long, ChatChannel> channels;
    @Getter
    private Map<Tuple2<String, DOTAChatChannelType_t>, ChatChannel> channelsByName;


    public Dota2Chat() {
        channels = new ConcurrentHashMap<>();
        channelsByName = new ConcurrentHashMap<>();
        dispatchMap = new HashMap<>();

        dispatchMap.put(EDOTAGCMsg.k_EMsgGCJoinChatChannelResponse_VALUE, this::handleJoinResponse);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCChatMessage_VALUE, this::handleMessage);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCOtherJoinedChannel_VALUE, this::handleMembersJoinUpdate);
        dispatchMap.put(EDOTAGCMsg.k_EMsgGCOtherLeftChannel_VALUE, this::handleMembersLeftUpdate);
    }


    @Override
    public void handleGCMsg(IPacketGCMsg packetGCMsg) {
        Consumer<IPacketGCMsg> dispatcher = dispatchMap.get(packetGCMsg.getMsgType());

        if (dispatcher != null) {
            log.debug("handleGCMsg: {}", packetGCMsg.getMsgType());
            dispatcher.accept(packetGCMsg);
        }
    }

    public void cleanUp() {
        this.channels.clear();
        this.channelsByName.clear();
    }

    public void removeChanel(Long chanelId) {
        ChatChannel channel = this.channels.remove(chanelId);
        this.channelsByName.remove(new Tuple2<>(channel.getName(), channel.getType()));
    }

    public ChatChannel getLobbyChatChanel(String name) {
        return this.channelsByName.get(new Tuple2<>("Lobby_" + name, DOTAChatChannelType_t.DOTAChannelType_Lobby));
    }

    private void handleJoinResponse(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgDOTAJoinChatChannelResponse.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgDOTAJoinChatChannelResponse.class, msg);
        Tuple2<String, DOTAChatChannelType_t> key = new Tuple2<>(protobuf.getBody().getChannelName(),
                protobuf.getBody().getChannelType());

        if (protobuf.getBody().getResult() == CMsgDOTAJoinChatChannelResponse.Result.JOIN_SUCCESS) {
            ChatChannel channel;
            if (channels.containsKey(protobuf.getBody().getChannelId())) {
                channel = channels.get(protobuf.getBody().getChannelId());
            } else {
                channel = new ChatChannel(protobuf.getBody().build(), this);
                channels.put(channel.getId(), channel);
                channelsByName.put(key, channel);
            }
            dota2Client.postCallback(new ChatJoinedChannelCallback(channel));
        } else {
            log.debug("handleJoinResponse: {}", protobuf.getBody());
        }
    }

    private void handleMessage(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgDOTAChatMessage.Builder> protobuf = new ClientGCMsgProtobuf<>(CMsgDOTAChatMessage.class,
                msg);


        if (channels.containsKey(protobuf.getBody().getChannelId())) {
            dota2Client.postCallback(
                    new ChatMessageCallback(protobuf.getBody(), channels.get(protobuf.getBody().getChannelId())));
        }
    }

    public void handleMembersLeftUpdate(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgDOTAOtherLeftChatChannel.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgDOTAOtherLeftChatChannel.class, msg);
        if (channels.containsKey(protobuf.getBody().getChannelId())) {
            ChatChannel channel = channels.get(protobuf.getBody().getChannelId());
            Set<Long> left = new HashSet<>();
            if (protobuf.getBody().getSteamId() != 0) {
                left.add(protobuf.getBody().getSteamId());
            }
            channel.processMembers(protobuf.getBody().build());
            if (!left.isEmpty()) {
                dota2Client.postCallback(new ChatMembersLeftUpdateCallback(channel, left));
            }
        }
    }

    public void handleMembersJoinUpdate(IPacketGCMsg msg) {
        ClientGCMsgProtobuf<CMsgDOTAOtherJoinedChatChannel.Builder> protobuf = new ClientGCMsgProtobuf<>(
                CMsgDOTAOtherJoinedChatChannel.class, msg);
        if (channels.containsKey(protobuf.getBody().getChannelId())) {
            ChatChannel channel = channels.get(protobuf.getBody().getChannelId());
            channel.processMembers(protobuf.getBody().build());
            if (protobuf.getBody().getSteamId() != 0) {
                dota2Client.postCallback(new ChatMembersJoinUpdateCallback(channel, protobuf.getBody().getSteamId()));
            }
        }
    }

    public void joinChannel(String channelName, DOTAChatChannelType_t channelType) {
        ClientGCMsgProtobuf<CMsgDOTAJoinChatChannel.Builder> protobuf = new ClientGCMsgProtobuf<>(CMsgDOTAJoinChatChannel.class,
                EDOTAGCMsg.k_EMsgGCJoinChatChannel_VALUE);
        protobuf.getBody().setChannelName(channelName);
        protobuf.getBody().setChannelType(channelType);
        log.debug("Dota2Chat joinChannel: {}", protobuf.getBody());
        send(protobuf);
    }

    public void joinLobbyChannel() {
        if (dota2Client.getLobbyHandler() != null && dota2Client.getLobbyHandler().getLobby() != null) {
            joinChannel("Lobby_" + dota2Client.getLobbyHandler().getLobby().getLobbyId(),
                    DOTAChatChannelType_t.DOTAChannelType_Lobby);
        }
    }

    public void sendAllChannelMessage(String message) {
        channels.values().forEach(chatChannel -> chatChannel.send(message));
    }

    public void joinLobbyChannel(DotaGcmessagesCommonLobby.CSODOTALobby lobby) {
        joinChannel("Lobby_" + lobby.getLobbyId(), DOTAChatChannelType_t.DOTAChannelType_Lobby);
    }

    public void leaveChannel(Long channelId) {
        if (channels.containsKey(channelId)) {
            ChatChannel channel = channels.get(channelId);

            ClientGCMsgProtobuf<CMsgDOTALeaveChatChannel.Builder> protobuf = new ClientGCMsgProtobuf<>(
                    CMsgDOTALeaveChatChannel.class, EDOTAGCMsg.k_EMsgGCLeaveChatChannel_VALUE);

            protobuf.getBody().setChannelId(channelId);
            log.debug("leaveChannel: {}", protobuf.getBody());
            send(protobuf);

            removeChanel(channelId);
            dota2Client.postCallback(new ChatChannelLeft(channel));
        }
    }

    @Getter @Setter
    public class ChatChannel {
        private Set<ChatMember> members;
        private long id;
        private String name;
        private DOTAChatChannelType_t type;
        private int userId;
        private int maxMembers;
        private Dota2Chat dota2chat;

        public ChatChannel(CMsgDOTAJoinChatChannelResponse joinData, Dota2Chat dota2chat) {
            this.members = new HashSet<>();
            this.id = joinData.getChannelId();
            this.name = joinData.getChannelName();
            this.type = joinData.getChannelType();
            this.userId = joinData.getChannelUserId();
            this.maxMembers = joinData.getMaxMembers();
            this.dota2chat = dota2chat;
            processMembers(joinData);
        }

        private void processMembers(Object joinData) {
            if (joinData instanceof CMsgDOTAOtherLeftChatChannel) {
                members.removeIf(m -> m.getSteamId().equals(((CMsgDOTAOtherLeftChatChannel) joinData).getSteamId())
                        || m.getChannelUserId().equals(((CMsgDOTAOtherLeftChatChannel) joinData).getChannelUserId()));
            } else if (joinData instanceof CMsgDOTAJoinChatChannelResponse) {
                for (CMsgDOTAChatMember member : ((CMsgDOTAJoinChatChannelResponse) joinData).getMembersList()) {
                    members.add(new ChatMember(member));
                }
            } else if (joinData instanceof CMsgDOTAOtherJoinedChatChannel) {
                members.add(new ChatMember((CMsgDOTAOtherJoinedChatChannel) joinData));
            }
        }

        public void leave() {
            dota2chat.leaveChannel(id);
        }




        public void send(String message) {
            ClientGCMsgProtobuf<CMsgDOTAChatMessage.Builder> protobuf = new ClientGCMsgProtobuf<>(
                    CMsgDOTAChatMessage.class, EDOTAGCMsg.k_EMsgGCChatMessage_VALUE);
            protobuf.getBody().setChannelId(id);
            protobuf.getBody().setText(message);
            log.debug("ChatChannelSend: {}", protobuf.getBody());
            dota2chat.send(protobuf);
        }

        public void shareLobby() {
            if (dota2Client.getLobbyHandler() != null && dota2Client.getLobbyHandler().getLobby() != null) {
                ClientGCMsgProtobuf<CMsgDOTAChatMessage.Builder> protobuf = new ClientGCMsgProtobuf<>(
                        CMsgDOTAChatMessage.class, EDOTAGCMsg.k_EMsgGCChatMessage_VALUE);
                protobuf.getBody().setChannelId(id);
                protobuf.getBody().setShareLobbyId(dota2Client.getLobbyHandler().getLobby().getLobbyId());
                protobuf.getBody().setShareLobbyPasskey(dota2Client.getLobbyHandler().getLobby().getPassKey());
                log.debug("ChatChannelShareLobby: {}", protobuf.getBody());
                dota2chat.send(protobuf);
            }
        }

        public void flipCoin() {
            ClientGCMsgProtobuf<CMsgDOTAChatMessage.Builder> protobuf = new ClientGCMsgProtobuf<>(
                    CMsgDOTAChatMessage.class, EDOTAGCMsg.k_EMsgGCChatMessage_VALUE);
            protobuf.getBody().setChannelId(id);
            protobuf.getBody().setCoinFlip(true);
            log.debug("ChatChannelFlipCoin: {}", protobuf.getBody());
            dota2chat.send(protobuf);
        }

        public void rollDice(Integer min, Integer max) {
            ClientGCMsgProtobuf<CMsgDOTAChatMessage.Builder> protobuf = new ClientGCMsgProtobuf<>(
                    CMsgDOTAChatMessage.class, EDOTAGCMsg.k_EMsgGCChatMessage_VALUE);
            protobuf.getBody().setChannelId(id);
            protobuf.getBody().getDiceRollBuilder().setRollMin(min != null ? min : 1)
                    .setRollMax(max != null ? max : 100).build();
            log.debug("ChatChannelRollDice: {}", protobuf.getBody());
            dota2chat.send(protobuf);
        }

    }

    private class ChatMember {

        @Getter
        private Integer channelUserId;
        private String personaName;
        @Getter
        private Long steamId;
        private Integer status;

        public ChatMember(CMsgDOTAChatMember data) {
            this.channelUserId = data.getChannelUserId();
            this.personaName = data.getPersonaName();
            this.steamId = data.getSteamId();
            this.status = data.getStatus();
        }

        public ChatMember(CMsgDOTAOtherJoinedChatChannel data) {
            this.channelUserId = data.getChannelUserId();
            this.personaName = data.getPersonaName();
            this.steamId = data.getSteamId();
            this.status = data.getStatus();
        }


        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((channelUserId == null) ? 0 : channelUserId.hashCode());
            result = prime * result + ((steamId == null) ? 0 : steamId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ChatMember other = (ChatMember) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (channelUserId == null) {
                if (other.channelUserId != null)
                    return false;
            } else if (!channelUserId.equals(other.channelUserId))
                return false;
            if (steamId == null) {
                return other.steamId == null;
            } else return steamId.equals(other.steamId);
        }

        private Dota2Chat getOuterType() {
            return Dota2Chat.this;
        }

        @Override
        public String toString() {
            return "ChatMember [channelUserId=" + channelUserId + ", personaName=" + personaName + ", steamId="
                    + steamId + ", status=" + status + "]";
        }

    }
}
