package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
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
    public List<Nacionalidad> obtenerNacionalidadesDesdeAutores() throws BadRequestException {
        return autorRepository.listarNacionalidadesDeAutores();
    }
    
    @Override
    public List<Autor> listarAutoresPorNacionalidad(Integer nacionalidadId) {
        return autorRepository.findByNacionalidad_NacionalidadId(nacionalidadId);
    }
    
    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidadId(String nacionalidadId) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   @Override
    public AutorRs guardarAutorNuevo(AutorRq autor) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findByNombre(autor.getNombre());

        if (optAutor.isPresent()) {
            throw new BadRequestException("El autor ya existe con el nombre "
                    + autor.getNombre()
                    + ", verifique e intente de nuevo.");
        }

        Nacionalidad nacionalidad = nacionalidadRepository.findById(autor.getNacionalidadId())
                .orElseThrow(() -> new BadRequestException("Nacionalidad no encontrada"));

        Autor nuevoAutor = new Autor();
        nuevoAutor.setNombre(autor.getNombre());
        nuevoAutor.setFechaNacimiento(autor.getFechaNacimiento());
        nuevoAutor.setNacionalidad(nacionalidad);

        this.autorRepository.save(nuevoAutor);

        AutorRs rta = new AutorRs();
        rta.setMessage("Se ha guardado el autor con éxito.");
        return rta;
    }

    @Override
    public AutorRs actualizarAutor(AutorRq autor) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autor.getAutorId());

        if (!optAutor.isPresent()) {
            throw new BadRequestException("No existe el autor.");
        }

        Autor autorActual = optAutor.get();

        if (!cambioObjeto(autorActual, autor)) {
            AutorRs sinCambios = new AutorRs();
            sinCambios.setMessage("No se realizaron cambios en el autor.");
            return sinCambios;
        }

        if (!autor.getNombre().equals(autorActual.getNombre())
                && this.autorRepository.existsByNombre(autor.getNombre())) {
            throw new BadRequestException("El nombre del autor: " + autor.getNombre()
                    + ", ya existe en la base de datos. Verifique e intente de nuevo.");
        }

        Nacionalidad nacionalidad = nacionalidadRepository.findById(autor.getNacionalidadId())
                .orElseThrow(() -> new BadRequestException("Nacionalidad no encontrada"));

        autorActual.setNombre(autor.getNombre());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());
        autorActual.setNacionalidad(nacionalidad);

        this.autorRepository.save(autorActual);

        AutorRs rta = new AutorRs();
        rta.setMessage("El autor se actualizó correctamente.");
        return rta;
    }

    private boolean cambioObjeto(Autor actual, AutorRq nuevo) {
    return !actual.getNombre().equals(nuevo.getNombre())
            || !actual.getFechaNacimiento().equals(nuevo.getFechaNacimiento())
            || !actual.getNacionalidad().getNacionalidadId().equals(nuevo.getNacionalidadId());
}
}
