package com.springboot.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "medico")
@Entity
@Table(name = "pacientes")
public class Paciente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Nombre del paciente es requerido")
	@Pattern(regexp = "^[A-Za-zÑñ]*$", message = "Nombre del paciente solo debe contener letras")
	private String nombre;
	@NotBlank(message = "Apellido Paterno del paciente es requerido")
	@Pattern(regexp = "^[A-Za-zÑñ]*$", message = "Apellido Paterno del paciente solo debe contener letras")
	@Column(name = "ap_paterno")
	private String apPaterno;
	@NotBlank(message = "Apellido Materno del paciente es requerido")
	@Pattern(regexp = "^[A-Za-zÑñ]*$", message = "Apellido Materno del paciente solo debe contener letras")
	@Column(name = "ap_materno")
	private String apMaterno;
	private int edad;
	@ManyToOne
	private Medico medico;

}
