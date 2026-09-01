package com.example.yupiProjet.Services;

import com.example.yupiProjet.Dto.*;
import com.example.yupiProjet.Enum.Role;
import com.example.yupiProjet.Models.Client;
import com.example.yupiProjet.Models.Produits;
import com.example.yupiProjet.Models.Utilisateur;
import com.example.yupiProjet.Repository.ClientRepository;
import com.example.yupiProjet.Repository.ProduitRepository;
import com.example.yupiProjet.Repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UtilisateurServie {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProduitRepository produitRepository;
    private final ClientRepository clientRepository;
    private final CommandeMapper commandeMapper;

    public Utilisateur registerUser(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public List<ProduitDto> getProduit(){
        return produitRepository.findAll().stream().map(ProduitDto::of).toList();
    }
    public Client registerCli(Client client) {
        return clientRepository.save(client);
    }

    public List<ClientDto> getClients() {
        return clientRepository.findAll().stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }
}
