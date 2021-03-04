package br.com.mercadolivre.request;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.validadors.UniqueValue;

public class UsuarioRequest {
	
	@Email
	@NotBlank
	@UniqueValue(domainClass = Usuario.class, fieldName = "email")
	private String email;
	
	@NotNull
	@Length(min=6)
	
	private String senha;

	public UsuarioRequest(@Email @NotBlank String email, @Length(min = 6) String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public Usuario toModel() {
		return new Usuario(email, senha);
		
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	
	
	

}
