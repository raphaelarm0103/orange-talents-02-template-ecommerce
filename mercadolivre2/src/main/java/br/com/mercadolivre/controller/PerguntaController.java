package br.com.mercadolivre.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.modelo.Pergunta;
import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.request.PerguntaRequest;
import br.com.mercadolivre.validadors.UsuarioLogado;
import javassist.NotFoundException;

@RestController
@RequestMapping("/produtos")
public class PerguntaController {

	@PersistenceContext
	private EntityManager manager;

	

	@PostMapping("/{id}/perguntas")
	@Transactional
	public String adicionarPergunta(@PathVariable long id, @RequestBody @Valid PerguntaRequest request, @AuthenticationPrincipal UsuarioLogado usuarioLogado)
			throws NotFoundException {

		Produto produto = manager.find(Produto.class, id);

		Usuario usuario = usuarioLogado.get();

		Pergunta pergunta = request.converter(usuario, produto);
		manager.persist(pergunta);

		return pergunta.toString();
	}
}