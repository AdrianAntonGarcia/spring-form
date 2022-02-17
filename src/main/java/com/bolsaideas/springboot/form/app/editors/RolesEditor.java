package com.bolsaideas.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bolsaideas.springboot.form.app.services.RoleService;

@Component
public class RolesEditor extends PropertyEditorSupport {

	@Autowired
	private RoleService roleService;

	@Override
	public void setAsText(String idRoleString) throws IllegalArgumentException {
		try {
			Integer idRole = Integer.parseInt(idRoleString);
			this.setValue(roleService.obtenerPorId(idRole));
		} catch (Exception e) {
			setValue(null);
		}

	}

}
