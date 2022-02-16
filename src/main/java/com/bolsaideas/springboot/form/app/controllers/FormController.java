package com.bolsaideas.springboot.form.app.controllers;

//import java.util.HashMap;
//import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsaideas.springboot.form.app.models.domain.Usuario;

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
