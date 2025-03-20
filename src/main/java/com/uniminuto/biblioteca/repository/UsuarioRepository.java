package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface UsuarioRepository extends
        JpaRepository<Usuario, Integer>  {

     Optional<Usuario> findByCorreo(String correo);
}
    
//revisar le LibroRepository for document