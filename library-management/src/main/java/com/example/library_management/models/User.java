package com.example.library_management.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Modelo que representa a un usuario de la plataforma.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Correo electrónico único del usuario.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Nombre del usuario.
     */
    @Column(nullable = false)
    private String name;
}

