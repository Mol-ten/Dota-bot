package kz.moltenhaze.web.service;

import kz.moltenhaze.lobbybot.DTO.TeamSlotDTO;
import kz.moltenhaze.lobbybot.DotaBot;
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
    private DotaBot dotaBot;

    public void start() {
        dotaBot.start();
    }

    public void destroy() {
        dotaBot.destroyLobby();
    }

    public void launchLobby() {
        dotaBot.launchLobby();
    }


    public void createLobby(Long messageId, LobbyOptionsDTO lobbyOptionsDTO) {
        LobbyOptionsMapper mapper = new LobbyOptionsMapper();

        dotaBot.createLobby(mapper.toBuilder(lobbyOptionsDTO));
    }

    public void configureLobby(Long messageId, LobbyOptionsDTO lobbyOptionsDTO) {

    }

}
