package kz.moltenhaze.lobbybot.callbacks.common.player;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgPlayerConductScorecard.Builder;

public class ConductScorecardCallback extends CallbackMsg {

    private Builder body;

    public ConductScorecardCallback(Builder body) {
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "ConductScorecardCallback [body=" + body + "]";
    }

}
