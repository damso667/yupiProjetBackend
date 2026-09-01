package com.example.yupiProjet.Controller;

import com.example.yupiProjet.Dto.CommandeRequestDto;
import com.example.yupiProjet.Enum.StatutCommande;
import com.example.yupiProjet.Services.CommandeService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import com.example.yupiProjet.Dto.CommandeResponseDto;


import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public CommandeResponseDto creer(@RequestBody CommandeRequestDto requete) {
        return commandeService.creer(requete);
    }

    @GetMapping
    public List<CommandeResponseDto> lister() {
        return commandeService.lister();
    }

    @PutMapping("/{id}/statut")
    public CommandeResponseDto changerStatut(@PathVariable Long id, @RequestParam StatutCommande statut) {
        return commandeService.changerStatut(id, statut);
    }
}