package br.com.mercadolivre.fechamentocompra;

import javax.validation.constraints.Positive;

import com.sun.istack.NotNull;

public class CompraRequest {
		
	@Positive
	private int quantidade;
	
	@NotNull
	private Long idProduto;
	
	@NotNull
	private GatewayPagamento gateway;

	public CompraRequest(@Positive int quantidade, @NotNull Long idProduto, GatewayPagamento gateway) {
		super();
		this.quantidade = quantidade;
		this.idProduto = idProduto;
		this.gateway = gateway;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public GatewayPagamento getGateway() {
		return gateway;
	}
	
	
}
