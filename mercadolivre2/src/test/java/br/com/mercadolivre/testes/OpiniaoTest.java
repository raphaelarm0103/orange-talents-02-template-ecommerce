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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mercadolivre.request.OpiniaoRequest;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OpiniaoTest {

	@Autowired
	ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Cadastra opinião")
	@WithUserDetails("raphael@gmail.com")
	public void deveCadastrarUmaOpiniao() throws Exception {
		OpiniaoRequest opiniao = new OpiniaoRequest(3, "Gostei do produto", "Muito bom atendeu");
		String objeto = objectMapper.writeValueAsString(opiniao);

		mockMvc.perform(post("/produtos/1/opinioes").contentType(MediaType.APPLICATION_JSON).content(objeto))
				.andExpect(status().isOk());

		assertNotNull(opiniao.getTitulo());

	}
	@Test
	@DisplayName("Cadastra opinião sem estar logado")
	public void naoDeveCadastrarSemLogar() throws Exception {
		OpiniaoRequest opiniao = new OpiniaoRequest(3, "Gostei do produto", "Muito bom atendeu");
		String objeto = objectMapper.writeValueAsString(opiniao);

		mockMvc.perform(post("/produtos/1/opinioes").contentType(MediaType.APPLICATION_JSON).content(objeto))
				.andExpect(status().is(500));

		assertNotNull(opiniao.getTitulo());

	}

}
