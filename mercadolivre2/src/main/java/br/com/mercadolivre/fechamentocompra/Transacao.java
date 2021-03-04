package br.com.mercadolivre.fechamentocompra;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

@Entity
@Table(name = "transacoes")
public class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private StatusTransacao status;

	private @NotBlank String idTransacaoGateway;
	@NotNull
	private LocalDateTime instante;
	@ManyToOne
	private @Valid Compra compra;

	public Transacao(@NotNull StatusTransacao status, @NotBlank String idTransacaoGateway,
			@NotNull @Valid Compra compra) {
		this.status = status;
		this.idTransacaoGateway = idTransacaoGateway;
		this.compra = compra;
		this.instante = LocalDateTime.now();

	}

	@Deprecated
	public Transacao() {

	}

	public boolean concluidaComSucesso() {
		return this.status.equals(StatusTransacao.sucesso);
	}

	@Override
	public String toString() {
		return "Transacao [id=" + id + ", status=" + status + ", idTransacaoGateway=" + idTransacaoGateway
				+ ", instante=" + instante + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idTransacaoGateway == null) ? 0 : idTransacaoGateway.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idTransacaoGateway == null) {
			if (other.idTransacaoGateway != null)
				return false;
		} else if (!idTransacaoGateway.equals(other.idTransacaoGateway))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
