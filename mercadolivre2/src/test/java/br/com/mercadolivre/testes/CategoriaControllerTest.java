package br.com.mercadolivre.testes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mercadolivre.modelo.Categoria;
import br.com.mercadolivre.request.CategoriaRequest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CategoriaControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager em;

	private final String json1 = "{\"nome\" : \"Categoria\", \"idCategoriaMae\" : \"\"}";

	private final String json2 = "{\"nome\" : \"Samsung\", \"idCategoriaMae\" : \"1\"}";

	private final String json3 = "{\"nome\" : \"\", \"idCategoriaMae\" : \"\"}";

	@Test
	@DisplayName("Não deve criar categoria sem categoria Mãe")
	@WithUserDetails("raphael@gmail.com")
	public void criarCategoriaSemCategoriaMae() throws Exception {
		Categoria categoria = new Categoria(json1);
		em.persist(categoria);

		CategoriaRequest request = new CategoriaRequest("Samsung", categoria.getId());

		// categoria sem mae
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON).content(json1))
				.andExpect(status().isOk());

		Categoria categoria1 = (Categoria) em.createQuery("select c from Categoria c where c.nome=:nome")
				.setParameter("nome", "Samsung").getResultList().get(0);

		assertNotNull(categoria1.getCategoriaMae());
	}

	@Test
	@DisplayName("Não deve criar categoria com nome em branco ou duplicado, e vai retornar 400")
	@WithUserDetails("raphael@gmail.com") // logado
	public void naoDeveCriarCategoriaComNomeDuplicadoOuEmBranco() throws Exception {
		Categoria categoria = new Categoria(json3);
		em.persist(categoria);

		CategoriaRequest request = new CategoriaRequest("Samsung", categoria.getId());

		// categoria sem mae
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON).content(json3))
				.andExpect(status().is(400));

	}

	@Test
	@DisplayName("Não deve cadastrar categoria sem estar logado, e vai retornar 400")
	// nao esta logado
	public void naoDeveCriarCategoriaSemEstarLogado() throws Exception {

		// Cadastra categoria com categoria mãe
		mockMvc.perform(post("/categoria").contentType(MediaType.APPLICATION_JSON).content(json2))
				.andExpect(status().is(400));
	}

}
