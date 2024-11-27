package kz.moltenhaze.lobbybot.handlers.steam;

import in.dragonbra.javasteam.base.IPacketGCMsg;

public interface IGCMsgHandler {
    void handleGCMsg(IPacketGCMsg packetGCMsg);
}
