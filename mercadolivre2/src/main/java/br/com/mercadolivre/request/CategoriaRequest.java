package br.com.mercadolivre.request;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

import org.springframework.context.annotation.DependsOn;

import com.sun.istack.NotNull;

import org.springframework.util.Assert;
import br.com.mercadolivre.modelo.Categoria;
import br.com.mercadolivre.validadors.ExistsId;
import br.com.mercadolivre.validadors.UniqueValue;

public class CategoriaRequest {

	@NotBlank
	@UniqueValue(domainClass = Categoria.class, fieldName = "nome")
	private String nome;

	@ExistsId(domainClass = Categoria.class, fieldName = "id")
	private Long idCategoriaMae;

	

	public CategoriaRequest(@NotBlank String nome, Long idCategoriaMae) {
		super();
		this.nome = nome;
		this.idCategoriaMae = idCategoriaMae;
	}

	public CategoriaRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	@Deprecated
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdCategoriaMae() {
		return idCategoriaMae;
	}
	
	

	public Categoria converter(EntityManager manager) {
		Categoria categoria = new Categoria(nome);
		if (this.idCategoriaMae != null) {
			Categoria categoriaMae = manager.find(Categoria.class, idCategoriaMae);

			Assert.state(categoriaMae != null, "Id da categoria Mãe está inválido");

			categoria.setCategoriaMae(categoriaMae);
		}
		return categoria;
	}

}
