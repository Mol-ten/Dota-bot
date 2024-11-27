package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgGCToClientMatchSignedOut.Builder;

public class MatchSignedOutCallback extends CallbackMsg {

    private Builder builder;

    public MatchSignedOutCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
