package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NacionalidadRepository nacionalidadRepository;
    
    
    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }

    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad)
            throws BadRequestException {
        this.autorRepository.findByNacionalidad(nacionalidad).forEach(elem -> {
            System.out.println("Nombre Autor => " + elem.getNombre());
        });
        List<Autor> listaAutores = this.autorRepository.findByNacionalidad(nacionalidad);
        if (listaAutores.isEmpty()) {
            throw new BadRequestException("No existen autores con esa nacionalidad.");
        }
        
        return listaAutores;
    }

    @Override
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autorId);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + autorId);
        }
        return optAutor.get();
    }
     
    @Override
    public RespuestaGenericaRs crearAutor(AutorRq autorRq) throws BadRequestException  {
       // Paso 1. - en la bd si el autor existe por nombre
       // Paso 2. SI ESTA => lanzo el error
       // Paso 3. SINO esta Convertir mi objeto entrada rq a entidad autor
       // Paso 4. Guardo el registro
       // Paso 5. Devolver una respuesta
       if (this.autorRepository.existsByNombre(autorRq.getNombre())) {
           throw new BadRequestException("El autor se encuentra ya registrado");
       }
       
       Autor autor = this.convertirAutorRqToAutor(autorRq);
       this.autorRepository.save(autor);
       
       RespuestaGenericaRs rta = new RespuestaGenericaRs();
       rta.setMessage("Se ha guardado el libro satisfactoriamente");
       return rta;
    }
    
    private Autor convertirAutorRqToAutor(AutorRq autorRq) throws BadRequestException {
        Autor autor = new Autor();

        Optional<Nacionalidad> optNac = this.nacionalidadRepository.findById(autorRq.getNacionalidadId());
        if (!optNac.isPresent()) {
            throw new BadRequestException("No existe la nacionalidad");
        }
        
        autor.setNombre(autorRq.getNombre());
        autor.setFechaNacimiento(autorRq.getFechaNacimiento());
        autor.setNacionalidad(optNac.get());
        return autor;
    }  

    @Override
    public RespuestaGenericaRs actualizarAutor(Autor actualizarAutor) throws BadRequestException {
    Optional<Autor> optAutor = this.autorRepository.findById(actualizarAutor.getAutorId());
    if (!optAutor.isPresent()) {
        throw new BadRequestException("No existe el autor con el ID proporcionado.");
    }

    Autor autorActual = optAutor.get();

    // Verifica si hay cambios reales
    if (!hayCambiosEnAutor(autorActual, actualizarAutor)) {
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("No se detectaron cambios en el autor.");
        return rta;
    }

    // Si cambió el nombre, valida si ya existe otro autor con ese nombre
    if (!autorActual.getNombre().trim().equalsIgnoreCase(actualizarAutor.getNombre().trim())
            && this.autorRepository.existsByNombre(actualizarAutor.getNombre())) {
        throw new BadRequestException("Ya existe un autor con el nombre '" + actualizarAutor.getNombre() + "'.");
    }

    // Valida la nacionalidad
    if (actualizarAutor.getNacionalidad() == null || actualizarAutor.getNacionalidad().getNacionalidadId() == null) {
        throw new BadRequestException("Debe especificar una nacionalidad válida.");
    }

    Optional<Nacionalidad> optNac = this.nacionalidadRepository.findById(actualizarAutor.getNacionalidad().getNacionalidadId());
    if (!optNac.isPresent()) {
        throw new BadRequestException("No existe la nacionalidad con ID " + actualizarAutor.getNacionalidad().getNacionalidadId());
    }

    // Actualiza los campos
    autorActual.setNombre(actualizarAutor.getNombre());
    autorActual.setFechaNacimiento(actualizarAutor.getFechaNacimiento());

    // **Modificación para la nacionalidad:**
    Nacionalidad nacionalidad = new Nacionalidad();
    nacionalidad.setNacionalidadId(actualizarAutor.getNacionalidad().getNacionalidadId());
    autorActual.setNacionalidad(nacionalidad);

    this.autorRepository.save(autorActual);

    RespuestaGenericaRs rta = new RespuestaGenericaRs();
    rta.setMessage("Se ha actualizado el autor satisfactoriamente.");
    return rta;
}

    private boolean hayCambiosEnAutor(Autor actual, Autor nuevo) {
        if (nuevo.getNacionalidad() == null || actual.getNacionalidad() == null) {
            return true; // Si alguna de las nacionalidades es nula, consideramos que hay cambio
        }
    return !actual.getNombre().equals(nuevo.getNombre()) ||
           !actual.getFechaNacimiento().equals(nuevo.getFechaNacimiento()) ||
           !actual.getNacionalidad().getNacionalidadId().equals(nuevo.getNacionalidad().getNacionalidadId());
    }
    
    @Override
    public AutorRs guardarAutor(AutorRq autorRq) throws BadRequestException {
        // Podemos reutilizar la lógica de crearAutor y devolver un AutorRs si es necesario
        RespuestaGenericaRs respuesta = this.crearAutor(autorRq);
        AutorRs autorRs = new AutorRs();
        autorRs.setMessage(respuesta.getMessage());
        // Podrías también buscar y devolver el autor creado si AutorRs lo requiere
        return autorRs;
    }
}
    


