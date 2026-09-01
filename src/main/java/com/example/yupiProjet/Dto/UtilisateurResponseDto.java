package com.example.yupiProjet.Dto;

import com.example.yupiProjet.Enum.Role;
import com.example.yupiProjet.Models.Utilisateur;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UtilisateurResponseDto {
    private Long id;
    private String nom;
    private String number;
    private String email;
    private String password;

    private Role role = Role.VENDEUR;

    public static UtilisateurResponseDto of(Utilisateur utilisateur) {
        UtilisateurResponseDto dto = new UtilisateurResponseDto();
        dto.setId(utilisateur.getId());
        dto.setNom(utilisateur.getNom());
        dto.setNumber(utilisateur.getNumber());
        dto.setEmail(utilisateur.getEmail());
        dto.setPassword(utilisateur.getPassword());
        dto.setRole(utilisateur.getRole());
        return dto;
    }


}
