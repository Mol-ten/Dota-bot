package kz.moltenhaze.lobbybot.callbacks.common;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.proto.dota.DotaGcmessagesClient.CMsgDOTAPopup.Builder;

public class PopupCallback extends CallbackMsg {

    private Builder body;

    public PopupCallback(JobID jobID, Builder body) {
        setJobID(jobID);
        this.body = body;
    }

    public Builder getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "PopupCallback [body=" + body + "]";
    }

}
