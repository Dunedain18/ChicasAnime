/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.servicios;

import com.mati.chicas.Exeptions.MiExeption;
import com.mati.chicas.entidades.Imagen;
import com.mati.chicas.entidades.Origen;
import com.mati.chicas.enumeraciones.tipoOrigen;
import com.mati.chicas.repositorios.ImagenRepositorio;
import com.mati.chicas.repositorios.OrigenRepositorio;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author matob
 */
@Service
public class OrigenServicio {

    @Autowired
    private OrigenRepositorio origenRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearOrigen(String nombre, tipoOrigen tipo,  MultipartFile archivo ) throws MiExeption{
        
        validar(nombre, tipo);
       
        Imagen imagen = imagenServicio.guardar(archivo);
        
        Origen origen = new Origen();
        
        origen.setNombre(nombre);
        origen.setTipo(tipo);
        origen.setLogo(imagen);
        
        origenRepositorio.save(origen);
    }
    
    public List<Origen> listarOrigen(){
        List<Origen> origenes = new ArrayList();
        
        origenes = origenRepositorio.findAll();
        
        return origenes;
    }
    
    @Transactional
    public void modificarOrigen(String id, String nombre, tipoOrigen tipo,  MultipartFile archivo)throws MiExeption{
         validar(nombre,tipo);
        
        Optional<Origen> respuesta = origenRepositorio.findById(id);
        if(respuesta.isPresent()){
        
        Origen origen = respuesta.get();
        
        origen.setNombre(nombre);
        origen.setTipo(tipo);
        
        String idImagen = null;
        
        if(origen.getLogo()!=null){
            idImagen = origen.getLogo().getId();
        }
        
        Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
        origen.setLogo(imagen);
        
        origenRepositorio.save(origen);  
        }
    }
    
     private void validar (String nombre, tipoOrigen tipo)throws MiExeption{
        
         if(nombre == null|| nombre.isEmpty()){
            throw new MiExeption("el nombre no puede ser nulo o estar vacio");
        }
         
         if(tipo == null){
            throw new MiExeption("el tipo no puede ser nulo o estar vacio");
        }
     }
     
     public String[] listaTipo (){
         
         String[] tipo = new String[tipoOrigen.values().length];
         int x =0;
         for (tipoOrigen aux : tipoOrigen.values()) {
             tipo[x]= aux.toString();
             x++;
         }
         return tipo;
     }

    public Origen getOne(String id) {
        return origenRepositorio.getOne(id);
    }
}
