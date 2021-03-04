package br.com.mercadolivre.fechamentocompra;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.web.util.UriComponentsBuilder;

import com.sun.istack.NotNull;

import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;
import io.jsonwebtoken.lang.Assert;

@Entity
@Table(name = "compras")
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@Valid
	@NotNull
	private Produto produtoEscolhido;

	@Positive
	private int quantidade;

	@ManyToOne
	@Valid
	@NotNull
	private Usuario usuario;

	@Enumerated
	@NotNull
	private GatewayPagamento gatewayPagamento;

	@OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
	private Set<Transacao> transacoes = new HashSet<>();

	public Compra(@Valid @NotNull Produto produtoParaCompra, @Positive int quantidade, @Valid @NotNull Usuario usuario,
			GatewayPagamento gatewayPagamento) {
		this.produtoEscolhido  = produtoParaCompra;
		this.quantidade = quantidade;
		this.usuario = usuario;
		this.gatewayPagamento = gatewayPagamento;

	}

	@Deprecated
	public Compra() {

	}
	
	public Usuario getComprador() {
		return usuario;
	}

	public Usuario getDonoProduto() {
		return produtoEscolhido.getUsuario();
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", produtoParaCompra=" + produtoEscolhido + ", quantidade=" + quantidade
				+ ", usuario=" + usuario + ", gatewayPagamento=" + gatewayPagamento + ", transacoes=" + transacoes
				+ "]";
	}

	public Long getId() {
		return id;
	}

	

	public int getQuantidade() {
		return quantidade;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public GatewayPagamento getGatewayPagamento() {
		return gatewayPagamento;
	}

	public String urlRedirecionamento(UriComponentsBuilder uri) {
		return this.gatewayPagamento.criaUrlRetorno(this, uri);
	}

	public void adicionaTransacao(@Valid RetornoGatewayPagamento request) {
		Transacao novaTransacao = request.toTransacao(this);
		
		Assert.isTrue(!this.transacoes.contains(novaTransacao), "Já existe uma transação" + novaTransacao);

		Assert.isTrue(transacoesConcluidasComSucesso().isEmpty(), "Essa compra já foi concluida");

		this.transacoes.add(novaTransacao);

	}

	private Set<Transacao> transacoesConcluidasComSucesso() {
		Set<Transacao> transacoesConcluidaComSucesso = this.transacoes.stream().filter(Transacao::concluidaComSucesso)
				.collect(Collectors.toSet());
		Assert.isTrue(transacoesConcluidaComSucesso.size() <= 1, "Não foi poissvel processar, tem mais de uma transação concluida"+this.id);
		
			return transacoesConcluidaComSucesso;
	}
	
	public boolean processadaComSucesso() {
		return !transacoesConcluidasComSucesso().isEmpty();
	}

}
