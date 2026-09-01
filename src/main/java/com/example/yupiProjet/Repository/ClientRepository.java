package com.example.yupiProjet.Repository;

import com.example.yupiProjet.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {

}
