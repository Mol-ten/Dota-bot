package kz.moltenhaze.lobbybot.callbacks.common;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.CMsgClientWelcome.Builder;

public class GCWelcomeCallback extends CallbackMsg {

    private Builder body;

    public GCWelcomeCallback(Builder body) {
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

}
