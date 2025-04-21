package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author ewdev
 */
@Data
public class AutorRs {
    private String message;
    
public String getMessage(){
    return message;
    }

public void setMessage (String message){
    this.message = message;
}

}
