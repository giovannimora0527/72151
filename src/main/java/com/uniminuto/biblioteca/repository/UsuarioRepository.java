package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
<<<<<<< HEAD
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 
 *
 * @author 853345_MiguelRayo
 */
@Repository
public interface UsuarioRepository extends
        JpaRepository<Usuario, Integer>  {
    
    List<Usuario> findByCorreo(String correo);
=======
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author lmora
 */
public interface UsuarioRepository extends
        JpaRepository<Usuario, Integer> {
    
    /**
     * Busca un usuario dado un email.
     * @param correo email de entrada.
     * @return Usuario que cumpla con el criterio.
     */
    Optional<Usuario> findByCorreo(String correo);
    
    Optional<Usuario> findByNombre(String nombre);
    
>>>>>>> db945afcaf24de6d8d8b2c3cf58500c5f3e028e7
}
