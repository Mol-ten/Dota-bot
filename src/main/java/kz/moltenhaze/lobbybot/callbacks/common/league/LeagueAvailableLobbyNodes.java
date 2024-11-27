package kz.moltenhaze.lobbybot.callbacks.common.league;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLeague.CMsgDOTALeagueAvailableLobbyNodes.Builder;

public class LeagueAvailableLobbyNodes extends CallbackMsg {

    private Builder body;

    public LeagueAvailableLobbyNodes(JobID jobID, Builder body) {
        setJobID(jobID);
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "LeagueAvailableLobbyNodes [body=" + body + "]";
    }

}
