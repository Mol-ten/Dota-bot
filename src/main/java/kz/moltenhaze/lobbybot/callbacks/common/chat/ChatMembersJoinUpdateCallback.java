package kz.moltenhaze.lobbybot.callbacks.common.chat;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Chat.ChatChannel;

public class ChatMembersJoinUpdateCallback extends CallbackMsg {

    private ChatChannel channel;
    private Long playerSteamId;

    public ChatMembersJoinUpdateCallback(ChatChannel channel, Long playerSteamId) {
        this.channel = channel;
        this.playerSteamId = playerSteamId;
    }

    public ChatChannel getChannel() {
        return channel;
    }

    public Long getPlayerSteamId() {
        return playerSteamId;
    }

}
