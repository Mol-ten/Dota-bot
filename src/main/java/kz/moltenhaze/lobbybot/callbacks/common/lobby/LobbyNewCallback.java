package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobby;

public class LobbyNewCallback extends CallbackMsg {

    public CSODOTALobby lobby;

    public LobbyNewCallback(CSODOTALobby lobby) {
        this.lobby = lobby;
    }

    public CSODOTALobby getLobby() {
        return lobby;
    }

    @Override
    public String toString() {
        return "LobbyNewCallback [lobby=" + lobby + "]";
    }

}
