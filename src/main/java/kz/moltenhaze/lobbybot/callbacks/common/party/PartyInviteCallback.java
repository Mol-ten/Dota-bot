package kz.moltenhaze.lobbybot.callbacks.common.party;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonMatchManagement.CSODOTAPartyInvite;

public class PartyInviteCallback extends CallbackMsg {

    public CSODOTAPartyInvite partyInvite;

    public PartyInviteCallback(CSODOTAPartyInvite partyInvite) {
        this.partyInvite = partyInvite;
    }

    public CSODOTAPartyInvite getPartyInvite() {
        return partyInvite;
    }

}
