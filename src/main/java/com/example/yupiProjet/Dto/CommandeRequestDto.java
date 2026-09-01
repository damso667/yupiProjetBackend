package com.example.yupiProjet.Dto;



import com.example.yupiProjet.Enum.ModeLivraison;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CommandeRequestDto {
    private Long clientId;
    private LocalDate dateLivraison;
    private String lieuLivraison;
    private ModeLivraison modeLivraison;

    // Utilisé UNIQUEMENT si modeLivraison = MOI_MEME.
    // Ignoré côté service si modeLivraison = LIVREUR (frais imposé par la constante).
    private BigDecimal fraisLivraison;

    private List<LigneCommandeRequestDto> lignes;
}