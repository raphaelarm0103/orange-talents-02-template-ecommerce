package br.com.mercadolivre.request;



import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


import com.sun.istack.NotNull;

import br.com.mercadolivre.modelo.Caracteristicas;
import br.com.mercadolivre.modelo.Produto;

public class CaracteristicasRequest {

	@NotBlank
	private String nome;

	@NotNull
	private String descricao;

	public CaracteristicasRequest(@NotBlank String nome, @NotNull String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	@Deprecated
	public CaracteristicasRequest() {
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Caracteristicas converter(@NotNull @Valid Produto produto) {
		return new Caracteristicas(nome, descricao, produto);
	}

	@Override
	public String toString() {
		return "NovaCaracteristicaRequest [nome=" + nome + ", descricao=" + descricao + "]";
	}

}
