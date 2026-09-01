package com.example.yupiProjet.Dto;


import com.example.yupiProjet.Enum.ModeLivraison;
import com.example.yupiProjet.Enum.StatutCommande;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CommandeResponseDto {
    private Long id;
    private ClientDto client;
    private LocalDate dateLivraison;
    private String lieuLivraison;
    private LocalDate dateLivraisonEffective;
    private StatutCommande statut;
    private ModeLivraison modeLivraison;
    private BigDecimal fraisLivraison;
    private List<LigneCommandeResponseDto> lignes;

    // Bénéfice NET = somme des bénéfices lignes - frais de livraison
    private BigDecimal beneficeTotal;
}