package com.example.yupiProjet.Services;

import com.example.yupiProjet.Données.LivraisonConstantes;
import com.example.yupiProjet.Enum.ModeLivraison;
import com.example.yupiProjet.Enum.StatutCommande;
import com.example.yupiProjet.Enum.Statut;
import com.example.yupiProjet.Models.*;
import com.example.yupiProjet.Repository.*;
import org.springframework.stereotype.Service;
import com.example.yupiProjet.Dto.CommandeRequestDto;
import com.example.yupiProjet.Dto.CommandeResponseDto;
import com.example.yupiProjet.Dto.LigneCommandeRequestDto;
import com.example.yupiProjet.Dto.CommandeMapper;
import com.example.yupiProjet.Models.*;
import com.example.yupiProjet.Repository.ClientRepository;
import com.example.yupiProjet.Repository.CommandeRepository;
import com.example.yupiProjet.Repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;



import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final CommandeMapper commandeMapper;

    public CommandeResponseDto creer(CommandeRequestDto requete) {
        Client client = clientRepository.findById(requete.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDateLivraison(requete.getDateLivraison());
        commande.setLieuLivraison(requete.getLieuLivraison());
        commande.setStatut(StatutCommande.PLANIFIE);
        commande.setModeLivraison(requete.getModeLivraison());
        commande.setFraisLivraison(resoudreFraisLivraison(requete));

        List<LigneCommande> lignes = requete.getLignes().stream()
                .map(ligneDto -> creerLigne(ligneDto, commande))
                .collect(Collectors.toList());
        commande.setLignes(lignes);

        Commande sauvegardee = commandeRepository.save(commande);
        return commandeMapper.toDto(sauvegardee);
    }

    /**
     * LIVREUR  -> frais imposé par la constante, toute valeur envoyée par le client est ignorée
     * MOI_MEME -> frais obligatoire, fourni par l'utilisateur
     */
    private BigDecimal resoudreFraisLivraison(CommandeRequestDto requete) {
        if (requete.getModeLivraison() == null) {
            throw new IllegalArgumentException("Le mode de livraison est obligatoire");
        }

        if (requete.getModeLivraison() == ModeLivraison.LIVREUR) {
            return LivraisonConstantes.FRAIS_LIVREUR_FIXE;
        }

        // MOI_MEME
        if (requete.getFraisLivraison() == null || requete.getFraisLivraison().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Les frais de livraison sont obligatoires et doivent être positifs en mode MOI_MEME");
        }
        return requete.getFraisLivraison();
    }

    private LigneCommande creerLigne(LigneCommandeRequestDto dto, Commande commande) {
        Produits produit = produitRepository.findById(dto.getProduitId())
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable : " + dto.getProduitId()));

        LigneCommande ligne = new LigneCommande();
        ligne.setCommande(commande);
        ligne.setProduit(produit);
        ligne.setQuantite(dto.getQuantite());
        ligne.setPrixVenteUnitaire(dto.getPrixVenteUnitaire());
        return ligne;
    }

    public CommandeResponseDto changerStatut(Long id, StatutCommande nouveauStatut) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));

        validerTransition(commande.getStatut(), nouveauStatut);

        if (nouveauStatut == StatutCommande.LIVRE) {
            commande.setDateLivraisonEffective(LocalDate.now());
        }

        commande.setStatut(nouveauStatut);
        return commandeMapper.toDto(commandeRepository.save(commande));
    }

    private void validerTransition(StatutCommande actuel, StatutCommande nouveau) {
        if (actuel == StatutCommande.LIVRE || actuel == StatutCommande.ANNULE) {
            throw new IllegalStateException(
                    "Impossible de modifier une commande déjà " + actuel + " (état terminal)");
        }
        if (actuel == StatutCommande.PLANIFIE
                && nouveau != StatutCommande.LIVRE
                && nouveau != StatutCommande.ANNULE) {
            throw new IllegalStateException("Transition invalide : " + actuel + " → " + nouveau);
        }
    }

    public List<CommandeResponseDto> lister() {
        return commandeRepository.findAll().stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }
}