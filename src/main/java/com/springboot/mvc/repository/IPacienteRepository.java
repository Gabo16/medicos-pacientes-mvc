package com.springboot.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.mvc.entity.Paciente;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long>{

}
