package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.NacionalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NacionalidadServiceImpl implements NacionalidadService {

    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Override
    public List<Nacionalidad> obtenerListaNacionalidades() {
        return this.nacionalidadRepository.findAll();
    }
}
