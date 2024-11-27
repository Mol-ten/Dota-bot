package kz.moltenhaze.lobbybot.callbacks.common.player;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgProfileResponse.Builder;

public class ProfileResponse extends CallbackMsg {

    private Builder body;

    public ProfileResponse(JobID jobID, Builder body) {
        setJobID(jobID);
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "ProfileResponse [body=" + body + "]";
    }

}
