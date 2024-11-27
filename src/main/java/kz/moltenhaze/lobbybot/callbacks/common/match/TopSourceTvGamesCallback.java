package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientWatch.CMsgGCToClientFindTopSourceTVGamesResponse.Builder;

public class TopSourceTvGamesCallback extends CallbackMsg {

    private Builder builder;

    public TopSourceTvGamesCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }
}
