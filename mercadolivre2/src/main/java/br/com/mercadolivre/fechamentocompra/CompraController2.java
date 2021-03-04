package br.com.mercadolivre.fechamentocompra;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CompraController2 {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private EventosNovaCompra eventosNovaCompra;

	@PostMapping(value = "/retorno-pagseguro/{id}")
	@Transactional
	public String processamentoPagSeguro(@PathVariable("id") Long idCompra,
			@RequestBody @Valid PagSeguroRequest request) {

		return processa(idCompra, request);
	}

	@PostMapping(value = "/retorno-paypal/{id}")
	@Transactional
	public String processamentoPayPal(@PathVariable("id") Long idCompra, @RequestBody @Valid PayPalRequest request) {

		return processa(idCompra, request);
	}

	private String processa(Long idCompra, RetornoGatewayPagamento retornoGatewayPagamento) {
		Compra compra = manager.find(Compra.class, idCompra);
		compra.adicionaTransacao(retornoGatewayPagamento);

		manager.merge(compra);

		eventosNovaCompra.processa(compra);

		return compra.toString();
	}

}
