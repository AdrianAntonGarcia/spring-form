package com.bolsaideas.springboot.form.app.models.domain;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

//import org.springframework.format.annotation.DateTimeFormat;

import com.bolsaideas.springboot.form.app.validation.IdentificadorRegex;
import com.bolsaideas.springboot.form.app.validation.Requerido;

public class Usuario {

	// [0-9] y \\d es lo mismo
	// @Pattern(regexp = "[0-9]{2}[.][\\d]{3}[.][\\d]{3}[-][a-zA-Z]{1}")
	@IdentificadorRegex
	private String identificador;

	// Podemos personalizar los mensajes de error con message
	// Se sobrescribe si hay algo en el messages.properties
	// @NotEmpty(message = "El nombre no debería estar vacío")
	private String nombre;

	// @NotEmpty
	@Requerido
	private String apellido;

	@NotBlank
	@Size(min = 3, max = 8)
	private String username;

	@NotEmpty
	private String password;

	@Requerido
	@Email
	private String email;

	// @NotNull Solo para objetos
	// el @Min sería para primitivos(int) y objetos
	@NotNull
	@Min(5)
	@Max(24)
	private Long cuenta;

	@NotNull
	@Past
//	@Future
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;

	@NotEmpty
	private String pais;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Long getCuenta() {
		return cuenta;
	}

	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}
