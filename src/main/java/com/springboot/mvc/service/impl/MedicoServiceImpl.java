package com.springboot.mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.springboot.mvc.entity.Medico;
import com.springboot.mvc.repository.IMedicoRespository;
import com.springboot.mvc.service.MedicoService;

@Service
public class MedicoServiceImpl extends GenericServiceImpl<Medico, Long> implements MedicoService {
	
	@Autowired
	private IMedicoRespository medicoRepository;

	@Override
	public JpaRepository<Medico, Long> getRepository() {
		return medicoRepository;
	}
	

}
