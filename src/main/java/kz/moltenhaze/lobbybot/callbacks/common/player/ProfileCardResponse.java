package kz.moltenhaze.lobbybot.callbacks.common.player;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommon.CMsgDOTAProfileCard.Builder;

public class ProfileCardResponse extends CallbackMsg {

    private Builder body;

    public ProfileCardResponse(JobID jobID, Builder builder) {
        setJobID(jobID);
        this.body = builder;
    }

    public Builder getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "ProfileCardResponse [body=" + body + "]";
    }

}
