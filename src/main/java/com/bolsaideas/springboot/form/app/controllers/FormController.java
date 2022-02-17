package com.bolsaideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.HashMap;
//import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsaideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsaideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsaideas.springboot.form.app.editors.RolesEditor;
import com.bolsaideas.springboot.form.app.models.domain.Pais;
import com.bolsaideas.springboot.form.app.models.domain.Role;
import com.bolsaideas.springboot.form.app.models.domain.Usuario;
import com.bolsaideas.springboot.form.app.services.PaisService;
import com.bolsaideas.springboot.form.app.services.RoleService;
import com.bolsaideas.springboot.form.app.validation.UsuarioValidador;

/**
 * Con @SessionAttributes mantenemos los datos con la sesión, así no se pierden
 * si no se informan en el formulario
 * 
 * @author Adrián
 *
 */
@Controller
@SessionAttributes("user")
public class FormController {

	@Autowired
	private UsuarioValidador validador;

	@Autowired
	private PaisService paisService;

	@Autowired
	private PaisPropertyEditor paisPropertyEditor;

	@Autowired
	private RolesEditor rolesEditor;

	@Autowired
	private RoleService roleService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
//		Si usamos set reemplaza todos los validadores que hubiera antes incluidos los de la clase Usuario
//		binder.setValidator(validador);
		/**
		 * addValidators: Validador de un campo
		 */
		binder.addValidators(validador);
		/**
		 * registerCustomEditor: Interceptamos el valor de un campo y lo modificamos
		 * Otra forma de hacer el @DateTimeFormat del campo en la clase usuario Aplica a
		 * todos los campos que estén definidos como date
		 */
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Define si el analizador es estricto o tolerante al poner mal el formato de la
		// fecha2
		dateFormat.setLenient(false);
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		// Así especifiamos sobre que campo en concreto trabajamos y que no aplique a
		// todos
		binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, true));
		// Convertimos todos los string a mayúsculas
		binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
		binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());
		binder.registerCustomEditor(Pais.class, "pais", paisPropertyEditor);
		binder.registerCustomEditor(Role.class, "roles", rolesEditor);
	}

	@GetMapping({ "/form" })
	public String form(Model model) {
		Usuario usuario = new Usuario();
		usuario.setIdentificador("24.243.243-K");
		usuario.setNombre("Adrián");
		usuario.setApellido("Anton");
		model.addAttribute("titulo", "Formulario usuarios");
		model.addAttribute("user", usuario);
		return "form";
	}

	@ModelAttribute("user")
	public Usuario getUserObject() {
		return new Usuario();
	}

	@ModelAttribute("listaRolesString")
	public List<String> getListaRolesString() {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_USER");
		roles.add("ROLE_MODERATOR");
		return roles;
	}

	@ModelAttribute("listaRolesMap")
	public Map<String, String> getListaRolesMap() {
		Map<String, String> roles = new HashMap<String, String>();
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_USER", "Usuario");
		roles.put("ROLE_MODERATOR", "Moderador");
		return roles;
	}

	@ModelAttribute("listaRolesObject")
	public List<Role> getListaRolesObject() {
		return this.roleService.listar();
	}

	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("España", "México", "Chile", "Argentina", "Perú", "Colombia", "Venezuela");
	}

//	@ModelAttribute("listaPaises")
//	public List<Pais> listaPaises() {
//		return Arrays.asList(new Pais(1, "ES", "España"), new Pais(2, "MX", "México"), new Pais(3, "CH", "Chile"),
//				new Pais(4, "AR", "Argentina"), new Pais(5, "PE", "Perú"), new Pais(6, "CO", "Colombia"),
//				new Pais(7, "VE", "Venezuela"));
//	}

	@ModelAttribute("listaPaises")
	public List<Pais> listaPaises() {
		return paisService.listar();
	}

	@ModelAttribute("paisesMap")
	public Map<String, String> paisesMap() {
		Map<String, String> paises = new HashMap<String, String>();
		paises.put("ES", "España");
		paises.put("MX", "México");
		paises.put("CL", "Chile");
		paises.put("AR", "Argentina");
		paises.put("PE", "Perú");
		paises.put("CO", "Colombia");
		paises.put("VE", "Venezuela");
		return paises;
	}

	/**
	 * Automáticamente se mapean los datos del formulario con los de la clase
	 * Usuario
	 * 
	 * BindingResult contiene el resultado de la validación, es decir, contiene los
	 * mensajes de error ¡Siempre tiene que estar después del objeto que se valida!
	 * 
	 * Con @ModelAttribute cambiamos el nombre por defecto del campo
	 * 
	 * @param usuario
	 * @param model
	 * @return
	 */
//	@RequestParam(value = "username") String usernameM,
//	@RequestParam String password, @RequestParam String email
	@PostMapping("/form")
	public String procesar(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model,
			SessionStatus status) {
		// Forma manual
		// validador.validate(usuario, result);
		model.addAttribute("titulo", "Resultado del formulario");
		if (result.hasErrors()) {

//			Forma manual:
//			Map<String, String> errores = new HashMap<>();
//			result.getFieldErrors().forEach(err -> {
//				errores.put(err.getField(),
//						"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
//			});
//			model.addAttribute("error", errores);
			return "form";
		}
		model.addAttribute("usuario", usuario);
		// Se elimina el objeto usuario de la sesión una vez hemos trabajado los datos
		// con setComplete()
		status.setComplete();
		return "resultado";
	}
}
