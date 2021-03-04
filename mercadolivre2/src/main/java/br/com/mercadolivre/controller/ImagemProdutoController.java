package br.com.mercadolivre.controller;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.request.ImagemRequest;
import br.com.mercadolivre.request.UploaderFake;
import br.com.mercadolivre.validadors.UsuarioLogado;

@RestController
@RequestMapping("/produtos")
public class ImagemProdutoController {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private UploaderFake uploaderFake;

	@PostMapping("/{id}/imagem")
	@Transactional
	public String adicionaImagens(@PathVariable("id") Long id, @Valid ImagemRequest request,
			@AuthenticationPrincipal UsuarioLogado usuarioLogado) {
		Usuario usuario = usuarioLogado.get();
		Produto produto = manager.find(Produto.class, id);

		if (!produto.pertenceAoClienteLogado(usuario)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Você só pode adicionar imagens a um pr	oduto que vc cadastrou");
		}

		Set<String> links = uploaderFake.envia(request.getImagens());
		produto.associaImagens(links);
		manager.merge(produto);

		return produto.toString();
	}
}