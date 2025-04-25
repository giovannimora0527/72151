package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author lmora
 */
@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

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
    public Autor crearAutor(Autor autor) {   
        Optional<Autor> existente = autorRepository.findByNombreIgnoreCase(autor.getNombre());
        if (existente.isPresent()) {
            throw new ResponseStatusException (HttpStatus.BAD_REQUEST, "El autor con el nombre '" + autor.getNombre() + "' ya existe.");
        }   
        return autorRepository.save(autor);
}

    @Override
    public Autor actualizarAutor(Integer id, Autor autor)  {
    Optional<Autor> existente = autorRepository.findById(id);
    if (!existente.isPresent()) {
        throw new RuntimeException("Autor no encontrado con id " + id);
    }
    
    Optional<Autor> autorConMismoNombre = autorRepository.findByNombreIgnoreCase(autor.getNombre());
    if (autorConMismoNombre.isPresent() && !autorConMismoNombre.get().getAutorId().equals(id)) {
        throw new RuntimeException("Ya existe otro autor con el nombre '" + autor.getNombre() + "'.");
    }
    
    Autor autorExistente = existente.get();
    autorExistente.setNombre(autor.getNombre());
    autorExistente.setNacionalidad(autor.getNacionalidad());
    autorExistente.setFechaNacimiento(autor.getFechaNacimiento());
    return autorRepository.save(autorExistente);
}
}
