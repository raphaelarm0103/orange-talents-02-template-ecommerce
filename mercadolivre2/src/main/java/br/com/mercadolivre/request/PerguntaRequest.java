package br.com.mercadolivre.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import com.sun.istack.NotNull;

import br.com.mercadolivre.modelo.Pergunta;
import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;

public class PerguntaRequest {

	@NotBlank
	private String titulo;

	public PerguntaRequest(@NotBlank String titulo) {
		super();
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Deprecated
	public PerguntaRequest() {

	}

	
	public Pergunta converter(@NotNull @Valid Usuario usuario, @NotNull @Valid Produto produto) {
		return new Pergunta(titulo, usuario, produto);
	}
	
	@Override
	public String toString() {
		return "PerguntaRequest [titulo=" + titulo + "]";
	}

}
