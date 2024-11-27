package kz.moltenhaze.lobbybot.models;

import kz.moltenhaze.proto.dota.DotaGcmessagesCommonLobby.CSODOTALobbyMember;
import kz.moltenhaze.proto.dota.DotaSharedEnums.DOTA_GC_TEAM;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class LobbyPlayer {
    private long accountId;
    private long playerId;
    private DOTA_GC_TEAM team;
    private int slot;

    public LobbyPlayer(CSODOTALobbyMember member) {
        this.accountId = member.getId();
        this.playerId = accountId - 76561197960265728L;
        this.team = member.getTeam();
        this.slot = member.getSlot();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyPlayer that = (LobbyPlayer) o;
        return accountId == that.accountId && slot == that.slot && team == that.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, team, slot);
    }
}
