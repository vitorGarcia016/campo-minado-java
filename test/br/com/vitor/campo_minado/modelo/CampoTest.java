package br.com.vitor.campo_minado.modelo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CampoTest {

	Campo campo;

	@BeforeEach
	void iniciarCampo() {
		campo = new Campo(3, 3);

	}

	@Test
	void adicionarVizinhoTestEsquerda() {
		Campo campo2 = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(campo2);
		assertTrue(resultado);
	}

	@Test
	void adicionarVizinhoTestDireita() {
		Campo campo2 = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(campo2);
		assertTrue(resultado);
	}

	@Test
	void adicionarVizinhoTestEmCima() {
		Campo campo2 = new Campo(2, 4);
		boolean resultado = campo.adicionarVizinho(campo2);
		assertTrue(resultado);
	}

	@Test
	void adicionarVizinhoTest2() {
		Campo campo2 = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(campo2);
		assertTrue(resultado);
	}

	@Test
	void adicionarNaoVizinhoTest() {
		Campo campo2 = new Campo(1, 1);
		boolean resultado = campo.adicionarVizinho(campo2);
		assertFalse(resultado);
	}

	@Test
	void valorPadraoMarcadoTest() {
		assertFalse(campo.isMarcado());
	}

	@Test
	void alterarMarcacaoTest() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}

	@Test
	void alterarMarcacaoDuasChamadasTest() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}

	@Test
	void abrirNaoMarcadoNaoMinadoTest() {
		assertTrue(campo.abrir());
	}

	@Test
	void abrirMarcadoNaoMinadoTest() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}

	@Test
	void abrirMarcadoMinadoTest() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}


	@Test
	void abrirComVizinhosTest() {
		Campo vizinho11 = new Campo(1, 1);
		Campo vizinho22 = new Campo(2, 2);
		vizinho22.adicionarVizinho(vizinho11);

		campo.adicionarVizinho(vizinho22);
		campo.abrir();

		assertTrue(vizinho22.isAberto() && vizinho11.isAberto());

	}

	@Test
	void abrirComVizinhosTest2() {
		Campo vizinho11 = new Campo(1, 1);
		Campo vizinho12 = new Campo(1, 1);
		vizinho12.minar();

		Campo vizinho22 = new Campo(2, 2);
		vizinho22.adicionarVizinho(vizinho11);
		vizinho22.adicionarVizinho(vizinho12);

		campo.adicionarVizinho(vizinho22);
		campo.abrir();

		assertTrue(vizinho22.isAberto() && !vizinho11.isAberto());

	}

}
