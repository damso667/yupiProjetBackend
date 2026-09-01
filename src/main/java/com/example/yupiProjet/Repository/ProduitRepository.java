package com.example.yupiProjet.Repository;

import com.example.yupiProjet.Models.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produits,Long> {
    Optional<Produits> findByNomIgnoreCase(String nom);
}
