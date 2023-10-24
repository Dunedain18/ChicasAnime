/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.servicios;

import com.mati.chicas.Exeptions.MiExeption;
import com.mati.chicas.entidades.Chica;
import com.mati.chicas.entidades.Color;
import com.mati.chicas.entidades.Imagen;
import com.mati.chicas.entidades.Origen;
import com.mati.chicas.repositorios.ChicaRepositorio;
import com.mati.chicas.repositorios.ColorRepositorio;
import com.mati.chicas.repositorios.OrigenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author matob
 */
@Service
public class ChicaServicio {

    @Autowired
    private ChicaRepositorio chicaRepositorio;

    @Autowired
    private ColorRepositorio colorRepositorio;

    @Autowired
    private OrigenRepositorio origenRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearChica(String nombre, int edad, Integer idColor, String peinado,
             int altura, int peso, int busto, int cadera, int cintura, String copa,
             String idOrigen, String descripcion, MultipartFile archivo) throws MiExeption {

        validar(nombre, idColor, idOrigen);
        
        Color color = colorRepositorio.findById(idColor).get();
        
        Origen origen = origenRepositorio.findById(idOrigen).get();
        
        Imagen imagen = imagenServicio.guardar(archivo);
        
        Chica chica = new Chica();
        
        chica.setNombre(nombre);
        
        chica.setEdad(edad);
        chica.setColorDePelo(color);
        chica.setPeinado(peinado);
        chica.setAltura(altura);
        chica.setPeso(peso);
        chica.setBusto(busto);
        chica.setCadera(cadera);
        chica.setCintura(cintura);
        chica.setCopa(copa);
        chica.setAnime(origen);
        chica.setImagen(imagen);
        chica.setDescripcion(descripcion);
        
        chicaRepositorio.save(chica);
        
    }
    
    
    
    private void validar (String nombre, Integer idColor, String idOrigen)throws MiExeption{
        
         if(nombre == null|| nombre.isEmpty()){
            throw new MiExeption("el nombre no puede ser nulo o estar vacio");
        }
                    
        if(idColor == null ){
            throw new MiExeption("el id del color no puede ser nulo o  estar vacio");
        }
        
        if(idOrigen == null || idOrigen.isEmpty()){
            throw new MiExeption("el id del origen no puede ser nulo o  estar vacio");
        }
                   
    }
    
    public Chica getOne(Integer id){
        return chicaRepositorio.getOne(id);
    }
}
/*  ;
    private ;
    @ManyToOne
    private Color colorDePelo;
    private ;
    private ;
    private int peso;
    private int busto;
    private int cadera;
    private int cintura;
    private String copa;
    @ManyToOne
    private Origen anime;
    @OneToOne
    private Imagen imagen;
    private String descripcion;*/
