package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;

public class LobbyMemberLeftCallback extends CallbackMsg {
    private Long lobbyMemberId;

    public LobbyMemberLeftCallback(Long lobbyMemberId) {
        this.lobbyMemberId = lobbyMemberId;
    }

    public Long getLobbyMemberId() {
        return lobbyMemberId;
    }
}
