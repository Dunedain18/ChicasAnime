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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public void crearChica(String nombre, int edad,String cumple,String raza, String idColorOjo,String tipoOjo, String idColor, String peinado,
            int altura, int peso, int busto, int cadera, int cintura, String copa,
            String idOrigen, String descripcion, MultipartFile archivo) throws MiExeption {

        validar(nombre, idColor, idOrigen);

        Color color = colorRepositorio.findById(idColor).get();
        
        Color colorOjo = colorRepositorio.findById(idColorOjo).get();

        Origen origen = origenRepositorio.findById(idOrigen).get();

        Imagen imagen = imagenServicio.guardar(archivo);

        Chica chica = new Chica();

        chica.setNombre(nombre);

        chica.setEdad(edad);
        chica.setRaza(raza);
        chica.setColorDeOjos(colorOjo);
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

    @Transactional
    public void modificarChica(String id, String nombre, int edad, String idColor, String peinado,
            int altura, int peso, int busto, int cadera, int cintura, String copa,
            String idOrigen, String descripcion, MultipartFile archivo) throws MiExeption {
        
        

        validar(nombre, idColor, idOrigen);

        Optional<Chica> respuesta = chicaRepositorio.findById(id);

        Optional<Color> respuestaColor = colorRepositorio.findById(idColor);

        Optional<Origen> respuestaOrigen = origenRepositorio.findById(idOrigen);

        Color color = new Color();

        Origen origen = new Origen();

        if (respuestaColor.isPresent()) {
            color = respuestaColor.get();
        }
        if (respuestaOrigen.isPresent()) {
            origen = respuestaOrigen.get();
        }
        if (respuesta.isPresent()) {
            Chica chica = respuesta.get();

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

            String idImagen = null;

            if (chica.getImagen() != null) {
                idImagen = chica.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            if(imagen != null){
            chica.setImagen(imagen);}
            chica.setDescripcion(descripcion);
            chicaRepositorio.save(chica);
        }

    }

    public List<Chica> listarChica() {

        List<Chica> chicas = new ArrayList<>();
        chicas = chicaRepositorio.findAll();
        return chicas;
    }

    private void validar(String nombre, String idColor, String idOrigen) throws MiExeption {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiExeption("el nombre no puede ser nulo o estar vacio");
        }

        if (idColor == null || idColor.isEmpty()) {
            throw new MiExeption("el id del color no puede ser nulo o  estar vacio" + idColor);
        }

        if (idOrigen == null || idOrigen.isEmpty()) {
            throw new MiExeption("el id del origen no puede ser nulo o  estar vacio" + idOrigen);
        }

    }

    public Chica getOne(String id) {
        return chicaRepositorio.getOne(id);
    }
}
