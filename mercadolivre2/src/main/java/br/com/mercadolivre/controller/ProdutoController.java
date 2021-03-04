package br.com.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.request.ProdutoRequest;
import br.com.mercadolivre.request.UploaderFake;
import br.com.mercadolivre.validadors.UsuarioLogado;

@RestController
@RequestMapping("/produtos")

public class ProdutoController {

	@PersistenceContext
	private EntityManager manager;

	@PostMapping
	@Transactional
	public String cadastraProduto(@RequestBody @Valid ProdutoRequest request,
			@AuthenticationPrincipal UsuarioLogado usuarioLogado) {

		Usuario usuariocriador = usuarioLogado.get();

		Produto produto = request.converter(manager, usuariocriador);

		manager.persist(produto);

		return produto.toString();
	}

}
