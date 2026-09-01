package com.example.yupiProjet.Dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LigneCommandeResponseDto {
    private String produitNom;
    private Integer quantite;
    private BigDecimal prixVenteUnitaire;
    private BigDecimal beneficeLigne;
}