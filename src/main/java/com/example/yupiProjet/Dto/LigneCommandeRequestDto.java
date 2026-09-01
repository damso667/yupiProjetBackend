package com.example.yupiProjet.Dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LigneCommandeRequestDto {
    private Long produitId;
    private Integer quantite;
    private BigDecimal prixVenteUnitaire;
}