package br.com.mercadolivre.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import br.com.mercadolivre.request.CaracteristicasRequest;

@Entity
@Table(name = "caracteristicas")
public class Caracteristicas {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private String descricao;

    @ManyToOne
    private Produto produto;

    @Deprecated
    public Caracteristicas() {
    }

	public Caracteristicas(@NotBlank String nome,
			@NotBlank String descricao, @NotNull @Valid Produto produto) {
				this.nome = nome;
				this.descricao = descricao;
				this.produto = produto;
	}

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public Caracteristicas(CaracteristicasRequest caracteristicaRequest) {
        this.nome = caracteristicaRequest.getNome();
        this.descricao = caracteristicaRequest.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

 public String getDescricao() {
	 return descricao;
 }

    public Produto getProduto() {
        return produto;
    }

	@Override
	public String toString() {
		return "Caracteristicas [nome=" + nome + ", descricao=" + descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		return result;
	}

}