package com.br.asurf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.asurf.model.Usuario;



public interface Usuarios extends JpaRepository<Usuario, Long> {

}
