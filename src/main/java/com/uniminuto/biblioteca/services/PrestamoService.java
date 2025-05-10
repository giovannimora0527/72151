/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.services;


import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;

public interface PrestamoService {
    List<Prestamo> listarTodos();
    RespuestaGenericaRs guardarPrestamo(Prestamo prestamo);
    RespuestaGenericaRs entregarPrestamo(Prestamo prestamo);
}