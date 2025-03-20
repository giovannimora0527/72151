package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import java.util.regex.Pattern;
import com.uniminuto.biblioteca.services.usuarioService;

/**
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements usuarioService {

    @Autowired //Permite acceder  a la bd a traves de los metodos del repository, se podria hacer con un contructor
    private UsuarioRepository usuariosRepository;

    @Override
    public List<Usuario> obtenerListadoDeUsuarios() {return this.usuariosRepository.findAll();}
    //Obtiene todos los usuarios
    
    @Override
    public Usuario obtenerUsuariosPorId(Integer id_usuario) {
        Optional<Usuario> usuario = this.usuariosRepository.findById(id_usuario); // findBy(valor) retornar optional si existe .get(), si no lanza una exepción con runTimeEx otra alternativa es lanzar una excepción personalidad
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new RuntimeException("ID del usuario no encontrado");
        }
    }

    @Override
    public Usuario obtenerUsuariosCorreo(String correo) { //Se valida el formato del correo con logic invers, si no es valida lanza una expeción y no avanza
        if (!correoValido(correo)) {throw new RuntimeException("El formato del correo no es valido"); }return usuariosRepository.findByCorreo(correo) //busca el usuarios con findByCorreo
                .orElseThrow(() -> new RuntimeException("El correo proporcionado no existe")); // si el usuarios no existe lanza una con or.Else indica otra expeción
}                //OrElseThrow hace parte del metodo de optional, si el correo no existe optional estara vacion y con orElseThrow lanza una exepción, pero si existe lo devuelve por el metodo
    //Metodo auxiliar para validar el formato del correo, ejemplo deben contener el @, tambien se puede agregar el (?i) dentro de " & ^ para que no sensitve a mys and minus
    private boolean correoValido(String correo) {
      String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
      return Pattern.matches(regex, correo);  } //Es del metodo pattern, se comprueba si una cadena cumple con un patron regular(regex), es la expresión regular defin el formt for cadenas
    // regex comprueba si cumple, lanzara un value boolean
}