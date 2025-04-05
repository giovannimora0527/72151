package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;
=======
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579

/**
 *
 * @author lmora
 */
<<<<<<< HEAD
@Repository
public interface UsuarioRepository extends
        JpaRepository<Usuario, Integer>  {

     Optional<Usuario> findByCorreo(String correo);
}
    
//revisar le LibroRepository for document
=======
public interface UsuarioRepository extends
        JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario dado un email.
     *
     * @param correo email de entrada.
     * @return Usuario que cumpla con el criterio.
     */
    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    boolean existsByCorreo(String correo);

}
>>>>>>> 7d718bb03f5b91b34a27e06cc30dda87d7342579
