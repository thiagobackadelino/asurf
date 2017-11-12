package com.br.asurf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.asurf.model.Evento;
import com.br.asurf.model.Role; 



public interface Eventos extends JpaRepository<Evento, Long> {


	@Query("SELECT p FROM Evento p WHERE LOWER(p.title) = LOWER(:nome)")
    public Evento  evento(@Param("nome") String n);
	
	
	@Query("SELECT p FROM Evento p WHERE p.start like CONCAT('%',:id,'%')")
    public List<Evento>   eventosDodia(@Param("id") String n);
}
