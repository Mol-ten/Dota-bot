package kz.moltenhaze.lobbybot.callbacks.common.party;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonMatchManagement.CSODOTAParty;

public class PartyNewCallback extends CallbackMsg {

    public CSODOTAParty party;

    public PartyNewCallback(CSODOTAParty party) {
        this.party = party;
    }

    public CSODOTAParty getParty() {
        return party;
    }

}
