package com.bolsaideas.springboot.form.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bolsaideas.springboot.form.app.models.domain.Role;

@Service
public class RoleServiceImpl implements RoleService {

	private List<Role> roles;

	public RoleServiceImpl() {
		this.roles = new ArrayList<Role>();
		this.roles.add(new Role(1, "Administrador", "ROLE_ADMIN"));
		this.roles.add(new Role(1, "Usuario", "ROLE_USER"));
		this.roles.add(new Role(1, "operador", "ROLE_OPERATOR"));
	}

	@Override
	public List<Role> listar() {
		return roles;
	}

	@Override
	public Role obtenerPorId(Integer id) {
		Role resultado = null;
		try {
			for (Role role : roles) {
				if (id == role.getId()) {
					resultado = role;
					break;
				}
			}
			return resultado;
		} catch (Exception e) {
			return null;
		}
	}

}
