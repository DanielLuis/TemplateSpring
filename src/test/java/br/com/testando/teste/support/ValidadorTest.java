package br.com.testando.teste.support;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.testando.teste.support.Validador;

public class ValidadorTest {

	private String rotulo = "Rotulo";

	@Test
	public void deveObterSucessoQuandoNomeForValido() throws Exception {
		assertTrue(new Validador().nome("Manoel Rodrigues", rotulo));
		assertTrue(new Validador().nome("Luis Inacio 'Lula' da Silva", rotulo));
		assertTrue(new Validador().nome("Ruth-Carolina da Silva", rotulo));
		assertTrue(new Validador().nome("Manoel G. Rodrigues", rotulo));
		assertTrue(new Validador().nome("Manoel Garcia Rodrigues", rotulo));
		assertTrue(new Validador().nome(" Manoel  Rodrigues", rotulo));
		assertTrue(new Validador().nome("	Manoel 	 Rodrigues", rotulo));
		assertTrue(new Validador().nome("Mohamed's Baba", rotulo));
		assertTrue(new Validador().nome("Manoel", rotulo));
		assertTrue(new Validador().nome("	Manoel", rotulo));
		assertTrue(new Validador().nome("Manoel Garcia R", rotulo));
		assertTrue(new Validador().nome("Manoel R", rotulo));
		assertTrue(new Validador().nome("Su T", rotulo));
		assertTrue(new Validador().nome("Ali", rotulo));
	}

	@Test
	public void deveRetornarFalsoQuandoNomeNaoForValido() throws Exception {
		assertFalse(new Validador().nome(null, rotulo));
		assertFalse(new Validador().nome("", rotulo));
		assertFalse(new Validador().nome("Manoel Rodrigues 2", rotulo));
		assertFalse(new Validador().nome("Manoel \\ Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel ? Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel \" Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel ( Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel [ Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel { Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel ! Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel @ Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel % Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel $ Rodrigues", rotulo));
		assertFalse(new Validador().nome("Manoel # Rodrigues", rotulo));
		assertFalse(new Validador().nome("Luis Inacio \"Lula\" da Silva", rotulo));
		assertFalse(new Validador().nome("Manoel _ Rodrigues", rotulo));
	}

	@Test
	public void deveObterSucessoQuandoNomeCompletoForValido() throws Exception {
		assertTrue(new Validador().nomeCompleto("Manoel Rodrigues", rotulo));
		assertTrue(new Validador().nomeCompleto("Luis Inacio 'Lula' da Silva", rotulo));
		assertTrue(new Validador().nomeCompleto("Ruth-Carolina da Silva", rotulo));
		assertTrue(new Validador().nomeCompleto("Manoel Qujeiroz Rodrigues", rotulo));
		assertTrue(new Validador().nomeCompleto(" Manoel  Rodrigues", rotulo));
		assertTrue(new Validador().nomeCompleto("	Manoel 	 Rodrigues", rotulo));
		assertTrue(new Validador().nomeCompleto("Mohamed's Baba", rotulo));
		assertTrue(new Validador().nomeCompleto("Manoel Q. Rodrigues", rotulo));
	}

	@Test
	public void deveRetornarFalsoQuandoNomeCompletoNaoForValido() throws Exception {
		assertFalse(new Validador().nomeCompleto(null, rotulo));
		assertFalse(new Validador().nomeCompleto("", rotulo));
		assertFalse(new Validador().nomeCompleto("	Manoel", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel Queiroz M", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel M", rotulo));
		assertFalse(new Validador().nomeCompleto("Su T", rotulo));
		assertFalse(new Validador().nomeCompleto("Ali", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel Rodrigues 2", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel \\ Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel ? Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel \" Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel ( Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel [ Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel { Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel ! Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel @ Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel % Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel $ Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel # Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Luis Inacio \"Lula\" da Silva", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel _ Rodrigues", rotulo));
		assertFalse(new Validador().nomeCompleto("Manoel Q Rodrigues", rotulo));
	}

	@Test
	public void deveValidarEmail() throws Exception {
		// Invalidos
		assertFalse(new Validador().email("email", rotulo));
		assertFalse(new Validador().email("email@", rotulo));
		assertFalse(new Validador().email("email@servico", rotulo));
		assertFalse(new Validador().email(".@servico.com", rotulo));
		assertFalse(new Validador().email("email@ser,vico.com", rotulo));
		assertFalse(new Validador().email("em,ail@servico.com", rotulo));
		assertFalse(new Validador().email(".email@servico.com", rotulo));
		assertFalse(new Validador().email("email.@servico.com", rotulo));
		assertFalse(new Validador().email("", rotulo));
		assertFalse(new Validador().email(null, rotulo));

		// Validos
		assertTrue(new Validador().email("email@servico.com", rotulo));
		assertTrue(new Validador().email("email@servico.com.br", rotulo));
		assertTrue(new Validador().email("email@servico.com.br.eu", rotulo));
		assertTrue(new Validador().email("email.mais@servico.com.br", rotulo));
	}

	@Test
	public void deveValidarObjetoObrigatorio() throws Exception {
		// Invalido
		Long valor = null;
		assertFalse(new Validador().obrigatorio(valor, rotulo));

		// Valido
		assertTrue(new Validador().obrigatorio(new BigDecimal("15.00"), rotulo));
	}

	@Test
	public void deveValidarDDD() throws Exception {
		// Invalidos
		assertFalse(new Validador().ddd(null, rotulo));
		assertFalse(new Validador().ddd("", rotulo));
		assertFalse(new Validador().ddd("09", rotulo));
		assertFalse(new Validador().ddd("1", rotulo));
		assertFalse(new Validador().ddd("021", rotulo));
		assertFalse(new Validador().ddd("00", rotulo));
		assertFalse(new Validador().ddd("0", rotulo));

		// Validos
		assertTrue(new Validador().ddd("21", rotulo));
		assertTrue(new Validador().ddd("11", rotulo));
		assertTrue(new Validador().ddd("89", rotulo));
	}

	@Test
	public void deveValidarExpressaoVerdadeira() throws Exception {
		assertFalse(new Validador().verdadeiro(false, rotulo));
		assertTrue(new Validador().verdadeiro(true, rotulo));
	}

	@Test
	public void deveValidarExpressaoFalse() throws Exception {
		assertFalse(new Validador().falso(true, rotulo));
		assertTrue(new Validador().falso(false, rotulo));
	}

	@Test
	public void deveValidarNumeroTelefoneValido() throws Exception {
		// Invalidos
		assertFalse(new Validador().numeroTelefone(null, rotulo));
		assertFalse(new Validador().numeroTelefone("", rotulo));
		assertFalse(new Validador().numeroTelefone("11111111", rotulo));
		assertFalse(new Validador().numeroTelefone("111111111", rotulo));
		assertFalse(new Validador().numeroTelefone("123", rotulo));
		assertFalse(new Validador().numeroTelefone("333", rotulo));
		assertFalse(new Validador().numeroTelefone("0", rotulo));

		// Validos
		assertTrue(new Validador().numeroTelefone("22222222", rotulo));
		assertTrue(new Validador().numeroTelefone("999999999", rotulo));
		assertTrue(new Validador().numeroTelefone("88292828", rotulo));
	}

	@Test
	public void deveAdicionarErro() throws Exception {
		// Dado
		Validador validador = new Validador();

		// Quando
		validador.adicionarErro("mensagem.erro");

		// Entao
		assertTrue(validador.possuiErro());
		assertFalse(validador.getMensagens().isEmpty());
	}
}