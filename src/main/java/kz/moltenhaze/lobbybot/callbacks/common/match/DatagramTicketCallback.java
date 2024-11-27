package kz.moltenhaze.lobbybot.callbacks.common.match;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgClientToGCRequestSteamDatagramTicketResponse.Builder;

public class DatagramTicketCallback extends CallbackMsg {

    private Builder builder;

    public DatagramTicketCallback(Builder builder) {
        this.builder = builder;
    }

    public Builder getBuilder() {
        return builder;
    }

}
