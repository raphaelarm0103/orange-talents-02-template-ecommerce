package br.com.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.request.UsuarioRequest;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	
	@PersistenceContext
	private EntityManager manager;
	
	@PostMapping
	@Transactional
	public void cria(@RequestBody @Valid UsuarioRequest request) {
		Usuario novoUsuario = request.toModel();
		manager.persist(novoUsuario);
		novoUsuario.toString();
	}

}
