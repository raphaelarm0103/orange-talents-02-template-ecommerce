package br.com.mercadolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpNE;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import br.com.mercadolivre.modelo.Opiniao;
import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;

import br.com.mercadolivre.request.OpiniaoRequest;
import br.com.mercadolivre.validadors.UsuarioLogado;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class OpiniaoProdutoController {

	@PersistenceContext
	private EntityManager manager;
	
	/*
	 * @Autowired private UsuarioRepository usuarioRepository;
	 */

	@PostMapping("/{id}/opinioes")
	@Transactional
	public String adicionarOpiniao(@PathVariable("id") Long id, @RequestBody @Valid OpiniaoRequest form,
			@AuthenticationPrincipal UsuarioLogado usuarioLogado) {

		Usuario usuario = usuarioLogado.get();
        Produto produto = manager.find(Produto.class, id);

        Opiniao opiniao = form.converter(produto, usuario);
        manager.persist(opiniao);
        return opiniao.toString();
	}
}