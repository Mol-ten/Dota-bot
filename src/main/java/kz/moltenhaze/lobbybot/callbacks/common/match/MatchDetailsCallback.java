package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgGCMatchDetailsResponse.Builder;

public class MatchDetailsCallback extends CallbackMsg {

    private Builder builder;

    public MatchDetailsCallback(JobID jobID, Builder builder) {
        setJobID(jobID);
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
