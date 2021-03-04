package br.com.mercadolivre.detalheproduto;

import br.com.mercadolivre.modelo.Caracteristicas;

public class DetalheProdutoCaracteristica {

	private String nome;

	public DetalheProdutoCaracteristica(Caracteristicas caracteristica) {
		this.nome = caracteristica.getNome();
		
		
	}

	public String getNome() {
		return nome;
	}
		
	
}
