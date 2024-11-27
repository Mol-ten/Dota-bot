package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobby;


public class LobbyUpdatedCallback extends CallbackMsg {

    private CSODOTALobby lobby;

    public LobbyUpdatedCallback(CSODOTALobby lobby) {
        this.lobby = lobby;
    }

    public CSODOTALobby getLobby() {
        return lobby;
    }

}
