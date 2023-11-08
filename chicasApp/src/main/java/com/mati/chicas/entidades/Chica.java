/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author matob
 */
@Data
@Entity
public class Chica {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private int edad;
    private String raza;
    @ManyToOne
    private Color colorDeOjos;
    private String tipoDeOjos;
    private String cumpleaños;
    private String poder;
    @ManyToOne
    private Color colorDePelo;
    private String peinado;
    private int altura;
    private int peso;
    private int busto;
    private int cadera;
    private int cintura;
    private String copa;

    @ManyToOne
    private Origen anime;

    @OneToOne
    private Imagen imagen;

    private String descripcion;

}
