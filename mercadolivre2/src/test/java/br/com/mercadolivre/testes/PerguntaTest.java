package br.com.mercadolivre.testes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mercadolivre.request.ImagemRequest;
import br.com.mercadolivre.request.PerguntaRequest;
import io.jsonwebtoken.lang.Assert;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PerguntaTest {
		
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private EntityManager manager;
	
	@Test
	@DisplayName("Teste de adicionar Perguntas")
	@WithUserDetails("raphael@gmail.com")
	public void deveAdicionarUmaImagemAoProduto() throws Exception{
		PerguntaRequest perguntaRequest = new PerguntaRequest("Tem garantia?");
				String objeto = objectMapper.writeValueAsString(perguntaRequest);
				
				mockMvc.perform(post("/produtos/1/perguntas").contentType(MediaType.APPLICATION_JSON).content(objeto))
				.andExpect(status().isOk());
				
				Assert.notNull(perguntaRequest.getTitulo());
}
	
	
	@Test
	@DisplayName("Não deve adicionar Perguntas sem Titulo")
	@WithUserDetails("raphael@gmail.com")
	public void naoDeveAdicionarPerguntaSemTitulo() throws Exception{
		PerguntaRequest perguntaRequest = new PerguntaRequest(" ");
				String objeto = objectMapper.writeValueAsString(perguntaRequest);
				
				mockMvc.perform(post("/produtos/1/perguntas").contentType(MediaType.APPLICATION_JSON).content(objeto))
				.andExpect(status().isBadRequest());
				
				Assert.notNull(perguntaRequest.getTitulo());
}
}
