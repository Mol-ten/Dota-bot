package kz.moltenhaze.lobbybot.callbacks.common.chat;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Chat.ChatChannel;

import java.util.Set;

public class ChatMembersLeftUpdateCallback extends CallbackMsg {

    private ChatChannel channel;
    private Set<Long> left;

    public ChatMembersLeftUpdateCallback(ChatChannel channel, Set<Long> left) {
        this.channel = channel;
        this.left = left;
    }

    public ChatChannel getChannel() {
        return channel;
    }

    public Set<Long> getLeft() {
        return left;
    }

}
