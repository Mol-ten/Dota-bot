package kz.moltenhaze.lobbybot.callbacks.common;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgReadyUpStatus.Builder;


public class ReadyUpStatusCallback extends CallbackMsg {

    private Builder body;

    public ReadyUpStatusCallback(Builder body) {
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

}
