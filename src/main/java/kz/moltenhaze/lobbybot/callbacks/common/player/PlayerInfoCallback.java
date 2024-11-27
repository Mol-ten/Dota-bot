package kz.moltenhaze.lobbybot.callbacks.common.player;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientFantasy.CMsgDOTAPlayerInfo.Builder;

public class PlayerInfoCallback extends CallbackMsg {

    private Builder body;

    public PlayerInfoCallback(Builder body) {
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

}
