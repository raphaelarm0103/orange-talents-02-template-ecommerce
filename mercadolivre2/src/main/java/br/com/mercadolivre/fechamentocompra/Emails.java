package br.com.mercadolivre.fechamentocompra;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercadolivre.modelo.Pergunta;

@Service
public class Emails {
	
	@Autowired
	//1
	private Mailer mailer;

	//1
	public void novaPergunta(@NotNull @Valid Pergunta pergunta) {
		mailer.send("<html>...</html>","Nova pergunta...",pergunta.getUsuario().getEmail(),"novapergunta@nossomercadolivre.com",pergunta.getUsuario().getEmail());
	}

	public void novaCompra(Compra novaCompra) {
		mailer.send("nova compra..." + novaCompra, "Você tem uma nova compra",
				novaCompra.getComprador().getEmail(),
				"compras@nossomercadolivre.com",
				novaCompra.getDonoProduto().getEmail());
	}

	
}