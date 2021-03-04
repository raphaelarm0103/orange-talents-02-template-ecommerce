package br.com.mercadolivre.fechamentocompra;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventosNovaCompra {

	@Autowired
	//1
	private Set<EventoCompraSucesso> eventosCompraSucesso;

	public void processa(Compra compra) {
		//1
		if(compra.processadaComSucesso()) {
			//1
			eventosCompraSucesso.forEach(evento -> evento.processa(compra));
		} 
		else {
			//o evento falha
		}		
	}

}