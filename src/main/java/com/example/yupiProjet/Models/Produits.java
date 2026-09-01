package com.example.yupiProjet.Models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "produit")
@Getter @Setter
public class Produits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    // Prix fixé par Yupi Global (référence, non modifiable côté vente)
    @Column(name = "prix_yupi", nullable = false, precision = 12, scale = 2)
    private BigDecimal prixYupi;

    // Valeur du PV en FCFA pour ce produit (contribue directement au bénéfice)
    @Column(name = "valeur_pv_fcfa", nullable = false, precision = 12, scale = 2)
    private BigDecimal valeurPvFcfa;

    // Nombre de points PV (pour tes stats de grade, indépendant du bénéfice)
    @Column(name = "nombre_pv")
    private BigDecimal nombrePv;
}
