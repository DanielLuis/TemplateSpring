package br.com.testando.teste.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Metodos estaticos utilitarios para trabalhar com Strings ou colecoes de Strings
 * 
 * @author WDEV
 */
public class Strings {

	/**
	 * Normaliza o CEP colocando tamanho 8 e preenchendo com zeros a esquerda se necessario
	 * 
	 * @param cep CEP original
	 * @return CEP normalizado
	 */
	public static String normalizarCep(String cep) {
		if (StringUtils.isBlank(cep)) {
			return null;
		}
		return StringUtils.leftPad(removerCaracteresNaoNumericos(cep), 8, '0');
	}

	/**
	 * Desnormaliza o CPF removendo toda a formatacao, mantendo apenas os numeros
	 * 
	 * @param cpf CPF original
	 * @return CPF normalizado
	 */
	public static String desnormalizarCPF(String cpf) {
		if (StringUtils.isNotBlank(cpf)) {
			return cpfComDigitosCompletos(cpf);
		}
		return null;
	}

	/**
	 * Desnormaliza o CPF removendo toda a formatacao, mantendo apenas os numeros
	 * 
	 * @param cpf CPF original
	 * @return CPF normalizado
	 */
	public static Long desnormalizarCPFParaNumero(String cpf) {
		if (StringUtils.isNotBlank(cpf)) {
			return Long.valueOf(removerCaracteresNaoNumericos(cpf));
		}
		return null;
	}

	/**
	 * Remove os caracteres nao numericos do cpf e preenche com zeros a esquerda se necessario
	 * 
	 * @param cpf CPF original
	 * @return CPF apenas com caracteres numéricos e com zeros a esquerda se necessário
	 */
	protected static String cpfComDigitosCompletos(String cpf) {
		return StringUtils.leftPad(removerCaracteresNaoNumericos(cpf), 11, '0');
	}

	/**
	 * Normaliza o CNPJ mantendo-o com tamanho 14 e com zeros a esquerda se necessario
	 * 
	 * @param cnpj cnpj original
	 * @return cnpj normalizado
	 */
	public static String normalizarCNPJ(String cnpj) {
		if (cnpj == null) {
			return null;
		}
		return StringUtils.leftPad(removerCaracteresNaoNumericos(cnpj), 14, '0');
	}

	public static String removerCaracteresNaoNumericos(String texto) {
		return texto.replaceAll("[^\\d]", "");
	}

	/**
	 * A partir de uma lista de Strings retorna um Set com os valores duplicados dessa lista.
	 * 
	 * @param lista Lista completa com as Strings
	 * @return Set com as strings duplicadas
	 */
	public static Set<String> encontrarDuplicados(List<String> lista) {
		List<String> copia = new ArrayList<String>(lista);
		for (String value : new HashSet<String>(lista)) {
			copia.remove(value);
		}
		return new HashSet<String>(copia);
	}

	public static String normalizarTelefone(String telefone) {
		if (StringUtils.isBlank(telefone)) {
			return null;
		}
		return removerCaracteresNaoNumericos(telefone);
	}

	public static String maiusculas(String texto) {
		if (StringUtils.isBlank(texto)) {
			return null;
		}
		return texto.toUpperCase();
	}

	/**
	 * Metodo responsavel por verificar se a String e vazia ou nula.
	 * 
	 * @param string - objeto a ser verificado.
	 * @return boolean - true/false
	 */
	public static boolean isBlankOrNull(String string) {
		return StringUtils.isBlank(string);
	}

	public static boolean isNotBlankOrNull(String string) {
		return !isBlankOrNull(string);
	}

	/**
	 * Retorna uma String vazia com o tamanho informado
	 * 
	 * @param tamanho - referente tamanho da string
	 * @return String vazia com o tamanho informado
	 */
	public static String espacos(int tamanho) {
		return StringUtils.leftPad("", tamanho, " ");
	}

}