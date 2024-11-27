package kz.moltenhaze.lobbybot.callbacks.common.party;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonMatchManagement.CSODOTAParty;

public class PartyRemovedCallback extends CallbackMsg {

    public CSODOTAParty party;

    public PartyRemovedCallback(CSODOTAParty party) {
        this.party = party;
    }

    public CSODOTAParty getParty() {
        return party;
    }

}
