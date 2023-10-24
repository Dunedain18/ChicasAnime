/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mati.chicas.servicios;

import com.mati.chicas.Exeptions.MiExeption;
import com.mati.chicas.entidades.Imagen;
import com.mati.chicas.entidades.Usuario;
import com.mati.chicas.enumeraciones.Rol;
import com.mati.chicas.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author matob
 */
@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void registrar (MultipartFile archivo, String nombre,String email, String password, String password2) throws MiExeption{
        
        validar(nombre, email, password, password2);
        
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        usuario.setRol(Rol.USER);
        
        Imagen imagen = imagenServicio.guardar(archivo);
        
        usuario.setImagen(imagen);
        
        usuarioRepositorio.save(usuario);
        
    }
    
    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre,String email, String password, String password2) throws MiExeption{
       validar(nombre, email, password, password2);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if(respuesta.isPresent()){
        
        Usuario usuario = respuesta.get();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        
        
        String idImagen = null;
        
        if(usuario.getImagen() !=null){
            idImagen = usuario.getImagen().getId();
        }
        
        Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
        usuario.setImagen(imagen);
        
        usuarioRepositorio.save(usuario);  
        }
    }
    
    private void validar(String nombre,String email, String password, String password2) throws MiExeption{
        
         if(nombre == null || nombre.isEmpty()){
            throw new MiExeption("el nombre no puede ser nulo o  estar vacio");
        }
         if(email == null || email.isEmpty()){
            throw new MiExeption("el email no puede ser nulo o  estar vacio");
        }
         if(password == null || password.isEmpty()|| password.length() <= 5){
            throw new MiExeption("el password no puede ser nulo o  estar vacio y debe tener 5 digitos");
        }
         if(!password.equals(password2)){
            throw new MiExeption("las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        if(usuario != null){
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().toString());
            
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession session = attr.getRequest().getSession(true);
            
            session.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getEmail(), usuario.getPassword(),permisos );
            
        }else{
            return null;
        }
                
    }
   
    public List<Usuario> listarUsuarios(){
         
      
       List<Usuario> usuarios = new ArrayList();
       
       usuarios = usuarioRepositorio.findAll();
       
       return usuarios;
    }
    
    @Transactional
    public void cambiarRol (String id){
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        
        if(respuesta.isPresent()){
             Usuario usuario = respuesta.get();
             
             if(usuario.getRol().equals(Rol.USER)){
                 usuario.setRol(Rol.ADMIN);
             }else if(usuario.getRol().equals(Rol.ADMIN)){
                 usuario.setRol(Rol.USER);
             }
        }
    }
    
    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }
}
