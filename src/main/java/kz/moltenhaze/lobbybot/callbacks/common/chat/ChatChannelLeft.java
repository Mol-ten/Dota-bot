package kz.moltenhaze.lobbybot.callbacks.common.chat;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Chat.ChatChannel;

public class ChatChannelLeft extends CallbackMsg {

    private ChatChannel channel;

    public ChatChannelLeft(ChatChannel channel) {
        this.channel = channel;
    }

    public ChatChannel getChannel() {
        return channel;
    }

}
