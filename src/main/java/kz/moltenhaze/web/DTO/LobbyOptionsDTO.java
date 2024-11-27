package kz.moltenhaze.web.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LobbyOptionsDTO {
    private String lobbyName;
    private int gameServer;
    private int gameMode;
    private boolean isCheatsAllowed;
    private boolean isImmortalDraft;
    private int lobbyVisibility;
    private String passKey;
    private int selectionPriorityRules;
}
