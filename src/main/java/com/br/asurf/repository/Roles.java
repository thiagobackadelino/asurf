package com.br.asurf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
import com.br.asurf.model.Role; 



public interface Roles extends JpaRepository<Role, Long> {


}
