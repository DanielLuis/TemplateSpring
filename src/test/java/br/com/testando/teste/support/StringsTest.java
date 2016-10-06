package br.com.testando.teste.support;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StringsTest {

	@Test
	public void deveRetornarNullQuandoCepInformadoForNull() throws Exception {
		//Dado
		String cep = null;

		//Quando
		String cepNormalizado = Strings.normalizarCep(cep);

		//Entao
		assertNull(cepNormalizado);
	}

	@Test
	public void deveRetornarNullQuandoCNpjInformadoForNull() throws Exception {
		//Dado
		String cnpj = null;

		//Quando
		String cnpjNormalizado = Strings.normalizarCNPJ(cnpj);

		//Entao
		assertNull(cnpjNormalizado);
	}
}