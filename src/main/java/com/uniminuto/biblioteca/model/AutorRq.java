/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author holma
 */
@Data
public class AutorRq {
   private Integer autorId;
   private String nombre; 
   private Integer nacionalidadId;
   private LocalDate fechaNacimiento;
   
    
}
