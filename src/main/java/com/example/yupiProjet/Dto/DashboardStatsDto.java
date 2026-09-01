package com.example.yupiProjet.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DashboardStatsDto {
    private BigDecimal beneficeTotal;
    private int nombreProduitsVendus;
    private BigDecimal pvTotal; // ← changé (était int)
    private List<PointHistoriqueDto> historique;
    private List<CommandeResponseDto> prochainsRendezVous;
}
