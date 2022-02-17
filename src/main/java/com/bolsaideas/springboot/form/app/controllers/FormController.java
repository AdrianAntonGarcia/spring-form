package com.bolsaideas.springboot.form.app.controllers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.bolsaideas.springboot.form.app.models.domain.Usuario;
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

	@ModelAttribute("paises")
	public List<String> paises() {
		return Arrays.asList("España", "México", "Chile", "Argentina", "Perú", "Colombia", "Venezuela");
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
