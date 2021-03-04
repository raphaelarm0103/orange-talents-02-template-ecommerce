package br.com.mercadolivre.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.Collection;
import java.util.HashSet;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.sun.istack.NotNull;


import br.com.mercadolivre.request.CaracteristicasRequest;
import br.com.mercadolivre.validadors.UsuarioLogado;

@Entity
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private BigDecimal valor;

	private int quantidade;

	@OneToMany(mappedBy = "produto", cascade = { CascadeType.PERSIST })
	private Set<Caracteristicas> caracteristicas = new HashSet<>();

	private String descricao;

	@ManyToOne

	private Categoria categoria;

	private LocalDateTime dataCriacao = LocalDateTime.now();

	@ManyToOne
	private Usuario usuario;

	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private Set<ImagemProduto> imagens = new HashSet<>();

	@OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
	private Set<Opiniao> opinioes = new HashSet<>();

	@OneToMany(mappedBy = "produto")
	@OrderBy("titulo asc")
	private SortedSet<Pergunta> perguntas = new TreeSet<>();

	public Produto(@NotBlank String nome, @Positive int quantidade, @NotBlank @Length(max = 1000) String descricao,
			@NotNull @Positive BigDecimal valor, @NotNull @Valid Categoria categoria, @NotNull @Valid Usuario usuario,
			@Size(min = 3) @Valid Collection<CaracteristicasRequest> caracteristicas) {

		this.nome = nome;
		this.quantidade = quantidade;
		this.descricao = descricao;
		this.valor = valor;
		this.categoria = categoria;
		this.usuario = usuario;
		this.caracteristicas.addAll(caracteristicas.stream().map(caracteristica -> caracteristica.converter(this))
				.collect(Collectors.toSet()));
		
		  Assert.isTrue(this.caracteristicas.size() >= 3, "Precisa ter no minimo 3 caracteristicas");
	}

	public Produto() {

	}

	public Set<Opiniao> adicionaOpiniao(@NotNull Opiniao opiniao) {
		opinioes.add(opiniao);
		return opinioes;
	}

	public Long getId() {
		return id;
	}

	public Set<ImagemProduto> getImagens() {
		return imagens;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Categoria getCategoria() {
		return categoria;
	}

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

	public LocalDateTime getDataCriacao() {
		return getDataCriacao();
	}

	public Set<Caracteristicas> getCaracteristicas() {
		return caracteristicas;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", valor=" + valor + ", quantidade=" + quantidade
				+ ", caracteristicas=" + caracteristicas + ", descricao=" + descricao + ", categoria=" + categoria
				+ ", dataCriacao=" + dataCriacao + "]";
	}

	public <T> Set<T> mapeiaCaracteristicas(Function<Caracteristicas, T> funcaoMapeadora) {
		return this.caracteristicas.stream().map(funcaoMapeadora).collect(Collectors.toSet());
	}

	public boolean pertenceAoClienteLogado(Usuario usuarioLogado1) {
		UsuarioLogado logado = (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Usuario usuario = logado.get();

		return this.usuario.equals(usuario);
	}

	public void associaImagens(Set<String> links) {
		Set<ImagemProduto> imagens = links.stream().map(link -> new ImagemProduto(this, link))
				.collect(Collectors.toSet());

		this.imagens.addAll(imagens);

	}

	public void adicionarPergunta(Pergunta pergunta) {
		this.perguntas.add(pergunta);
	}

	public <T> Set<T> mapeiaImagens(Function<ImagemProduto, T> funcaoMapeadora) {
		return this.imagens.stream().map(funcaoMapeadora).collect(Collectors.toSet());
	}

	public <T extends Comparable<T>> SortedSet<T> mapeiaPerguntas(Function<Pergunta, T> funcaoMapeadora) {
		return this.perguntas.stream().map(funcaoMapeadora).collect(Collectors.toCollection(TreeSet::new));
	}

	public <T> Set<T> mapeiaOpinioes(Function<Opiniao, T> funcaoMapeadora) {
		return this.opinioes.stream().map(funcaoMapeadora).collect(Collectors.toSet());
	}

	public boolean abateEstoque(@Positive int quantidade) {
			Assert.isTrue(quantidade > 0, "A quantidade deve ser maior que zero para abater o estoque" +quantidade);
			
			if(quantidade <= this.quantidade) {
				this.quantidade-=quantidade;
				return true;
			}
			return false;
	}

}
