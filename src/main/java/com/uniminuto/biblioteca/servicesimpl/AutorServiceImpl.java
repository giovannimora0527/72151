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
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autorId);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + autorId);
        }
        return optAutor.get();
    }

    @Override
    public AutorRs guardarAutor(AutorRq autor) throws BadRequestException {

        Autor authorToSave = this.transformarAutorRqToAutor(autor);
        this.autorRepository.save(authorToSave);

        AutorRs rta = new AutorRs();
        rta.setMessage("El autor se ha registrado correctamente.");

        return rta;
    }

    private Autor transformarAutorRqToAutor(AutorRq autor) {
        Autor author = new Autor();
        author.setNombre(autor.getNombre());
        author.setFechaNacimiento(autor.getFechaNacimiento());
        Optional<Nacionalidad> optNacionalidad = this.nacionalidadRepository
                .findById(autor.getNacionalidadId());
        if (!optNacionalidad.isPresent()) {
           // Lanza error
        }
        author.setNacionalidad(optNacionalidad.get());
        return author;
    }

    @Override
    public RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository
                .findById(autor.getAutorId());
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No existe el Autor.");
        }

        Autor autorActual = optAutor.get();
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha actualizado el registro satisfactoriamente");
        if (!compararObjetosAutorActualizar(autorActual, autor)) {
            return rta;
        }

        if (!autorActual.getNombre().equals(autor.getNombre())) {
            // El nombre cambio
            if (this.autorRepository.existsByNombre(autor.getNombre())) {
                throw new BadRequestException("El autor " + autor.getNombre() + ", existe en la bd. por favor verifique.");
            }
        }

        autorActual.setNombre(autor.getNombre());
        autorActual.setNacionalidad(autor.getNacionalidad());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());
        this.autorRepository.save(autorActual);
        return rta;
    }

    // Aca permite como tal actualizar los datos, si no esta mapeado los campos aca, no se van a actualizar
    private boolean compararObjetosAutorActualizar(Autor autorActual, Autor autorFront) {
        return !autorActual.getNombre().equals(autorFront.getNombre())
                || !autorActual.getNacionalidad().equals(autorFront.getNacionalidad())
                || !autorActual.getFechaNacimiento().equals(autorFront.getFechaNacimiento());
    }
}
