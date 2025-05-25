package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author lmora
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

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
    
    @Query("SELECT u FROM Usuario u WHERE u.idUsuario NOT IN (" +
       "SELECT m.usuario.idUsuario FROM Multa m WHERE m.estado = 'PENDIENTE')")
    List<Usuario> findUsuariosSinMultas();

}