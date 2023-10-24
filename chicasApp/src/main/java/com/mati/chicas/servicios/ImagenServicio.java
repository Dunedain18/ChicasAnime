/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.servicios;

import com.mati.chicas.Exeptions.MiExeption;
import com.mati.chicas.entidades.Imagen;
import com.mati.chicas.repositorios.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author matob
 */
@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
    public Imagen guardar(MultipartFile archivo) throws MiExeption{
        if(archivo != null){
            try {
                 Imagen imagen = new Imagen();
                 
                 imagen.setMime(archivo.getContentType());
                 
                 imagen.setNombre(archivo.getName());
                 
                 imagen.setContenido(archivo.getBytes());
                 
                 return imagenRepositorio.save(imagen);
                 
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        
        return null;
    }
    
    public Imagen actualizar (MultipartFile archivo, String idImagen) throws MiExeption{
        
            if(archivo != null){
            try {
                 Imagen imagen = new Imagen();
                 
                 if(idImagen != null){
                     Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                     
                     if(respuesta.isPresent()){
                         imagen = respuesta.get();
                     }
                 }
                 
                 imagen.setMime(archivo.getContentType());
                 
                 imagen.setNombre(archivo.getName());
                 
                 imagen.setContenido(archivo.getBytes());
                 
                 return imagenRepositorio.save(imagen);
                 
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        
        return null;
        
    }
    
    
}
