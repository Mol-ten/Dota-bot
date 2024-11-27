package kz.moltenhaze.lobbybot.callbacks.common.chat;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Chat.ChatChannel;

import java.util.Set;

public class ChatMembersUpdateCallback extends CallbackMsg {

    private ChatChannel channel;
    private Set<Long> join;
    private Set<Long> left;

    public ChatMembersUpdateCallback(ChatChannel channel, Set<Long> join, Set<Long> left) {
        this.channel = channel;
        this.join = join;
        this.left = left;
    }

    public ChatChannel getChannel() {
        return channel;
    }

    public Set<Long> getJoin() {
        return join;
    }

    public Set<Long> getLeft() {
        return left;
    }

}
