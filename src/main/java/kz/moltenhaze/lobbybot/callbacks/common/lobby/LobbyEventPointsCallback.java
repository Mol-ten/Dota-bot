package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CMsgLobbyEventPoints.Builder;

public class LobbyEventPointsCallback extends CallbackMsg {

    private Builder builder;

    public LobbyEventPointsCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
