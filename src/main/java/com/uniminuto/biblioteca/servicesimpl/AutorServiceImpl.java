package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import java.util.Optional;
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
        return this.autorRepository.findAll();
    }    

    @Override
    public Autor obtenerAutoresPorId(Integer autorId) {
    Optional<Autor> autor = this.autorRepository.findById(autorId);
     if (autor.isPresent()) {
        return autor.get();
    } else {
        throw new RuntimeException("ID de autor no encontrado");
    }
}
    
}

 
