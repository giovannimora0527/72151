package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.AutorService;
import com.uniminuto.biblioteca.services.NacionalidadService;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
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
    private NacionalidadService nacionalidadService;
    
    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }

    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidad(Integer nacionalidad) throws BadRequestException {
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
    public RespuestaGenericaRs guardarAutor(AutorRq autor) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findByNombre(autor.getNombre());
        if (optAutor.isPresent()) {
            throw new BadRequestException("El autor ya se encuentra registrado. Intente de nuevo.");
        }

        Autor autorToSave = this.transformarAutorRqToAutor(autor);
        this.autorRepository.save(autorToSave);

        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("El autor se ha creado satisfactoriamente.");
        return rta;
    }

    private Autor transformarAutorRqToAutor(AutorRq autorRq) throws BadRequestException {
        Autor autor = new Autor();
        autor.setActivo(true);
        autor.setFechaNacimiento(autorRq.getFechaNacimiento());
        autor.setNombre(autorRq.getNombre());
        
        Optional<Nacionalidad> optNac = this.nacionalidadRepository.findById(autorRq.getNacionalidadId());
        if (!optNac.isPresent()) {
            throw new BadRequestException("No existe la nacionalidad");
        }
        
        Nacionalidad nacionalidad = optNac.get();
        autor.setNacionalidad(nacionalidad);
        return autor;
    }

    @Override
    public RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException {
        Optional<Autor> optAut = this.autorRepository.findById(autor.getAutorId());
        if (!optAut.isPresent()) {
            throw new BadRequestException("No existe el autor.");
        }

        Autor autorActual = optAut.get();
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
        
        Nacionalidad nacionalidad = new Nacionalidad();
        nacionalidad.setNacionalidadId(autor.getNacionalidad().getNacionalidadId());
        autorActual.setNacionalidad(nacionalidad);


        autorActual.setNombre(autor.getNombre());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());
        autorActual.setActivo(autor.getActivo());
        this.autorRepository.save(autorActual);
        return rta;
    }

    private boolean compararObjetosAutorActualizar(Autor autorActual, Autor autorFront) {
        return !autorActual.getNombre().equals(autorFront.getNombre())
                || !autorActual.getNacionalidad().equals(autorFront.getNacionalidad())
                || !autorActual.getFechaNacimiento().equals(autorFront.getFechaNacimiento())
                || !autorActual.getActivo().equals(autorFront.getActivo());
    }
}