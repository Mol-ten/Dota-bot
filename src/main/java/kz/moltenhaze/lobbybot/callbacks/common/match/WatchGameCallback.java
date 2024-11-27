package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientWatch.CMsgWatchGameResponse.Builder;

public class WatchGameCallback extends CallbackMsg {

    private Builder builder;

    public WatchGameCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
