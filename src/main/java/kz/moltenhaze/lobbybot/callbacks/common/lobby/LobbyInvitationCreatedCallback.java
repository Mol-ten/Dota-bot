package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.BaseGcmessages.CMsgInvitationCreated.Builder;

public class LobbyInvitationCreatedCallback extends CallbackMsg {

    private Builder builder;

    public LobbyInvitationCreatedCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
