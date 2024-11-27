package kz.moltenhaze.lobbybot.callbacks.common.party;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.BaseGcmessages.CMsgInvitationCreated.Builder;

public class PartyInvitationCreatedCallback extends CallbackMsg {

    private Builder builder;

    public PartyInvitationCreatedCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
