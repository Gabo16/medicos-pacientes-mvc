package com.springboot.mvc.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "medicos")
public class Medico implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Nombre del médico es requerido")
	@Pattern(regexp = "^[A-Za-zÑñ]*$", message = "Nombre del médico solo debe contener letras")
	private String nombre;
	@Column(name = "ap_paterno")
	@NotBlank(message = "Apellido Paterno del médico es requerido")
	@Pattern(regexp = "^[A-Za-zÑñ]*$", message = "Apellido Paterno del médico solo debe contener letras")
	private String apPaterno;
	@Column(name = "ap_materno")
	@NotBlank(message = "Apellido Materno del médico es requerido")
	@Pattern(regexp = "^[A-Za-zÑñ]*$", message = "Apellido Materno del médico solo debe contener letras")
	private String apMaterno;
	@Min(message = "Edad del médico es requerida", value = 18)
	private int edad;
	@NotBlank(message = "Consultorio del médico es requerido")
	private String consultorio;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "medico")
	private List<Paciente> pacientes;
	

}
