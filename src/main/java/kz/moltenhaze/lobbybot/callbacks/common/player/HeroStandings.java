package kz.moltenhaze.lobbybot.callbacks.common.player;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgGCGetHeroStandingsResponse.Builder;

public class HeroStandings extends CallbackMsg {

    private Builder body;

    public HeroStandings(Builder body) {
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HeroStandings [body=" + body + "]";
    }

}
