package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommon.CMsgDOTAMatchMinimal.Builder;

public class MatchesMinimalCallback extends CallbackMsg {

    private Builder builder;

    public MatchesMinimalCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
