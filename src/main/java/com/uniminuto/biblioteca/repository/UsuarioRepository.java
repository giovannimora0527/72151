package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 *
 * @author lmora
 */
@Repository
public interface UsuarioRepository extends
                JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);   
}
