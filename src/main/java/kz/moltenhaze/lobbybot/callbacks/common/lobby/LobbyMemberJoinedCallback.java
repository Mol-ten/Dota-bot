package kz.moltenhaze.lobbybot.callbacks.common.lobby;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;

public class LobbyMemberJoinedCallback extends CallbackMsg {
    private Long lobbyMemberId;

    public LobbyMemberJoinedCallback(Long lobbyMemberId) {
        this.lobbyMemberId = lobbyMemberId;
    }

    public Long getLobbyMemberId() {
        return lobbyMemberId;
    }
}
