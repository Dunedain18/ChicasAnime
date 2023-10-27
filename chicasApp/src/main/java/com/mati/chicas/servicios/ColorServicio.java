/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.servicios;

import com.mati.chicas.entidades.Color;
import com.mati.chicas.repositorios.ColorRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author matob
 */
@Service
public class ColorServicio {
    
    @Autowired
    private ColorRepositorio colorRepositorio;
    
    public List<Color> listarColor(){
        
        List<Color> colores = new ArrayList<>();
        colores = colorRepositorio.findAll();
        return colores;
    }
    
}
