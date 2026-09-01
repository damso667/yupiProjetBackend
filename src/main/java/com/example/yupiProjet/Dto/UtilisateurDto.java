package com.example.yupiProjet.Dto;

import com.example.yupiProjet.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UtilisateurDto {
    private Long id;
    private String nom;
    private String number;
    private String email;
    private String password;
    private Role role;
}
