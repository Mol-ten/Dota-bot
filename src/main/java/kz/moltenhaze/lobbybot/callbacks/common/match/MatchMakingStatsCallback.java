package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTAMatchmakingStatsResponse.Builder;

public class MatchMakingStatsCallback extends CallbackMsg {

    private Builder builder;

    public MatchMakingStatsCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
