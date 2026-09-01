package com.example.yupiProjet.Models;


import com.example.yupiProjet.Enum.ModeLivraison;
import com.example.yupiProjet.Enum.StatutCommande;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commande")
@Getter @Setter
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_livraison", nullable = false)
    private ModeLivraison modeLivraison;

    @Column(name = "frais_livraison", nullable = false, precision = 12, scale = 2)
    private BigDecimal fraisLivraison;

    @Column(name = "date_livraison_effective")
    private LocalDate dateLivraisonEffective;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "date_livraison", nullable = false)
    private LocalDate dateLivraison;

    @Column(name = "lieu_livraison", nullable = false)
    private String lieuLivraison;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommande statut = StatutCommande.PLANIFIE;

    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommande> lignes = new ArrayList<>();

}