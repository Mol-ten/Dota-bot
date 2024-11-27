package kz.moltenhaze.lobbybot.callbacks.common;

import com.google.protobuf.ByteString;
import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;

public class SingleObjectUpdatedLobby extends CallbackMsg {
    private int typeId;
    private ByteString data;

    public SingleObjectUpdatedLobby(ByteString data, int typeId) {
        this.data = data;
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public ByteString getData() {
        return data;
    }

    public void setData(ByteString data) {
        this.data = data;
    }
}
