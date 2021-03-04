package br.com.mercadolivre.fechamentocompra;

public interface RetornoGatewayPagamento {
	
	//retorna uma nova transação em função do gateway especifico
	Transacao toTransacao(Compra compra);
}
