package com.springboot.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.mvc.entity.Medico;

@Repository
public interface IMedicoRespository extends JpaRepository<Medico, Long>{

}
