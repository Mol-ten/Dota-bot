package kz.moltenhaze.lobbybot.callbacks.common.league;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLeague.CMsgDOTALeagueInfoList.Builder;

public class LeagueInfoAdmin extends CallbackMsg {
    private Builder body;

    public LeagueInfoAdmin(JobID jobID, Builder builder) {
        setJobID(jobID);
        this.body = builder;
    }

    public Builder getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "LeagueInfoAdmin [body=" + body + "]";
    }

}
