package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobby;

public class LobbyRemovedCallback extends CallbackMsg {

    public CSODOTALobby lobby;

    public LobbyRemovedCallback(CSODOTALobby lobby) {
        this.lobby = lobby;
    }

    public CSODOTALobby getLobby() {
        return lobby;
    }

}
