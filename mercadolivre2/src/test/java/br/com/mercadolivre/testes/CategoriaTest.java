package br.com.mercadolivre.testes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


import br.com.mercadolivre.request.CategoriaRequest;



@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoriaTest {

	@Autowired
	ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	@DisplayName("Cadastra categoria com categoria Mae")
	public void deveCadastrarUmaCategoriaComMae() throws Exception{
		CategoriaRequest categoria = new CategoriaRequest("Sony", 1L);
		String objeto = objectMapper.writeValueAsString(categoria);
		
		
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON).content(objeto))
		.andExpect(status().isOk());
		
		assertNotNull(categoria.getNome());
	}
	
	@Test
	@DisplayName("Não Cadastra categoria com categoria Mae inexistente")
	public void naoDeveCadastrarSemCategoriaMae() throws Exception{
		CategoriaRequest categoria = new CategoriaRequest("Xiaomi", 4L);
		String objeto = objectMapper.writeValueAsString(categoria);
        
		
		
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON).content(objeto))
		.andExpect(status().isBadRequest());
		
		assertNotNull(categoria.getNome());
	
	}
	@Test
	@DisplayName("Não cadastra categoria duplicada")
	public void naoDeveCadastrarCategoriaDuplicada() throws Exception{
		CategoriaRequest categoria = new CategoriaRequest("Samsung", 1L);
		String objeto = objectMapper.writeValueAsString(categoria);
        
		
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON).content(objeto))
		.andExpect(status().isBadRequest());
		
		assertNotNull(categoria.getNome());
	
	}
	
}
