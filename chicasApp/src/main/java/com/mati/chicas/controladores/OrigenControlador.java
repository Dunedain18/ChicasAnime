/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.controladores;

import com.mati.chicas.Exeptions.MiExeption;
import com.mati.chicas.enumeraciones.tipoOrigen;
import com.mati.chicas.servicios.OrigenServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author matob
 */
@Controller
@RequestMapping("/origen")
public class OrigenControlador {

    @Autowired
    private OrigenServicio origenServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        String[] tipo = origenServicio.listaTipo();

        
        modelo.addAttribute("tipo", tipo);

        return "origen_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, String tipo, ModelMap modelo, MultipartFile archivo) {

        try {
            origenServicio.crearOrigen(nombre, tipoOrigen.valueOf(tipo), archivo);

            modelo.put("exito", "el origen fue cargado correctamente");
        } catch (MiExeption ex) {

            modelo.put("error", ex.getMessage());
            return "origen_form.html";
        }

        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
       return "origen_list.html";
    }

}
