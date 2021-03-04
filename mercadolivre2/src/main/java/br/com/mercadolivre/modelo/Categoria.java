package br.com.mercadolivre.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="categorias")
public class Categoria {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotBlank
	@Length(min=1)
	private String nome;
	
	@ManyToOne
	private Categoria categoriaMae;
	

	public void setCategoriaMae(Categoria categoriaMae) {
		this.categoriaMae = categoriaMae;
	}

	public Categoria() {
		
	}
	

	public Categoria(@NotBlank @Length(min = 1) String nome) {
		
		this.nome = nome;
	}


	public Categoria(@NotBlank @Length(min = 1) String nome, Categoria categoriaMae) {
		super();
		this.nome = nome;
		this.categoriaMae = categoriaMae;
	}


	public Long getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}
	public String setNome() {
		return nome;
	}


	public Categoria getCategoriaMae() {
		return categoriaMae;
	}


	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nome=" + nome + ", categoriaMae=" + categoriaMae + "]";
	}
	
	
	
	
	

	
	
}
