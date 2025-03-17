package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
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
}
