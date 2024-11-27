package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTAGetPlayerMatchHistoryResponse.Builder;

public class MatchHistoryCallback extends CallbackMsg {

    private Builder builder;

    public MatchHistoryCallback(JobID jobID, Builder builder) {
        setJobID(jobID);
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
