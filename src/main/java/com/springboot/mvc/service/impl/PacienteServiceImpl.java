package com.springboot.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.springboot.mvc.entity.Paciente;
import com.springboot.mvc.repository.IPacienteRepository;
import com.springboot.mvc.service.PacienteService;


@Service
public class PacienteServiceImpl extends GenericServiceImpl<Paciente, Long> implements PacienteService {
	
	@Autowired
	private IPacienteRepository pacienteRepository;

	@Override
	public JpaRepository<Paciente, Long> getRepository() {
		return pacienteRepository;
	}


}
