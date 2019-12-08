package com.springboot.mvc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.mvc.entity.Medico;
import com.springboot.mvc.service.MedicoService;

/**
 * Esta clase es el controlador de los médicos
 * 
 * @author Gabriel Gutiérrez
 *
 */

@Controller
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;
	
	/**
	 * 
	 * @return Lista de médicos registrados en la base de datos
	 */
	@GetMapping
	public ModelAndView getMEdicos() {
		
		List<Medico> medicos = medicoService.findAll();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("medicos", medicos);
        mav.setViewName("medicos");
		return mav;
	}

	/**
	 * 
	 * @param id Identificador del médico a consultar en la base de datos
	 * @return Médico consultado en la base de datos
	 */
	@GetMapping("{id}")
	public ModelAndView getMedico(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView();
		Medico medico = null;
		
		try { 
			medico = medicoService.findById(id);
		} catch (DataAccessException e) {
			mav.addObject("message", "error encontrando el médico en la base de datos");
			mav.addObject("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			mav.setViewName("error500");
			return mav;
		}
		
		if(medico == null) {
			mav.addObject("message", "error encontrando el médico en la base de datos");
			mav.addObject("error", "el médico con el id :" + id + " no existe en la base de datos");
			mav.setViewName("error404");
			return mav;
		}
		
		mav.addObject("medico", medico);
		mav.setViewName("medico");
		return mav;
	}
	
	
	/**
	 * Método que muestra vista para crear un médico
	 * @return Vista medicoForm
	 */
	@GetMapping("/crear")
	public String create() {
		return "medicoForm";
	}
	
	/**
	 * Método que muestra vista para actualizar un médico
	 * @return Vista medicoForm
	 */
	@GetMapping("/{id}/actualizar")
	public String update(@PathVariable Long id, Model model) {
		Medico medico = null;
		
		try { 
			medico = medicoService.findById(id);
		} catch (DataAccessException e) {
			model.addAttribute("message", "error encontrando el médico en la base de datos");
			model.addAttribute("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return "error500";
		}
		 
		if(medico == null) {
			model.addAttribute("message", "error encontrando el médico en la base de datos");
			model.addAttribute("error", "el médico con el id :" + id + " no existe en la base de datos");
			return "error404";
		}
		
		model.addAttribute("medico", medico);
		return "medicoForm";
	}
	
	/**
	 * 
	 * @param medico Médico a guardar en la base de datos
	 * @param result Validación del médico
	 * @param model Modelo para representar en las vistas
	 * @return Redireción a /medicos/{id}
	 * 
	 */
	
	@PostMapping
	public String saveRedirect(@Valid Medico medico, BindingResult result, Model model) {
		Medico medicoSaved = null;
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.toList());
			model.addAttribute("errors", errors);
			return "medicoForm";
		}
		
		try {
			medicoSaved = medicoService.save(medico);
		} catch (DataAccessException e) {
			model.addAttribute("message", "error encontrando el médico en la base de datos");
			model.addAttribute("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return "error500";
		}
		
		return "redirect:/medicos/" + medicoSaved.getId();		
	}
	
	/**
	 * 
	 * @param medico Médico a guardar en la base de datos
	 * @param result Validación del médico
	 * @param id Identificador del médico a actualizar
	 * @return
	 */
	
	@PostMapping("/{id}")
	public ModelAndView update(@Valid Medico medico, BindingResult result, @PathVariable Long id) {
		ModelAndView mav = new ModelAndView();
		Medico medicoUpdated = null;
		
		medicoUpdated = medicoService.findById(id);
		
		if(medicoUpdated == null) {
			mav.addObject("message", "error actualizando el médico");
			mav.addObject("error", "el médico con el id :" + id + " no existe en la base de datos");
			mav.setViewName("error404");
			return mav;
		}
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.toList());
			mav.addObject("errors", errors);
			mav.setViewName("medicoForm");
			return mav;
		}
		
		medicoUpdated.setNombre(medico.getNombre());
		medicoUpdated.setApPaterno(medico.getApPaterno());
		medicoUpdated.setApMaterno(medico.getApMaterno());
		medicoUpdated.setEdad(medico.getEdad());
		medicoUpdated.setConsultorio(medico.getConsultorio());
		
		try {
			medicoUpdated = medicoService.save(medicoUpdated);
		} catch (DataAccessException e) {
			mav.addObject("message", "error actualizando el médico en la base de datos");
			mav.addObject("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			mav.setViewName("error500");
			return mav;
		}
		mav.addObject("medico", medicoUpdated);
		mav.setViewName("medico");
		return mav;
	}
	
	/**
	 * 
	 * @param id Identificador del médico a eliminar
	 * @param model Modelo para representar en las vistas
	 * @return Redireción a /medicos
	 */
	@GetMapping("/{id}/eliminar")
	public String delete(@PathVariable Long id, Model model) {
		Medico medico = medicoService.findById(id);
		
		if(medico == null) {
			model.addAttribute("message", "error eliminando el médico");
			model.addAttribute("error", "el médico no existe en la base de datos");
			return "error404";
		}
		
		try {
			medicoService.deleteById(medico.getId());
		} catch (DataAccessException e) {
			model.addAttribute("message", "error eliminando el médico");
			model.addAttribute("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return "error500";
		}
	
		return "redirect:/medicos";
	}

}
