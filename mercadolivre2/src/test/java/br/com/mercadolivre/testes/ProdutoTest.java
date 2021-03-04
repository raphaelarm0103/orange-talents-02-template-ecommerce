package br.com.mercadolivre.testes;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.transaction.annotation.Transactional;

import br.com.mercadolivre.modelo.Categoria;
import br.com.mercadolivre.modelo.Produto;
import br.com.mercadolivre.modelo.Usuario;
import br.com.mercadolivre.request.CaracteristicasRequest;

@Transactional
public class ProdutoTest {

	@DisplayName("3 Caracteristicas para o produto")
	@ParameterizedTest
	@MethodSource("Teste1")
	void teste1(Collection<CaracteristicasRequest> caracteristicas) throws Exception {
		Categoria categoria = new Categoria("categoria");
		Usuario usuario = new Usuario("email@email.com", "123456");

		new Produto("nome", 10, "descricao", BigDecimal.TEN, categoria, usuario,
				(List<CaracteristicasRequest>) caracteristicas);

	}

	static Stream<Arguments> Teste1() {
		return Stream.of(Arguments.of(List.of(new CaracteristicasRequest("nome", "valor"),
				new CaracteristicasRequest("nome2", "descricao"), new CaracteristicasRequest("nome3", "descricao"))),
				Arguments.of(List.of(new CaracteristicasRequest("chave", "valor"),
						new CaracteristicasRequest("nome2", "descricao"), new CaracteristicasRequest("nome3", "descricao"),
						new CaracteristicasRequest("nome4", "descricao")

				)));
	}

	@DisplayName("Não pode ser criado com menos de 3 caracteristicas")
	@ParameterizedTest
	@MethodSource("Teste2")
	void teste2(Collection<CaracteristicasRequest> caracteristicas) throws Exception {
		Categoria categoria = new Categoria("categoria");
		Usuario usuario = new Usuario("email@email.com", "123456");

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Produto("nome", 10, "descricao", BigDecimal.TEN, categoria, usuario,
					(List<CaracteristicasRequest>) caracteristicas);
		});
	}

	static Stream<Arguments> Teste2() {
		return Stream.of(Arguments
				.of(List.of(new CaracteristicasRequest("nome", "descricao"), new CaracteristicasRequest("nome2", "descricao")

				)), Arguments.of(List.of((new CaracteristicasRequest("nome", "descricao")))));

	}
}