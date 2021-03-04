package br.com.mercadolivre.fechamentocompra;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

public class PagSeguroRequest implements RetornoGatewayPagamento {
		
		@NotBlank
		private String idTransacao;
		
		@NotNull
		private StatusRetornoPagSeguro status;

		public PagSeguroRequest(@NotBlank String idTransacao, StatusRetornoPagSeguro status) {
			super();
			this.idTransacao = idTransacao;
			this.status = status;
		}

		@Override
		public String toString() {
			return "PagSeguroRequest [idTransacao=" + idTransacao + ", status=" + status + "]";
		}

		public Transacao toTransacao(Compra compra) {
			
			return new Transacao(status.normaliza(), idTransacao, compra);
		}
		
		
}
