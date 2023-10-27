/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.controladores;

import com.mati.chicas.Exeptions.MiExeption;
import com.mati.chicas.entidades.Chica;
import com.mati.chicas.entidades.Color;
import com.mati.chicas.entidades.Origen;
import com.mati.chicas.enumeraciones.tipoOrigen;
import com.mati.chicas.servicios.ChicaServicio;
import com.mati.chicas.servicios.ColorServicio;
import com.mati.chicas.servicios.OrigenServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author matob
 */
@Controller
@RequestMapping("/chica")//http://localhost:8080/chica
public class ChicaControlador {

    @Autowired
    private ChicaServicio chicaServicio;

    @Autowired
    private ColorServicio colorServicio;

    @Autowired
    private OrigenServicio origenServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Color> colores = colorServicio.listarColor();
        List<Origen> origenes = origenServicio.listarOrigen();
        
        modelo.addAttribute("colores", colores);
        modelo.addAttribute("origenes", origenes);

        return "chica_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, int edad, Integer idColor, String peinado,
            int altura, int peso, int busto, int cadera, int cintura, String copa,
            String idOrigen, String descripcion, MultipartFile archivo, ModelMap modelo) {

        try {
            chicaServicio.crearChica(nombre, edad, idColor, peinado, altura, peso, busto, cadera, cintura, copa, idOrigen, descripcion, archivo);

            modelo.put("exito", "la chica fue cargada correctamente");
        } catch (MiExeption ex) {

            modelo.put("error", ex.getMessage());
            return "chica_form.html";
        }

        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

              List<Chica> chicas = chicaServicio.listarChica();
        
        
        modelo.addAttribute("chicas", chicas);

        return "chica_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, ModelMap modelo) {
        modelo.put("chica", chicaServicio.getOne(id));

        return "chica_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Integer id, String nombre, int edad, Integer idColor, String peinado,
            int altura, int peso, int busto, int cadera, int cintura, String copa,
            String idOrigen, String descripcion, MultipartFile archivo, ModelMap modelo) {
        try {
            chicaServicio.modificarChica(id, nombre, edad, idColor, peinado, altura, peso, busto, cadera, cintura, copa, idOrigen, descripcion, archivo);
            return "redirect:../lista";
        } catch (MiExeption ex) {
            modelo.put("error", ex.getMessage());
            return "chica_modificar.html";
        }
    }

}
