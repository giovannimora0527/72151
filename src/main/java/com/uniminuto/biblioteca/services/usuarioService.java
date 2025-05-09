    package com.uniminuto.biblioteca.services;

    import com.uniminuto.biblioteca.entity.Usuario;
    import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
    import com.uniminuto.biblioteca.model.UsuarioRq;
    import com.uniminuto.biblioteca.model.UsuarioRs;
    import java.util.List;
    import java.util.Optional; // Importar Optional
    import org.apache.coyote.BadRequestException;

    /**
     * Interfaz del servicio para la gestión de usuarios.
     * Define las operaciones disponibles para los usuarios.
     * @author lmora
     */
    public interface UsuarioService {

        /**
         * Servicio para listar todos los usuarios del sistema.
         * @return Lista de usuarios registrados.
         * @throws BadRequestException Excepcion.
         */
        List<Usuario> listarTodo() throws BadRequestException;

        /**
         * Busca un usuario dado un email.
         * @param correo email a buscar.
         * @return Usuario.
         * @throws BadRequestException excepcion.
         */
        Usuario buscarPorCorreo(String correo) throws BadRequestException;


        UsuarioRs guardarUsuario(UsuarioRq usuario) throws BadRequestException;

        RespuestaGenericaRs actualizarUsuario(Usuario usuario) throws BadRequestException;

        // *** CORRECCIÓN: Añadir la declaración del método findById ***
        /**
         * Busca un usuario dado su ID.
         * @param id ID del usuario.
         * @return Optional que contiene el Usuario si se encuentra, o vacío si no.
         */
        Optional<Usuario> findById(Integer id);

    }
    