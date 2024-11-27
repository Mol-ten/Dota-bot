package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbyListResponseEntry;

import java.util.List;

public class LobbyCallback extends CallbackMsg {
    private List<CMsgPracticeLobbyListResponseEntry> lobbies;

    public LobbyCallback(List<CMsgPracticeLobbyListResponseEntry> lobbies) {
        this(JobID.INVALID, lobbies);
    }

    public LobbyCallback(JobID jobID, List<CMsgPracticeLobbyListResponseEntry> lobbies) {
        setJobID(jobID);
        this.lobbies = lobbies;
    }

    public List<CMsgPracticeLobbyListResponseEntry> getLobbies() {
        return lobbies;
    }

    @Override
    public String toString() {
        return "LobbyCallback [lobbies=" + lobbies + "]";
    }

}
