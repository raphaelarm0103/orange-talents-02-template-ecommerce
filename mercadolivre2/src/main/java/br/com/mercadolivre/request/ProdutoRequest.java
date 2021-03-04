package br.com.mercadolivre.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import br.com.mercadolivre.modelo.Caracteristicas;
import br.com.mercadolivre.modelo.Categoria;
import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.validadors.ExistsId;
import br.com.mercadolivre.validadors.UniqueValue;

public class ProdutoRequest {
	@NotBlank
	private String nome;

	@NotNull
	@Min(1)
	private BigDecimal valor;

	@NotNull
	@Min(0)
	private int quantidade;

	@Size(min = 3)
	@OneToMany(mappedBy = "produto", cascade = { CascadeType.PERSIST })
	private List<CaracteristicasRequest> caracteristicas = new ArrayList<>();

	@NotBlank
	private String descricao;

	@NotNull
	@ExistsId(domainClass = Categoria.class, fieldName = "id", message = "")
	private Long idCategoria;

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	@Deprecated
	public ProdutoRequest() {
	}

	public ProdutoRequest(@NotBlank String nome, @NotNull @Min(1) @NotNull @Min(1) BigDecimal valor,
			@NotNull @Min(0) int quantidade, @Size(min = 3) List<CaracteristicasRequest> caracteristicas,
			@NotBlank String descricao, @NotNull long idCategoria) {
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.caracteristicas.addAll(caracteristicas);
		this.descricao = descricao;
		this.idCategoria = idCategoria;
	}

	@Override
	public String toString() {
		return "ProdutoRequest{" + "nome='" + nome + '\'' + ", valor=" + valor + ", quantidade=" + quantidade
				+ ", caracteristicas=" + caracteristicas + ", descricao='" + descricao + '\'' + ", idCategoria="
				+ idCategoria + '}';
	}

	public List<CaracteristicasRequest> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(List<CaracteristicasRequest> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public Produto converter(EntityManager manager, Usuario usuario) {
		@NotNull
		Categoria categoria = manager.find(Categoria.class, idCategoria);

		return new Produto(nome, quantidade, descricao, valor, categoria, usuario, caracteristicas);

	}
}