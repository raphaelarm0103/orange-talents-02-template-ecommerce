package br.com.mercadolivre.testes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;


import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.request.UsuarioRequest;
import br.com.mercadolivre.validadors.UsuarioLogado;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UsuarioTest {

	@Autowired
	ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Cadastra um Usuario")
	public void CadastraUsuario() throws Exception {
		UsuarioRequest usuario = new UsuarioRequest("raphael1234567@gmail.com", "123456"); // deve colocar um email novo a cada requisição de teste
		String objeto = objectMapper.writeValueAsString(usuario);

		mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(objeto))
				.andExpect(status().isOk());
	
		assertNotNull(usuario.getEmail());

	}
	
	@Test
	@DisplayName("Cadastrar usuario com email repetido, deve retornar 400")
	public void naoDeveCadastrarUsuarioComEmailRepetido() throws Exception{
	UsuarioRequest usuario = new UsuarioRequest("raphael123@gmail.com", "123456");
	String objeto = objectMapper.writeValueAsString(usuario);

	mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(objeto))
			.andExpect(status().isBadRequest());

	
	assertNotNull(usuario.getEmail());
	}
	
	
	@Test
	@DisplayName("Cadastrar usuario com email errado, deve retornar 400")
	public void naoPodeCadastrarEmailIncorreto() throws Exception{
		
	UsuarioRequest usuario = new UsuarioRequest("gmail.com", "123456");
	String objeto = objectMapper.writeValueAsString(usuario);
	
	

	mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON).content(objeto))
			.andExpect(status().isBadRequest());

	assertNotNull(usuario.getEmail());
	}
}
