package kz.moltenhaze.web.service;

import kz.moltenhaze.lobbybot.DTO.TeamSlotDTO;
import kz.moltenhaze.lobbybot.DotaBot;
import kz.moltenhaze.lobbybot.managers.DotaBotManager;
import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbySetDetails;
import kz.moltenhaze.proto.dota.DotaSharedEnums.DOTASelectionPriorityRules;
import kz.moltenhaze.proto.dota.DotaSharedEnums.DOTALobbyVisibility;
import kz.moltenhaze.web.DTO.LobbyOptionsDTO;
import kz.moltenhaze.web.DTO.LobbyOptionsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DotaBotService {
    private DotaBotManager botManager;

    public void start() {
        botManager.getCurrentBot().start();
    }

    public void destroy() {
        botManager.getCurrentBot().getLobbyManager().destroyLobby();
    }

    public void launchLobby() {
        botManager.getCurrentBot().getLobbyManager().launchLobby();
    }

    public void createBot() {
        botManager.createNewBot();
    }



    public void createLobby(Long messageId, LobbyOptionsDTO lobbyOptionsDTO) {
        LobbyOptionsMapper mapper = new LobbyOptionsMapper();

        botManager.getCurrentBot().getLobbyManager().createLobby(mapper.toBuilder(lobbyOptionsDTO));
    }

    public void configureLobby(Long messageId, LobbyOptionsDTO lobbyOptionsDTO) {

    }

}
