package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaSharedEnums.DOTAJoinLobbyResult;

public class PracticeLobbyCallback extends CallbackMsg {

    private DOTAJoinLobbyResult result;

    public PracticeLobbyCallback(DOTAJoinLobbyResult result) {
        this(JobID.INVALID, result);
    }

    public PracticeLobbyCallback(JobID jobID, DOTAJoinLobbyResult result) {
        setJobID(jobID);
        this.result = result;
    }

    public DOTAJoinLobbyResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PracticeLobbyCallback [result=" + result + "]";
    }

}
