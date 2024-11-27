package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTARequestMatchesResponse.Builder;

public class RequestMatchesCallback extends CallbackMsg {

    private Builder builder;

    public RequestMatchesCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
