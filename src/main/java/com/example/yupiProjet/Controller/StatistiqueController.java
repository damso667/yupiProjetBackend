package com.example.yupiProjet.Controller;


import com.example.yupiProjet.Dto.BeneficeStatsDto;
import com.example.yupiProjet.Dto.CommandeMapper;
import com.example.yupiProjet.Dto.CommandeResponseDto;
import com.example.yupiProjet.Dto.DashboardStatsDto;
import com.example.yupiProjet.Enum.StatutCommande;
import com.example.yupiProjet.Repository.CommandeRepository;
import com.example.yupiProjet.Services.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatistiqueController {

    private final StatistiqueService statistiqueService;
    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;

    @GetMapping("/dashboard")
    public DashboardStatsDto dashboard(@RequestParam StatistiqueService.Periode periode) {
        DashboardStatsDto dto = new DashboardStatsDto();
        dto.setBeneficeTotal(statistiqueService.calculerBenefice(periode));
        dto.setNombreProduitsVendus(statistiqueService.calculerNombreProduitsVendus(periode));
        dto.setPvTotal(statistiqueService.calculerPvTotal(periode));
        dto.setHistorique(statistiqueService.genererHistorique(periode));

        List<CommandeResponseDto> prochains = commandeRepository
                .findByStatutOrderByDateLivraisonAsc(StatutCommande.PLANIFIE)
                .stream()
                .limit(5)
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
        dto.setProchainsRendezVous(prochains);

        return dto;
    }
}
