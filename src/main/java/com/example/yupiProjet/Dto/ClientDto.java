package com.example.yupiProjet.Dto;


import lombok.Data;

@Data
public class ClientDto {
    private Long id;
    private String nom;
    private String telephone;
    private String adresse;
}