package com.example.yupiProjet.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "ligne_commande")
@Getter @Setter
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produit_id")
    private Produits produit;

    @Column(nullable = false)
    private Integer quantite;

    // Prix auquel TOI tu as vendu (librement fixé, >= prixYupi en principe)
    @Column(name = "prix_vente_unitaire", nullable = false, precision = 12, scale = 2)
    private BigDecimal prixVenteUnitaire;

    /**
     * Bénéfice de cette ligne = ((prixVente - prixYupi) + valeurPvFcfa) * quantité
     */
    @Transient
    public BigDecimal getBeneficeLigne() {
        BigDecimal margeParUnite = prixVenteUnitaire
                .subtract(produit.getPrixYupi())
                .add(produit.getValeurPvFcfa());
        return margeParUnite.multiply(BigDecimal.valueOf(quantite));
    }
}