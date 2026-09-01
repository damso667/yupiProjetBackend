package com.example.yupiProjet.Données;

import com.example.yupiProjet.Models.Produits;
import com.example.yupiProjet.Models.Utilisateur;
import com.example.yupiProjet.Repository.ProduitRepository;
import com.example.yupiProjet.Repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@Component
public class CreationUtilisateur implements CommandLineRunner {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProduitRepository produitRepository;

    @Override
    public void run(String... args) throws Exception {

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("Adrien");
        utilisateur.setEmail("njassineadrien@gmail.com");
        utilisateur.setPassword(passwordEncoder.encode("adrien667"));

        Optional<Utilisateur> existingUser = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (existingUser.isEmpty()) {
            utilisateurRepository.save(utilisateur);
        }


        creerProduitSiInexistant("COSTI AWAY", 1800, 10500, BigDecimal.valueOf(10));
        creerProduitSiInexistant("Alka Plus", 2200, 15000, BigDecimal.valueOf(12.5));
        creerProduitSiInexistant("IMMUNO BOOST (30)", 1080, 7500, BigDecimal.valueOf(6));
        creerProduitSiInexistant("IMMUNO BOOST (60)", 2250, 15000, BigDecimal.valueOf(12.5));
        creerProduitSiInexistant("DETOX HEALT(30)", 1080, 7500, BigDecimal.valueOf(12.5));
        creerProduitSiInexistant("GOLDEN PAIN", 900, 7000, BigDecimal.valueOf(6));
        creerProduitSiInexistant("WOMEN CARE", 1800, 15000, BigDecimal.valueOf(10));
        creerProduitSiInexistant("MEN POWER OIL", 720, 5000, BigDecimal.valueOf(4));
        creerProduitSiInexistant("PILON CARE", 1800, 12500, BigDecimal.valueOf(10));
        creerProduitSiInexistant("MEN POWER MALT", 2250, 15000, BigDecimal.valueOf(12.5));
        creerProduitSiInexistant("DIABO CARE", 1800, 15000, BigDecimal.valueOf(10));
        creerProduitSiInexistant("DENTAL DROP", 1800, 2000, BigDecimal.valueOf(1));
        creerProduitSiInexistant("YUPI PREMIX CAFFEE", 1440, 10000, BigDecimal.valueOf(8));
        creerProduitSiInexistant("DIABO SPRAY", 900, 7000, BigDecimal.valueOf(5));
        creerProduitSiInexistant("SEA BUCKOM JUICE", 2250, 15000, BigDecimal.valueOf(12.5));
        creerProduitSiInexistant("PAIN AND COLD BAIM", 180, 2000, BigDecimal.valueOf(1));
    }

    // Méthode utilitaire pour éviter la répétition inutile de code
    private void creerProduitSiInexistant(String nom, long pv, long prixYupi, BigDecimal nbPv) {
        if (produitRepository.findByNomIgnoreCase(nom).isEmpty()) {
            Produits produit = new Produits();
            produit.setNom(nom);
            produit.setValeurPvFcfa(BigDecimal.valueOf(pv));
            produit.setPrixYupi(BigDecimal.valueOf(prixYupi));
            produit.setNombrePv(nbPv);
            
            produitRepository.save(produit);
        }
    }
}