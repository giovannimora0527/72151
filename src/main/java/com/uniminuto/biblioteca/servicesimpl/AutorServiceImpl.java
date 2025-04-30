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
import javax.transaction.Transactional;
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
    System.out.println("Nombre del autor: " + authorToSave.getNombre());
System.out.println("Fecha nacimiento: " + authorToSave.getFechaNacimiento());
System.out.println("Nacionalidad: " + (authorToSave.getNacionalidad() != null ? authorToSave.getNacionalidad().getNombre() : "null"));
Optional<Autor> autorExistente = this.autorRepository.findByNombre(authorToSave.getNombre());
    if (autorExistente.isPresent()) {
        throw new BadRequestException("Ya existe un autor con el nombre: " + authorToSave.getNombre());
    }
    this.autorRepository.save(authorToSave);

    AutorRs rta = new AutorRs();
    rta.setAutorId(authorToSave.getAutorId());
    rta.setNombre(authorToSave.getNombre());
    rta.setNacionalidad(authorToSave.getNacionalidad().getNombre());
    rta.setFechaNacimiento(authorToSave.getFechaNacimiento());
    rta.setMessage("El autor se ha registrado correctamente.");

    return rta;
}



   @Transactional
@Override
public RespuestaGenericaRs actualizarAutor(Autor autor) throws BadRequestException {
    Optional<Autor> optAutor = this.autorRepository.findById(autor.getAutorId());
    if (!optAutor.isPresent()) {
        throw new BadRequestException("No existe el Autor.");
    }

    Autor autorActual = optAutor.get();
    if (!autor.getNombre().equalsIgnoreCase(autorActual.getNombre())) {
        Optional<Autor> autorConMismoNombre = this.autorRepository.findByNombre(autor.getNombre());
        if (autorConMismoNombre.isPresent() && !autorConMismoNombre.get().getAutorId().equals(autor.getAutorId())) {
            throw new BadRequestException("Ya existe otro autor con el nombre: " + autor.getNombre());
        }
    }
    RespuestaGenericaRs rta = new RespuestaGenericaRs();
    rta.setMessage("Se ha actualizado el registro satisfactoriamente");

    if (!compararObjetosAutorActualizar(autorActual, autor)) {
        return rta;
    }

    
    // Actualización de nacionalidad
    if (autor.getNacionalidad() != null && autor.getNacionalidad().getNacionalidadId() != null) {
        Optional<Nacionalidad> nacionalidadOpt = nacionalidadRepository.findById(autor.getNacionalidad().getNacionalidadId());
        nacionalidadOpt.ifPresent(autorActual::setNacionalidad);
    }

    autorActual.setNombre(autor.getNombre());
    autorActual.setFechaNacimiento(autor.getFechaNacimiento());

    // Guardar cambios
    this.autorRepository.saveAndFlush(autorActual);

    // 🔁 Forzar la recarga del autor actualizado desde la base de datos
    Autor autorRecargado = this.autorRepository.findById(autorActual.getAutorId()).orElse(autorActual);
    System.out.println("Nacionalidad actualizada: " + autorRecargado.getNacionalidad().getNombre());

    return rta;
}




    // Aca permite como tal actualizar los datos, si no esta mapeado los campos aca, no se van a actualizar
    private boolean compararObjetosAutorActualizar(Autor autorActual, Autor autorFront) {
        return !autorActual.getNombre().equals(autorFront.getNombre())
            || !autorActual.getNacionalidad().equals(autorFront.getNacionalidad())
            || !autorActual.getFechaNacimiento().equals(autorFront.getFechaNacimiento());
    }
    private Autor transformarAutorRqToAutor(AutorRq autorRq) throws BadRequestException {
    Autor author = new Autor();
    author.setNombre(autorRq.getNombre());
    author.setFechaNacimiento(autorRq.getFechaNacimiento());

    // Asignar la nacionalidad directamente desde el objeto Nacionalidad recibido
    Nacionalidad nacionalidad = autorRq.getNacionalidad();

    // Verificar si la nacionalidad es nula, en cuyo caso puedes crear una nueva o manejarlo como un error.
    if (nacionalidad == null) {
        throw new BadRequestException("La nacionalidad es obligatoria.");
    }

    author.setNacionalidad(nacionalidad);  // Asignar directamente el objeto Nacionalidad

    return author;
}
    
    
}
