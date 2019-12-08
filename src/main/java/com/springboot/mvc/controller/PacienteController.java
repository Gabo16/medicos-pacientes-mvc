package com.springboot.mvc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.mvc.entity.Paciente;
import com.springboot.mvc.service.PacienteService;

/**
 * Esta clase es el controlador de los pacientes
 * @author Gabriel Gutiérrez
 *
 */
@Controller
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	private PacienteService pacienteService;
	
	/**
	 * 
	 * @param page Numero de página a consultar
	 * @return Lisa de pacientes a la vista
	 */
	@GetMapping
	public ModelAndView getPacientes(@RequestParam(required = false, defaultValue = "0") int page) {
		ModelAndView mav = new ModelAndView();
		if(page > 0) --page;
		Page<Paciente> pacientes = pacienteService.findAll(PageRequest.of(page, 5));

		mav.addObject("pacientes", pacientes);
		mav.setViewName("pacientes");
		return mav;
	}
	
	/**
	 * 
	 * @return La vista para crear un paciente
	 */
	@GetMapping("/crear")
	public String create() {
		return "pacienteForm";
	}

	/**
	 * 
	 * @param id Identificador del paciente a consultar en la base de datos
	 * @return La vista del paciente consultado
	 */
	@GetMapping("{id}")
	public ModelAndView getPaciente(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView();
		Paciente paciente = null;
		
		try {
			paciente = pacienteService.findById(id);
		} catch (DataAccessException e) {
			mav.addObject("message", "error encontrando el paciente en la base de datos");
			mav.addObject("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			mav.setViewName("error500");
			return mav;
		}
		
		if(paciente == null) {
			mav.addObject("message", "error encontrando el paciente en la base de datos");
			mav.addObject("error", "el paciente con el id: " + id + " no existe en la base de datos");
			mav.setViewName("error404");
		}
		
		mav.addObject("paciente", paciente);
		mav.setViewName("paciente");
		return mav;
	}
	
	/**
	 * 
	 * @param id Identificador del paciente a actualizar
	 * @param model Modelo para representar en las vistas
	 * @return La vista para actualizar un paciente
	 */
	@GetMapping("/{id}/actualizar")
	public String updatePaciente(@PathVariable Long id, Model model) {
		Paciente paciente = null;
		
		try {
			paciente = pacienteService.findById(id);
		} catch (DataAccessException e) {
			model.addAttribute("message", "error actualizando el paciente");
			model.addAttribute("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return "error500";
		}
		
		if(paciente == null) {
			model.addAttribute("message", "error actualizando el paciente");
			model.addAttribute("error", "el paciente no existe en la base de datos");
			return "error404";
		}
		
		model.addAttribute("paciente", paciente);
		return "pacienteForm";
	}
	
	/**
	 * 
	 * @param paciente Paciente a guardar en la base de datos
	 * @param result Validación del paciente
	 * @return La vista del paciente creado
	 */
	@PostMapping
	public ModelAndView save(@Valid Paciente paciente, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		Paciente pacienteSaved = null;
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.toList());
			mav.addObject("errors", errors);
			mav.setViewName("pacienteForm");
			return mav;
		}
		
		try {
			pacienteSaved = pacienteService.save(paciente);
		} catch (DataAccessException e) {
			mav.addObject("message", "error insertando el paciente");
			mav.addObject("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			mav.setViewName("error500");
			return mav;
		}
		
		mav.addObject("paciente" + pacienteSaved);
		mav.setViewName("paciente");
		return mav;
	}
	
	/**
	 * 
	 * @param id Identificador del paciente a actualizar
	 * @param paciente Paciente a actializar
	 * @param result Validación del paciente
	 * @return La vista del paciente actualizado
	 */
	@PostMapping("/{id}/actualizar")
	public ModelAndView update(@PathVariable Long id, @Valid Paciente paciente, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		Paciente pacienteFound = pacienteService.findById(id);
		
		if(pacienteFound == null) {
			mav.addObject("message", "error actualizando el paciente");
			mav.addObject("error", "el paciente no existe en la base de datos");
			mav.setViewName("error404");
			return mav;
		}
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.toList());
			mav.addObject("errors", errors);
			mav.setViewName("pacienteForm");
			return mav;
		}

		pacienteFound.setNombre(paciente.getNombre());
		pacienteFound.setNombre(paciente.getNombre());
		pacienteFound.setApPaterno(paciente.getApPaterno());
		pacienteFound.setApMaterno(paciente.getApMaterno());
		pacienteFound.setEdad(paciente.getEdad());
		pacienteFound.setMedico(paciente.getMedico());
		
		try {
			pacienteFound = pacienteService.save(pacienteFound);
		} catch (DataAccessException e) {
			mav.addObject("message", "error actualizando el paciente");
			mav.addObject("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			mav.setViewName("error500");
			return mav;
		}
		mav.addObject("paciente", pacienteFound);
		mav.setViewName("paciente");
		return mav;
	}
	
	
	/**
	 * 
	 * @param id Identificador del paciente a eliminar
	 * @param model Modelo para representar en las vistas
	 * @return Redireción a /pacientes
	 * 
	 */
	@GetMapping("/{id}/eliminar")
	public String delete(@PathVariable Long id, Model model) {
		Paciente paciente = pacienteService.findById(id);
		
		if(paciente == null) {
			model.addAttribute("message", "error eliminando el paciente");
			model.addAttribute("error", "el paciente no existe en la base de datos");
			return "error404";
		}
		
		try {
			pacienteService.deleteById(id);
		} catch (DataAccessException e) {
			model.addAttribute("message", "error actualizando el paciente");
			model.addAttribute("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return "error500";
		}
		
		return "redirect:/pacientes";
	}

}
