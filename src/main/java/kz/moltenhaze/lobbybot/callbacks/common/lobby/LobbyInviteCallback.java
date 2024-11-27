package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobbyInvite;

public class LobbyInviteCallback extends CallbackMsg {

    public CSODOTALobbyInvite lobbyInvite;

    public LobbyInviteCallback(CSODOTALobbyInvite lobbyInvite) {
        this.lobbyInvite = lobbyInvite;
    }

    public CSODOTALobbyInvite getLobbyInvite() {
        return lobbyInvite;
    }

}
