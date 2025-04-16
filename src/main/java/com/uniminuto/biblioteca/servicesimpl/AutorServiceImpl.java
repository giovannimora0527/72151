package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.Comparator;
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

    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoAsc();
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
        Optional<Autor> optAutor = this.autorRepository.findByNombre(autor.getNombre());
        if (optAutor.isPresent()) {
            throw new BadRequestException("El autor ya se encuentra registrado. Intente de nuevo.");
        }

        Autor autorToSave = this.transformarAutorRqToAutor(autor);
        this.autorRepository.save(autorToSave);

        AutorRs rta = new AutorRs();
        rta.setMessage("El autor se ha creado satisfactoriamente.");
        return rta;
    }

    private Autor transformarAutorRqToAutor(AutorRq autor) {
        Autor autorEntity = new Autor();
        autorEntity.setNombre(autor.getNombre());
        autorEntity.setFechaNacimiento(autor.getFechaNacimiento());
        autorEntity.setNacionalidad(autor.getNacionalidad());
        return autorEntity;
    }

    @Override
    public RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autor.getAutorId());
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No existe el autor.");
        }

        Autor autorActual = optAutor.get();
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha actualizado el registro satisfactoriamente");
        if (!compararObjetosAutorActualizar(autorActual, autor)) {
            return rta;
        }

        if (!autorActual.getNombre().equals(autor.getNombre())) {
            if (this.autorRepository.existsByNombre(autor.getNombre())) {
                throw new BadRequestException("El autor " + autor.getNombre() + ", existe en la bd. por favor verifique.");
            }
        }

        autorActual.setNombre(autor.getNombre());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());
        autorActual.setNacionalidad(autor.getNacionalidad());
        this.autorRepository.save(autorActual);
        return rta;
    }

    private boolean compararObjetosAutorActualizar(Autor autorActual, Autor autorFront) {
        return !autorActual.getNombre().equals(autorFront.getNombre())
                || !autorActual.getFechaNacimiento().equals(autorFront.getFechaNacimiento())
                || !autorActual.getNacionalidad().equals(autorFront.getNacionalidad());
    }

}
