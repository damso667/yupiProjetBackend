package com.example.yupiProjet.Dto;

import com.example.yupiProjet.Services.StatistiqueService;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BeneficeStatsDto {
    private StatistiqueService.Periode periode;
    private BigDecimal benefice;
    private int nombreProduitsVendus;
}