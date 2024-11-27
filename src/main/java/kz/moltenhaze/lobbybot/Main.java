package kz.moltenhaze.lobbybot;

import in.dragonbra.javasteam.networking.steam3.ProtocolTypes;
import in.dragonbra.javasteam.steam.steamclient.configuration.SteamConfiguration;
import in.dragonbra.javasteam.util.log.DefaultLogListener;
import in.dragonbra.javasteam.util.log.LogManager;
import kz.moltenhaze.lobbybot.clients.Dota2Client;
import kz.moltenhaze.lobbybot.handlers.dota2.Dota2Lobby;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement;
import kz.moltenhaze.proto.dota.DotaSharedEnums;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    test();

    }

    public static void test() throws InterruptedException, ExecutionException {
        LogManager.addListener(new DefaultLogListener());


    }
}
