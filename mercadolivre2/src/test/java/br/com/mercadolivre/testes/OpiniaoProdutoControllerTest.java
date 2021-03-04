package br.com.mercadolivre.testes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mercadolivre.modelo.Opiniao;
import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.request.OpiniaoRequest;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OpiniaoProdutoControllerTest {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@PersistenceContext
	private EntityManager em;

	private String toJson(Object request) throws JsonProcessingException {
		return objectMapper.writeValueAsString(request);
	}

	@Test
	@DisplayName("Cadastrar uma opinião de um produto, com nota de 1 a 5, com titulo, descricao, usuario e e retornar status 200")
	@WithUserDetails("raphael@gmail.com") // logado
	public void deveCadastrarNovaOpiniaoParaUmProduto() throws Exception {
		OpiniaoRequest opiniaoForm = new OpiniaoRequest(3, "Gostei Teste", "Produto ok, bom custoxbeneficio");

		// Deve cadastrar opinião completa e retornar 200.
		mockMvc.perform(
				post("/produtos/1/opinioes").contentType(MediaType.APPLICATION_JSON).content(toJson(opiniaoForm)))
				.andExpect(status().is(200));

		Opiniao opiniao = (Opiniao) em.createQuery("select o from Opiniao o where o.titulo=:titulo")
				.setParameter("titulo", "Gostei Teste").getResultList().get(0);
		Produto produto = em.find(Produto.class, 1L);
		Usuario usuario = (Usuario) em.createQuery("select u from Usuario u where u.email=:email")
				.setParameter("email", "raphael@gmail.com").getResultList().get(0);

		assertEquals(produto.getId(), opiniao.getProduto().getId());
		assertTrue(opiniao.getNota() >= 1 && opiniao.getNota() <= 5);
		assertNotNull(opiniao.getTitulo(), opiniao.getDescricao());
		assertEquals(opiniao.getUsuario(), usuario);
		assertNotNull(opiniao.getUsuario());

	}

	private String opiniaoSemNota = "{\"nota\":\"\",\n" + "\t\"titulo\":\"Opiniao Teste\",\n"
			+ "\t\"descricao\":\"Não gostei tanto, poderia ser melhor\"}";

	private String opiniaoNota0 = "{\"nota\":\"0\",\n" + "\t\"titulo\":\"Adorei, nota 2\",\n"
			+ "\t\"descricao\":\"Não gostei tanto, poderia ser melhor\"}";

	private String opiniaoNota6 = "{\"nota\":\"6\",\n" + "\t\"titulo\":\"Adorei, nota 2\",\n"
			+ "\t\"descricao\":\"Não gostei tanto, poderia ser melhor\"}";

	private String opiniaoSemTitulo = "{\"nota\":\"2\",\n" + "\t\"titulo\":\"\",\n"
			+ "\t\"descricao\":\"Não gostei tanto, poderia ser melhor\"}";

	private String opiniaoSemDescricao = "{\"nota\":\"2\",\n" + "\t\"titulo\":\"Não gostei tanto assim\",\n"
			+ "\t\"descricao\":\"\"}";

	@Test
	@DisplayName("Não deve cadastrar opinião sem as condições pedidas, retorno 400")
	@WithUserDetails("raphael@gmail.com") // logado
	public void naoDeveCadastrarOpiniaoSemCamposPreenchidos() throws Exception {

		// Tentativa cadastrar opinião sem NOTA
		mockMvc.perform(post("/produtos/1/opinioes").contentType(MediaType.APPLICATION_JSON).content(opiniaoSemNota))
				.andExpect(status().is(400));

		// Tentativa cadastrar opinião com NOTA 0
		mockMvc.perform(post("/produtos/1/opinioes").contentType(MediaType.APPLICATION_JSON).content(opiniaoNota0))
				.andExpect(status().is(400));

		// Tentativa cadastrar opinião com NOTA 6
		mockMvc.perform(post("/produtos/1/opinioes").contentType(MediaType.APPLICATION_JSON).content(opiniaoNota6))
				.andExpect(status().is(400));

		// Tentativa cadastrar opinião sem TITULO
		mockMvc.perform(post("/produtos/1/opinioes").contentType(MediaType.APPLICATION_JSON).content(opiniaoSemTitulo))
				.andExpect(status().is(400));

		// Tentativa cadastrar opinião sem DESCRICAO
		mockMvc.perform(
				post("/produtos/1/opinioes").contentType(MediaType.APPLICATION_JSON).content(opiniaoSemDescricao))
				.andExpect(status().is(400));

	}

}
