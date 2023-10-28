/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.repositorios;

import com.mati.chicas.entidades.Chica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matob
 */
@Repository
public interface ChicaRepositorio extends JpaRepository<Chica, String>{
    @Query ("SELECT u FROM Chica u WHERE u.nombre = :nombre")
    public Chica buscarPorNombre(@Param("nombre") String nombre);
}
