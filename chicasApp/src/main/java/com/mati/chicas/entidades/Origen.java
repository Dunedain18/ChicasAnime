/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.entidades;

import com.mati.chicas.enumeraciones.tipoOrigen;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author matob
 */
@Data
@Entity
public class Origen {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    private tipoOrigen tipo;
    
    @OneToOne
    private Imagen logo;
    
    
    
}
