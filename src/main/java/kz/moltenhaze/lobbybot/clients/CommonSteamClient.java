package kz.moltenhaze.lobbybot.clients;

import in.dragonbra.javasteam.base.IClientMsg;
import in.dragonbra.javasteam.enums.EPersonaState;
import in.dragonbra.javasteam.enums.EResult;
import in.dragonbra.javasteam.networking.steam3.ProtocolTypes;
import in.dragonbra.javasteam.steam.handlers.steamfriends.SteamFriends;
import in.dragonbra.javasteam.steam.handlers.steamuser.LogOnDetails;
import in.dragonbra.javasteam.steam.handlers.steamuser.SteamUser;
import in.dragonbra.javasteam.steam.handlers.steamuser.callback.LoggedOffCallback;
import in.dragonbra.javasteam.steam.handlers.steamuser.callback.LoggedOnCallback;
import in.dragonbra.javasteam.steam.steamclient.SteamClient;
import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackManager;
import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackMsg;
import in.dragonbra.javasteam.steam.steamclient.callbacks.ConnectedCallback;
import in.dragonbra.javasteam.steam.steamclient.callbacks.DisconnectedCallback;
import in.dragonbra.javasteam.steam.steamclient.configuration.SteamConfiguration;
import in.dragonbra.javasteam.types.JobID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Slf4j
public class CommonSteamClient {
    private static final Long DEFAULT_TIMEOUT = 10L;
    @Getter
    protected SteamClient steamClient;
    @Getter
    protected CallbackManager manager;
    protected boolean logged;
    protected SteamUser steamUser;
    @Getter
    protected SteamFriends steamFriends;
    protected boolean isRunning;
    private CompletableFuture<Void> managerLoop;
    private final Map<Object, CompletableFuture<Object>> subscribers = new HashMap<>();
    private final String username;
    private final String password;

    CommonSteamClient(String username, String password) {
        this.username = username;
        this.password = password;

        init();
    }

    protected void init() {
        steamClient = new SteamClient(SteamConfiguration.create(iSteamConfigurationBuilder ->
                iSteamConfigurationBuilder.withProtocolTypes(ProtocolTypes.TCP)));
        manager = new CallbackManager(steamClient);
        steamFriends = steamClient.getHandler(SteamFriends.class);
        steamUser = steamClient.getHandler(SteamUser.class);

        manager.subscribe(ConnectedCallback.class, this::onConnected);
        manager.subscribe(DisconnectedCallback.class, this::onDisconnected);
        manager.subscribe(LoggedOnCallback.class, this::onLoggedOn);
        manager.subscribe(LoggedOffCallback.class, this::onLoggedOff);
    }

    public void onConnected(ConnectedCallback callback) {
        log.info("Connected to Steam. Logging in {}...", username);

        LogOnDetails details = new LogOnDetails();
        details.setUsername(username);
        details.setPassword(password);

        steamUser.logOn(details);
    }

    public void onDisconnected(DisconnectedCallback callback) {
        log.info("Disconnected callback. Is user initialized: {}", callback.isUserInitiated());

        if (callback.isUserInitiated()) {
            isRunning = false;
        } else {
            log.info("Disconnected from Steam.");
        }
    }

    protected void onLoggedOn(LoggedOnCallback callback) {
        if (callback.getResult() != EResult.OK) {
            log.info("Unable to logon to Steam: {} / {}", callback.getResult(), callback.getExtendedResult());

            isRunning = false;
            return;
        }

        log.info("Successfully logged on.");
        logged = true;

        steamFriends.setPersonaState(EPersonaState.Online);
    }

    public void onLoggedOff(LoggedOffCallback callback) {
        log.info("Logged off of Steam: {}", callback.getResult());
        logged = false;

    }

    public void connect() {
        isRunning = true;

        log.info("Connecting to Steam.");

        steamClient.connect();

        if (managerLoop == null) {
            managerLoop = CompletableFuture.runAsync(() -> {
                while (isRunning) {
                    manager.runWaitCallbacks(1000L);
                }
            });
        }

        log.info("Manager loop has been started");
    }

    public void disconnect() {
        log.info("Disconnecting from Steam.");
        isRunning = false;
        steamClient.disconnect();
    }

    public void postCallback(CallbackMsg callback) {
        if (!callback.getJobID().equals(JobID.INVALID)) {
            postCallback(callback.getJobID(), callback);
        } else {
            steamClient.postCallback(callback);
        }

    }

    public void postCallback(Object key, CallbackMsg callback) {
        submitResponse(key, callback);
        steamClient.postCallback(callback);
    }

    public void submitResponse(Object key, Object result) {
        log.info("submitResponse: {}", key);
        if (this.subscribers.get(key) != null) {
            this.subscribers.get(key).complete(result);
        } else {
            CompletableFuture<Object> future = new CompletableFuture<>();
            future.complete(result);
            this.subscribers.put(key, future);
        }
    }

    private JobID sendJob(IClientMsg msg) {
        JobID jobID = steamClient.getNextJobID();
        msg.setSourceJobID(jobID);
        steamClient.send(msg);
        return jobID;
    }

    public <T> T sendJobAndWait(IClientMsg msg,  long timeout) {
        sendJob(msg);
        return registerAndWait(msg.getSourceJobID(), timeout);
    }

    public <T> T registerAndWait(Object key) {
        return registerAndWait(key, DEFAULT_TIMEOUT);
    }

    @SuppressWarnings("unchecked")
    public <T> T registerAndWait(Object key, long timeout) {
        log.debug("registerAndWait: {}", key);
        T value = null;
        try {
            if (this.subscribers.get(key) != null) {
                return (T) this.subscribers.get(key).get(timeout, TimeUnit.SECONDS);
            }
            CompletableFuture<Object> future = new CompletableFuture<>();
            subscribers.put(key, future);
            value = (T) future.get(timeout, TimeUnit.SECONDS);
            return value;
        } catch (Exception e) {
            log.error("registerAndWait: {}", e.getMessage());
            return null;
        } finally {
            subscribers.remove(key);

            System.out.println("registerAndWait end: " + value);
        }
    }

    public JobID getNextJobID() {
        return steamClient.getNextJobID();
    }

}
