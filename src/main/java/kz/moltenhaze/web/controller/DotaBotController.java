package kz.moltenhaze.web.controller;

import kz.moltenhaze.lobbybot.DTO.TeamSlotDTO;
import kz.moltenhaze.web.DTO.LobbyOptionsDTO;
import kz.moltenhaze.web.service.DotaBotService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dotabot")
public class DotaBotController {
    private DotaBotService dotaBotService;


    @PostMapping("/createlobby")
    public ResponseEntity<Void> createLobby(@RequestParam Long messageId, @RequestBody LobbyOptionsDTO lobbyOptionsDTO) {
        dotaBotService.createLobby(messageId, lobbyOptionsDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/start")
    public ResponseEntity<Void> start() {
        dotaBotService.start();

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/destroy")
    public ResponseEntity<Void> destroy() {
        dotaBotService.destroy();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/launchlobby")
    public ResponseEntity<Void> launchLobby() {
        dotaBotService.launchLobby();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/createbot")
    public ResponseEntity<Void> createBot() {
        dotaBotService.createBot();

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
