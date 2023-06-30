package br.inatel.labs.labrest.server.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.inatel.labs.labrest.server.model.Produto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProdutoControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void deveListarProdutos() {
		webTestClient
			.get()
			.uri("/produto")
			.exchange()
			.expectStatus()
				.isOk()
			.expectBody()
				.returnResult();
	}
	
	@Test
	void dadoProdutoIdValido_quandoGetProdutoPeloId_entaoRespondeComProdutoValido() {
		Long produtoIdValido = 1L;
		
		Produto produtoRespondido = webTestClient.get()
				.uri("/produto/" + produtoIdValido)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Produto.class)
				.returnResult()
				.getResponseBody();
		assertNotNull(produtoRespondido);
		assertEquals(produtoRespondido.getId(), produtoIdValido);
	}
	
	@Test
	void dadoProdutoIdInvalido_quandoGetProdutoPeloId_entaoRespondeComStatusNotFound() {
		Long idInvalido = 99L;
		
		webTestClient.get()
		.uri("/produto/" + idInvalido)
		.exchange()
		.expectStatus().isNotFound();
	}
	
	@Test
	void dadoNovoProduto_quandoPostProduto_entaoRespondeComStatusCreatedEProdutoValido() {
		Produto prod = new Produto(4L, "Serra Copo", new BigDecimal(150.00));
		
		webTestClient.post()
		.uri("/produto")
		.bodyValue(prod)		
		.exchange()
		.expectStatus().isCreated();
		assertEquals(prod.getId(), 4L);
	}
	
	@Test
	void dadoProdutoExistente_quandoPutProduto_entaoRespondeComStatusNoContent() {
		Produto prodExistente = new Produto();
		prodExistente.setId(2L);
		prodExistente.setDescricao("Alteracao do produto");
		
		webTestClient.put()
		.uri("/produto")
		.bodyValue(prodExistente)
		.exchange()
		.expectStatus().isNoContent();		
	}
	
	@Test
	void dadoProdutoIdValido_quandoDeleteProdutoPeloId_entaoRespondeComStatusNoContent() {
		Long produtoIdValido = 1L;
		
		webTestClient.delete()
		.uri("/produto/" + produtoIdValido)
		.exchange()
		.expectStatus().isNoContent();		
	}
	
	@Test
	void dadoProdutoIdInvalido_quandoDeleteProdutoPeloId_entaoRespondeComStatusNotFound() {
		Long produtoIdInvalido = 100L;
		webTestClient.delete()
		.uri("/produto/" + produtoIdInvalido)
		.exchange()
		.expectStatus().isNotFound();
	}
}