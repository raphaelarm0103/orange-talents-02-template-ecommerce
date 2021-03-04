package br.com.mercadolivre.detalheproduto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;


import br.com.mercadolivre.modelo.Produto;

public class DetalheProduto {

	private String descricao;
	private String nome;
	private BigDecimal valor;
	private Set<DetalheProdutoCaracteristica> caracteristicas;
	private Set<String> linksImagens;
	private SortedSet<String> perguntas;
	private Set<Map<String, String>> opinioes;
	private Double mediaNotas;

	public DetalheProduto(Produto produto) {
		this.descricao = produto.getDescricao();
		this.nome = produto.getNome();
		this.valor = produto.getValor();
		this.caracteristicas = produto.mapeiaCaracteristicas(DetalheProdutoCaracteristica::new);
		this.linksImagens = produto.mapeiaImagens(imagem -> imagem.getLink());
		this.perguntas = produto.mapeiaPerguntas(pergunta -> pergunta.getTitulo());
		this.opinioes = produto.mapeiaOpinioes(opiniao -> {
			return Map.of("titulo", opiniao.getTitulo(), "descricao", opiniao.getDescricao());
		});

		Set<Integer> notas = produto.mapeiaOpinioes(opiniao -> opiniao.getNota());
		OptionalDouble possivelMedia = notas.stream().mapToInt(nota -> nota).average();

		if (possivelMedia.isPresent()) {
			this.mediaNotas = possivelMedia.getAsDouble();
		}
	}

	public Object getMediaNotas() {
		return mediaNotas;
	}

	public Set<Map<String, String>> getOpinioes() {
		return opinioes;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public Set<DetalheProdutoCaracteristica> getCaracteristicas() {
		return caracteristicas;
	}

	public Set<String> getLinksImagens() {
		return linksImagens;
	}

	public SortedSet<String> getPerguntas() {
		return perguntas;
	}

}
