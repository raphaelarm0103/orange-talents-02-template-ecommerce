package br.com.mercadolivre.fechamentocompra;

import org.springframework.validation.BindException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;

import br.com.mercadolivre.validadors.UsuarioLogado;

@RestController
@RequestMapping("/compras")
public class CompraController1 {

	

	@Autowired
	private EntityManager manager;
	
	@Autowired
	private Emails emails;

	@PostMapping
	@Transactional
	public String criaCompra(@RequestBody @Valid CompraRequest request, UriComponentsBuilder uri, @AuthenticationPrincipal UsuarioLogado usuarioLogado) throws BindException {
		// 1
		Produto produtoParaCompra = manager.find(Produto.class, request.getIdProduto());

		int quantidade = request.getQuantidade();
		boolean abateu = produtoParaCompra.abateEstoque(request.getQuantidade());
		// 1
		if (abateu) {
			// 1
			Usuario usuario = usuarioLogado.get();
			
			GatewayPagamento gateway = request.getGateway();

			Compra compra = new Compra(produtoParaCompra, quantidade, usuario, request.getGateway());
			manager.persist(compra);
			
			return compra.urlRedirecionamento(uri);

		}
		BindException problemaComEstoque = new BindException(request, "CompraReqeuest");
		problemaComEstoque.reject(null, "Não foi possivel realizar a compra");

		throw problemaComEstoque;
	}

}
