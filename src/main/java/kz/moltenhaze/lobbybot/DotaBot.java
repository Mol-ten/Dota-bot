package kz.moltenhaze.lobbybot;

import in.dragonbra.javasteam.steam.steamclient.callbackmgr.CallbackManager;
import in.dragonbra.javasteam.types.SteamID;
import in.dragonbra.javasteam.util.log.DefaultLogListener;
import in.dragonbra.javasteam.util.log.LogManager;
import kz.moltenhaze.lobbybot.callbacks.common.NotReadyCallback;
import kz.moltenhaze.lobbybot.callbacks.common.ReadyCallback;
import kz.moltenhaze.lobbybot.callbacks.common.chat.ChatMessageCallback;
import kz.moltenhaze.lobbybot.callbacks.common.lobby.LobbyInvitationCreatedCallback;
import kz.moltenhaze.lobbybot.callbacks.common.lobby.LobbyNewCallback;
import kz.moltenhaze.lobbybot.callbacks.common.lobby.LobbyRemovedCallback;
import kz.moltenhaze.lobbybot.callbacks.common.lobby.LobbyUpdatedCallback;
import kz.moltenhaze.lobbybot.callbacks.common.match.MatchDetailsCallback;
import kz.moltenhaze.lobbybot.clients.Dota2Client;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Chat;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Chat.ChatChannel;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Lobby;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Match;
import kz.moltenhaze.lobbybot.models.LobbyPlayer;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbySetDetails;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobbyMember;
import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobby;
import kz.moltenhaze.proto.dota.DotaGcmessagesMsgid;
import kz.moltenhaze.proto.dota.DotaSharedEnums.EMatchOutcome;
import kz.moltenhaze.proto.dota.DotaSharedEnums;
import kz.moltenhaze.proto.dota.Netmessages;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.ec.custom.sec.SecT113Field;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Component
@Slf4j
public class DotaBot {
    private Dota2Client client;
    private Dota2Chat chatHandler;
    private Dota2Lobby lobbyHandler;
    private Dota2Match matchHandler;
    private ChatChannel lobbyChatChannel;
    private List<CSODOTALobbyMember> players;
    private CallbackManager manager;
    private boolean isLobbyReady;
    private Instant lastLobbyCreationTime;
    private boolean isGCReady; //TODO: make use of it
    private Set<LobbyPlayer> RadiantTeam;
    private Set<LobbyPlayer> DireTeam;

    @Getter
    private boolean isBusy;

    public DotaBot() {          //TODO: add username, password as method arguments for DotaBotManager
        LogManager.addListener(new DefaultLogListener());
        client = new Dota2Client("moneymason", "shelby31gt500");
        manager = client.getManager();
        chatHandler = client.getChatHandler();
        lobbyHandler = client.getLobbyHandler();
        matchHandler = client.getMatchHandler();

        players = new ArrayList<>();

        manager.subscribe(ReadyCallback.class, this::onReady);
        manager.subscribe(NotReadyCallback.class, this::onNotReady);

        manager.subscribe(LobbyNewCallback.class, this::onLobbyNew);
        manager.subscribe(LobbyUpdatedCallback.class, this::onLobbyUpdated);
        manager.subscribe(LobbyRemovedCallback.class, this::onLobbyRemovedCallback);
        manager.subscribe(LobbyInvitationCreatedCallback.class, this::onLobbyInvitationCreated);

        manager.subscribe(ChatMessageCallback.class, this::onChatMessage);
        manager.subscribe(MatchDetailsCallback.class, this::onMatchDetails);
    }

    private void onReady(ReadyCallback callback) {
        isGCReady = true;
        log.info("DOTA2 GC connection has been established!");
    }

    private void onNotReady(NotReadyCallback callback) {
        isGCReady = false;
        log.warn("DOTA2 GC connection has been lost!");
    }

    private void onLobbyNew(LobbyNewCallback callback){
        if (lobbyHandler.getLobby().getLobbyId() != callback.getLobby().getLobbyId()) {
            log.warn("Wrong lobby!");
            return;
        }

        lobbyHandler.changeBotSlot(DotaSharedEnums.DOTA_GC_TEAM.DOTA_GC_TEAM_PLAYER_POOL, 1);
        chatHandler.joinLobbyChannel();
        lobbyChatChannel = chatHandler.getLobbyChatChanel(lobbyHandler.getLobby().getLobbyId() + "");

        //TODO: rest request to discord bot to inform about lobby readiness
        //TODO: implement a way to perform steamInvites, and create a lobby with observers
        client.getLobbyHandler().inviteToLobbySteam(76561198058063405L);
    }

    public void onLobbyUpdated(LobbyUpdatedCallback callback) {
        log.info("onLobbyUpdated: {}", callback.getLobby());
        CSODOTALobby lobby = callback.getLobby();
        players = lobby.getAllMembersList();
        log.info(lobby.getState().name());
        log.info(lobby.getGameState().name());
    }

    private void onLobbyRemovedCallback(LobbyRemovedCallback lobbyRemovedCallback) {
        log.info("Lobby match has been ended.");
        EMatchOutcome outcome = lobbyRemovedCallback.getLobby().getMatchOutcome();

        if (outcome == EMatchOutcome.k_EMatchOutcome_RadVictory || outcome == EMatchOutcome.k_EMatchOutcome_DireVictory) {
            log.info("Winner is :{}", outcome);

        //TODO: rest request to update LobbyQueue message with outcome info
            // TODO: add a method to process match
        }

        matchHandler.requestMatchDetails(lobbyRemovedCallback.getLobby().getMatchId());
        chatHandler.cleanUp();
    }

    private void onLobbyInvitationCreated(LobbyInvitationCreatedCallback lobbyInvitationCreatedCallback) {
        String playerName = client.getSteamFriends()
                .getFriendPersonaName(new SteamID(lobbyInvitationCreatedCallback.getBuilder().getSteamId()));
        log.info("Lobby invitation created for player: {}", playerName);
    }

    private void onChatMessage(ChatMessageCallback callback) {
        if (chatHandler.getLobbyChatChanel(getLobby().getLobbyId() + "") != callback.getChatChannel()) {
            return;
        }

        Optional<CSODOTALobbyMember> player = players
                .stream()
                .filter(member -> member.getId() - 76561197960265728L == callback.getBuilder().getAccountId())
                .findFirst();

        player.ifPresent(member ->
                chatHandler.getLobbyChatChanel(lobbyHandler.getLobby().getLobbyId() + "")
                        .send("Players slot: " + member.getSlot() + ", players id " + member.getId())); // TODO: method to send lobby message





        String text = callback.getBuilder().getText();

        if (text.equals("!start")) {
            launchLobby();
        }
    }

    private void sendLobbyMessage() {

    }

    private void onMatchDetails(MatchDetailsCallback callback) {
        log.info(callback.getBuilder().getMatch() + "");  //TODO: update DB with matchDetails
    }

    public void createLobby(CMsgPracticeLobbySetDetails.Builder lobbyOptions) {
        if (!isGCReady) {
            log.warn("GC connection is not ready.");
            return;
        }

        if (lobbyHandler.getLobby() != null) {
            log.warn("Lobby already exists.");
            return;
        }

        log.debug("createLobby: {}", lobbyOptions);
        Dota2Lobby lobby = client.getLobbyHandler();

        if (!canCreateLobby()) {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        lastLobbyCreationTime = Instant.now();
        lobby.createPracticeLobby(lobbyOptions.build());
    }


    public void start() {
        client.connect();
    }

    public void exit() {
        client.stop();
    }

    public void destroyLobby() {
        if (!isGCReady) {
            log.warn("GC connection is not ready.");
            return;
        }

        if (getLobby() == null) {
            log.warn("Lobby is null");
            return;
        }

        if (getLobbyState() != CSODOTALobby.State.UI) {
            log.warn("Lobby state has to be equal to \"UI\" to launch the lobby. Current state: {}", getLobbyState());
        }

        lastLobbyCreationTime = Instant.now();
        lobbyHandler.destroyLobby();
    }

    public void launchLobby() {
        if (!isGCReady) {
            log.warn("GC connection is not ready.");
            return;
        }

        if (getLobby() == null) {
            log.warn("Lobby is null");
            return;
        }

        if (getLobbyState() == CSODOTALobby.State.RUN){
            log.warn("Lobby is already running");
            return;
        }

        if (getLobbyState() != CSODOTALobby.State.UI) {
            log.warn("State should be equal to \"UI\" to launch the lobby. Current state: {}", getLobbyState());
            return;
        }

        //TODO: lobby check for readiness (10 player slots are busy, both captains are ready,
        // if its CM, then check for tags, if its immortal draft then check that only first slots of bot team are busy.)


        lobbyHandler.launchPracticeLobby();
    }

    public CSODOTALobby getLobby() {
        return lobbyHandler.getLobby();
    }

    private CSODOTALobby.State getLobbyState() {
        if (getLobby() == null) {
            log.warn("Lobby is null");
            return null;
        }

        return getLobby().getState();
    }

    private boolean canCreateLobby() {
        if (lastLobbyCreationTime == null) {
            return true;
        }

        Duration durationSinceLastLobby = Duration.between(lastLobbyCreationTime, Instant.now());
        return durationSinceLastLobby.getSeconds() >= 10;
    }

    private void sendLobbyMessage(String message) {
        if (lobbyChatChannel == null) {
            log.error("lobbyChatChannel is null");
            throw new RuntimeException("lobbyChatChannel is null");
        }

        lobbyChatChannel.send(message);
    }

    private void processPlayers(List<CSODOTALobbyMember> members) {

    }
}
