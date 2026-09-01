package com.example.yupiProjet.Controller;

import com.example.yupiProjet.Dto.ClientDto;
import com.example.yupiProjet.Dto.ProduitDto;
import com.example.yupiProjet.Dto.UtilisateurDto;
import com.example.yupiProjet.Dto.UtilisateurResponseDto;
import com.example.yupiProjet.Enum.Role;
import com.example.yupiProjet.Models.Client;
import com.example.yupiProjet.Models.Utilisateur;
import com.example.yupiProjet.Repository.UtilisateurRepository;
import com.example.yupiProjet.Services.UtilisateurServie;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/utilisateur")
public class UtilisateurController {
    private final UtilisateurServie utilisateurServie;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UtilisateurDto utilisateurDto) {
        if (utilisateurRepository.findByEmail(utilisateurDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email déjà utilisé");
        }
        Utilisateur personne = new Utilisateur();
        personne.setNom(utilisateurDto.getNom());
        personne.setPassword(passwordEncoder.encode(utilisateurDto.getPassword()));
        personne.setNumber(utilisateurDto.getNumber());
        personne.setRole(Role.VENDEUR);
        personne.setEmail(utilisateurDto.getEmail());


        utilisateurServie.registerUser(personne);
        return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur enregistré avec succès");

    }


    @GetMapping(value = "/produits")
    private ResponseEntity<List<ProduitDto>> getProduit() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        return ResponseEntity.ok(utilisateurServie.getProduit());
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<List<ClientDto>> getClients() {
        return ResponseEntity.ok(utilisateurServie.getClients());


    }

}
