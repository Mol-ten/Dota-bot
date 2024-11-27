package kz.moltenhaze.lobbybot.callbacks.common.chat;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Chat.ChatChannel;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientChat.CMsgDOTAChatMessage.Builder;

public class ChatMessageCallback extends CallbackMsg {

    private Builder builder;
    private ChatChannel chatChannel;

    public ChatMessageCallback(Builder builder, ChatChannel chatChannel) {
        this.builder = builder;
        this.chatChannel = chatChannel;
    }

    public Builder getBuilder() {
        return builder;
    }

    public ChatChannel getChatChannel() {
        return chatChannel;
    }

}
