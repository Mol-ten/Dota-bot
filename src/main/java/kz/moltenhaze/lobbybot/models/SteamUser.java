package kz.moltenhaze.lobbybot.models;

import in.dragonbra.javasteam.base.ClientMsgProtobuf;
import in.dragonbra.javasteam.base.IPacketMsg;
import in.dragonbra.javasteam.enums.EMsg;
import in.dragonbra.javasteam.protobufs.steamclient.SteammessagesClientserver.CMsgClientGamesPlayed;
import in.dragonbra.javasteam.steam.handlers.ClientMsgHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SteamUser extends ClientMsgHandler {
    private Map<EMsg, Consumer<IPacketMsg>> dispatchMap;
    private Set<Integer> currentGamesPlayed;

    public SteamUser() {
        dispatchMap = new HashMap<>();
        currentGamesPlayed = new HashSet<>();
    }
    @Override
    public void handleMsg(IPacketMsg iPacketMsg) {
        Consumer<IPacketMsg> dispatcher = dispatchMap.get(iPacketMsg.getMsgType());

        if (dispatcher != null) {
            dispatcher.accept(iPacketMsg);
        }
    }

    public void setCurrentGamePlayed(Integer appId) {
        ClientMsgProtobuf<CMsgClientGamesPlayed.Builder> gamesPlayed = new ClientMsgProtobuf<>(CMsgClientGamesPlayed.class,
                EMsg.ClientGamesPlayed);

        gamesPlayed.getBody().addGamesPlayedBuilder().setGameId(appId);

        client.send(gamesPlayed);
        currentGamesPlayed.add(appId);
    }

    public Set<Integer> getCurrentGamesPlayed() {
        return currentGamesPlayed;
    }
}
