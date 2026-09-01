package com.example.yupiProjet.Dto;




import com.example.yupiProjet.Models.Client;
import com.example.yupiProjet.Models.Commande;
import com.example.yupiProjet.Models.LigneCommande;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandeMapper {

    public ClientDto toDto(Client client) {
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setNom(client.getNom());
        dto.setTelephone(client.getTelephone());
        dto.setAdresse(client.getAdresse());
        return dto;
    }

    public LigneCommandeResponseDto toDto(LigneCommande ligne) {
        LigneCommandeResponseDto dto = new LigneCommandeResponseDto();
        dto.setProduitNom(ligne.getProduit().getNom());
        dto.setQuantite(ligne.getQuantite());
        dto.setPrixVenteUnitaire(ligne.getPrixVenteUnitaire());
        dto.setBeneficeLigne(ligne.getBeneficeLigne());
        return dto;
    }

    public CommandeResponseDto toDto(Commande commande) {
        CommandeResponseDto dto = new CommandeResponseDto();
        dto.setId(commande.getId());
        dto.setClient(toDto(commande.getClient()));
        dto.setDateLivraison(commande.getDateLivraison());
        dto.setLieuLivraison(commande.getLieuLivraison());
        dto.setStatut(commande.getStatut());
        dto.setModeLivraison(commande.getModeLivraison());
        dto.setDateLivraisonEffective(commande.getDateLivraisonEffective());
        dto.setFraisLivraison(commande.getFraisLivraison());

        List<LigneCommandeResponseDto> lignesDto = commande.getLignes().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        dto.setLignes(lignesDto);

        BigDecimal beneficeBrut = commande.getLignes().stream()
                .map(LigneCommande::getBeneficeLigne)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal beneficeNet = beneficeBrut.subtract(commande.getFraisLivraison());
        dto.setBeneficeTotal(beneficeNet);

        return dto;
    }
}
