package com.example.yupiProjet.Repository;

import com.example.yupiProjet.Enum.StatutCommande;
import com.example.yupiProjet.Models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByStatut(StatutCommande statut);

    @Query("SELECT c FROM Commande c WHERE c.statut = :statut " +
            "AND c.dateLivraisonEffective BETWEEN :debut AND :fin")
    List<Commande> findLivreesEntre(StatutCommande statut, LocalDate debut, LocalDate fin);

    // À ajouter dans CommandeRepository
    List<Commande> findByStatutOrderByDateLivraisonAsc(StatutCommande statut);
}
