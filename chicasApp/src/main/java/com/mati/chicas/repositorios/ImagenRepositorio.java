/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.repositorios;


import com.mati.chicas.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matob
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    
}
