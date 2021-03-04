package br.com.mercadolivre.testes;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mercadolivre.request.ImagemRequest;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ImagemTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private EntityManager manager;
	
	@Test
	@DisplayName("Teste de adicionar imagem")
	@WithUserDetails("raphael@gmail.com")
	public void deveAdicionarUmaImagemAoProduto() throws Exception{
		ImagemRequest imagem = new ImagemRequest();
		
		
	}

}
