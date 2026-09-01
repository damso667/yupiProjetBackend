package com.example.yupiProjet.Dto;

import com.example.yupiProjet.Models.Produits;
import com.example.yupiProjet.Models.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProduitDto {
    private Long idProduit;
    private String nomProduit;
    private BigDecimal pv;
    private BigDecimal Npv;

    private BigDecimal prixVente;

    public static ProduitDto of(Produits produits) {
        ProduitDto dto = new ProduitDto();
        dto.setIdProduit(produits.getId());
        dto.setNomProduit(produits.getNom());
        dto.setPv(produits.getValeurPvFcfa());
        dto.setPrixVente(produits.getPrixYupi());
        dto.setNpv(produits.getNombrePv());
        return dto;
    }
}
