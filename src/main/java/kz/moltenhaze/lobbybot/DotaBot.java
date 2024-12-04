package kz.moltenhaze.lobbybot;

import in.dragonbra.javasteam.util.log.DefaultLogListener;
import in.dragonbra.javasteam.util.log.LogManager;
import kz.moltenhaze.lobbybot.clients.Dota2Client;
import kz.moltenhaze.lobbybot.managers.ChatManager;
import kz.moltenhaze.lobbybot.managers.LobbyManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
public class DotaBot {
    private final Dota2Client client;
    @Getter
    private ChatManager chatManager;
    @Getter
    private LobbyManager lobbyManager;

    @Getter
    private boolean isBusy;

    public DotaBot(ChatManager chatManager, LobbyManager lobbyManager, Dota2Client client) { //TODO: add username, password as method arguments for DotaBotManager
        this.chatManager = chatManager;
        this.lobbyManager = lobbyManager;
        LogManager.addListener(new DefaultLogListener());
        this.client = client;
    }

    public void start() {
        client.connect();
    }

    public void exit() {
        client.stop();
    }


}
