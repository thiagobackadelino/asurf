package com.br.asurf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.asurf.model.Usuario;



public interface UsuariosRep extends JpaRepository<Usuario, Long> {
	
	
	@Query("SELECT p FROM Usuario p WHERE LOWER(p.email) = LOWER(:email) and LOWER(p.senha) = LOWER(:senha)")
    public boolean autenticacao(@Param("email") String email,@Param("email") String senha);
	
	
	@Query("SELECT p FROM Usuario p WHERE LOWER(p.nome) = LOWER(:nome)")
    public Usuario getUsuario(@Param("nome") String nome);
	
	
}
