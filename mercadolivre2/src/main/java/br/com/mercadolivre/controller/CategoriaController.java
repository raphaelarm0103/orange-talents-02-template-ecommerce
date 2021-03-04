package br.com.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.modelo.Categoria;
import br.com.mercadolivre.request.CategoriaRequest;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@PersistenceContext
	private EntityManager manager;
	
	@PostMapping
	@Transactional
	public void criaCategoria(@RequestBody @Valid CategoriaRequest request) {
		Categoria categoria = request.converter(manager);
		manager.persist(categoria);
		
		categoria.toString();
	}

}
