package com.distribuidos.auto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patente;
    private String color;
    private int modelo;

    public Auto() {
    }

    public Auto(Long id, String patente, String color, int modelo) {
        this.id = id;
        this.patente = patente;
        this.color = color;
        this.modelo = modelo;
    }
}
