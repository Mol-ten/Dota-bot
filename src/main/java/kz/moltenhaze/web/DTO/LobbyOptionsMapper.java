package kz.moltenhaze.web.DTO;

import kz.moltenhaze.proto.dota.DotaGcmessagesClientMatchManagement.CMsgPracticeLobbySetDetails;
import kz.moltenhaze.proto.dota.DotaSharedEnums.DOTASelectionPriorityRules;
import kz.moltenhaze.proto.dota.DotaSharedEnums.DOTALobbyVisibility;

public class LobbyOptionsMapper {
    public LobbyOptionsDTO toDto(CMsgPracticeLobbySetDetails.Builder builder) {
        if (builder == null) {
            return null;
        }

        LobbyOptionsDTO dto = new LobbyOptionsDTO();
        dto.setLobbyName(builder.getGameName());
        dto.setGameMode(builder.getGameMode());
        dto.setLobbyVisibility(builder.getVisibility().getNumber());
        dto.setGameServer(builder.getServerRegion());
        dto.setCheatsAllowed(builder.getAllowCheats());
        dto.setImmortalDraft(builder.getDoPlayerDraft());
        dto.setSelectionPriorityRules(builder.getSelectionPriorityRules().getNumber());
        dto.setPassKey(builder.getPassKey());

        return dto;
    }

    public CMsgPracticeLobbySetDetails.Builder toBuilder(LobbyOptionsDTO dto) {
        if (dto == null) {
            return null;
        }

        CMsgPracticeLobbySetDetails.Builder builder = CMsgPracticeLobbySetDetails.newBuilder();
        builder.setGameName(dto.getLobbyName() != null ? dto.getLobbyName() : "RASSVET LOBBY " + Math.random());
        builder.setGameMode(dto.getGameMode());
        builder.setVisibility(DOTALobbyVisibility.forNumber(dto.getLobbyVisibility()));
        builder.setServerRegion(dto.getGameServer());
        builder.setAllowCheats(dto.isCheatsAllowed());
        builder.setDoPlayerDraft(dto.isImmortalDraft());
        builder.setSelectionPriorityRules(DOTASelectionPriorityRules.forNumber(dto.getSelectionPriorityRules()));
        builder.setPassKey(dto.getPassKey() != null ? dto.getPassKey() : Math.random() + "");

        return builder;
    }
}
