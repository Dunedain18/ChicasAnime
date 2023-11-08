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
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        List<Color> colorOjos = colorServicio.listarColor();
        List<Origen> origenes = origenServicio.listarOrigen();

        modelo.addAttribute("colores", colores);
        modelo.addAttribute("colorOjos", colorOjos);
        modelo.addAttribute("origenes", origenes);

        return "chica_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam(required = false) int edad,@RequestParam(required = false) String cumple,
            @RequestParam(required = false) String raza,@RequestParam(required = false) String tipoOjo,@RequestParam String idColorOjo, @RequestParam String idColor,
            @RequestParam(required = false) String peinado, @RequestParam(required = false) int altura,
            @RequestParam(required = false) int peso, @RequestParam(required = false) int busto, @RequestParam(required = false) int cadera,
            @RequestParam(required = false) int cintura, @RequestParam(required = false) String copa,
            @RequestParam String idOrigen, @RequestParam(required = false) String descripcion,@RequestParam(required = false) String poder,
            @RequestParam(required = false) MultipartFile archivo, ModelMap modelo) {

        try {
            chicaServicio.crearChica(nombre, edad,cumple,raza,idColorOjo,tipoOjo, idColor, peinado, altura, peso, busto, cadera, cintura, copa, idOrigen, descripcion, archivo);

            modelo.put("exito", "la chica fue cargada correctamente");
        } catch (MiExeption ex) {
            List<Color> colores = colorServicio.listarColor();
            List<Origen> origenes = origenServicio.listarOrigen();

            modelo.addAttribute("colores", colores);
            modelo.addAttribute("origenes", origenes);

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
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("chica", chicaServicio.getOne(id));

        return "chica_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, int edad, String idColor, String peinado,
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

     @GetMapping("/detalle/{id}")
    public String detalleChica(@PathVariable String id, Model model) {
        Chica chica = chicaServicio.getOne(id);

        if (chica != null) {
            // Realiza la conversión de la imagen a base64
            byte[] imagenContenido = chica.getImagen().getContenido();
            String imagenBase64 = Base64.getEncoder().encodeToString(imagenContenido);

//             Agrega la imagen base64 al modelo
            model.addAttribute("imagenBase64", imagenBase64);
            // Agrega el chica al modelo
            model.addAttribute("chica", chica);

            return "chica_detalle";
        } else {
            // Manejar el caso en el que no se encuentra el chica
            return "error"; // Puedes crear una vista específica para errores.
        }
    }
}
