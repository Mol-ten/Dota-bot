package kz.moltenhaze.lobbybot.handlers.dota2;

import in.dragonbra.javasteam.base.IClientGCMsg;
import in.dragonbra.javasteam.types.JobID;
import kz.moltenhaze.lobbybot.clients.Dota2Client;
import kz.moltenhaze.lobbybot.handlers.steam.IGCMsgHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Dota2GCMsgHandler implements IGCMsgHandler {
    protected Dota2GameCoordinator dota2GameCoordinator;
    protected Dota2Client dota2Client;

    public void setup(Dota2Client dota2Client) {
        this.dota2GameCoordinator = dota2Client.getGameCoordinator();
        this.dota2Client = dota2Client;
    }

    public void send(IClientGCMsg msg) {
        if (dota2Client.isReady()) {
            dota2GameCoordinator.send(msg);
        } else {
            log.warn("GC is not ready. msgType: {}", msg.getMsgType());
            throw new RuntimeException("GC is not ready");
        }
    }

    public JobID sendJob(IClientGCMsg msg) {
        JobID jobID = dota2Client.getNextJobID();
        msg.setSourceJobID(jobID);
        send(msg);
        return jobID;
    }

    public <T> T sendJobAndWait(IClientGCMsg msg) {
        sendJob(msg);
        return dota2Client.registerAndWait(msg.getSourceJobID());
    }

    public <T> T sendCustomAndWait(IClientGCMsg msg, Object key) {
        send(msg);
        return dota2Client.registerAndWait(key);
    }

    public <T> T sendJobAndWait(IClientGCMsg msg, Long timeout) {
        sendJob(msg);
        return dota2Client.registerAndWait(msg.getSourceJobID(), timeout);
    }

    public <T> T sendCustomAndWait(IClientGCMsg msg, Object key, Long timeout) {
        send(msg);
        return dota2Client.registerAndWait(key, timeout);
    }

}