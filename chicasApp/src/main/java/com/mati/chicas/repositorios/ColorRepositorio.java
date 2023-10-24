/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.repositorios;

import com.mati.chicas.entidades.Color;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author matob
 */
public interface ColorRepositorio extends JpaRepository<Color, Integer>{
    
}
