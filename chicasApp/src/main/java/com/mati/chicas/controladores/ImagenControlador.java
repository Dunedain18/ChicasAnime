/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.controladores;


import com.mati.chicas.entidades.Chica;
import com.mati.chicas.entidades.Origen;
import com.mati.chicas.entidades.Usuario;
import com.mati.chicas.servicios.ChicaServicio;
import com.mati.chicas.servicios.OrigenServicio;
import com.mati.chicas.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author matob
 */
@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
   @Autowired
   UsuarioServicio usuarioServicio;
   
   @Autowired
   OrigenServicio origenServicio;
   
   @Autowired
   ChicaServicio chicaServicio;
   
   @GetMapping("/perfil/{id}")
   public ResponseEntity<byte[]> imagenUsuario (@PathVariable String id){
       Usuario usuario = usuarioServicio.getOne(id);
       
      byte[] imagen = usuario.getImagen().getContenido();
      
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
       
       
      
      return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
   }
   
   @GetMapping("/origen/{id}")
   public ResponseEntity<byte[]> imagenOrigen (@PathVariable String id){
       
       Origen origen= origenServicio.getOne(id);
       
      byte[] imagen = origen.getLogo().getContenido();
      
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
       
       
      
      return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
   }
   
   @GetMapping("/chica/{id}")
   public ResponseEntity<byte[]> imagenChica (@PathVariable String id){
       Chica chica = chicaServicio.getOne(id);
       
      byte[] imagen = chica.getImagen().getContenido();
      
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
       
       
      
      return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
   }
}
