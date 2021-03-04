package br.com.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.detalheproduto.DetalheProduto;
import br.com.mercadolivre.modelo.Produto;

@RestController
@RequestMapping
public class DetalheProdutoController {
	
	@PersistenceContext
	private EntityManager manager;
				
	@GetMapping(value="/produtos/{id}")
	public DetalheProduto detalhe(@PathVariable("id") Long id) {
		
		
		Produto produto = manager.find(Produto.class, id);
		return new DetalheProduto(produto);
	}
}
