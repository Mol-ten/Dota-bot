package kz.moltenhaze.lobbybot.callbacks.common;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.GcsdkGcmessages.GCConnectionStatus;

public class ConnectionStatusCallback extends CallbackMsg {

    private GCConnectionStatus status;

    public ConnectionStatusCallback(GCConnectionStatus status) {
        this.status = status;
    }

    public GCConnectionStatus getStatus() {
        return status;
    }

}
