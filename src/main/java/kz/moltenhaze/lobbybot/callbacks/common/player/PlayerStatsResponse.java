package kz.moltenhaze.lobbybot.callbacks.common.player;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgGCToClientPlayerStatsResponse.Builder;

public class PlayerStatsResponse extends CallbackMsg {

    private Builder body;

    public PlayerStatsResponse(JobID jobID, Builder body) {
        setJobID(jobID);
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "PlayerStatsResponse [body=" + body + "]";
    }

}
