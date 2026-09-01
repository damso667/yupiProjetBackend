package com.example.yupiProjet.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PointHistoriqueDto {
    private String label;
    private BigDecimal benefice;
}
