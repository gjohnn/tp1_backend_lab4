package com.example.demo.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name = "empresas")
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 128, nullable = false)
    private String denominacion;
    
    @Column(length = 50)
    private String telefono;
    
    @Column(length = 256)
    private String horarioAtencion;
    
    @Column(length = 1024)
    private String quienesSomos;
    
    @Column(precision = 10, scale = 6)
    private BigDecimal latitud;
    
    @Column(precision = 10, scale = 6)
    private BigDecimal longitud;
    
    @Column(length = 256)
    private String domicilio;
    
    @Column(length = 75, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;
    
}
