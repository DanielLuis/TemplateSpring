package br.com.testando.teste.support;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import br.com.testando.teste.support.BusinessException;

import com.google.common.collect.Lists;

/**
 * Classe utilizada para auxiliar na validacao de regras. 
 * 
 * @author WDEV
 */
public class Validador {

	private static final Pattern PADRAO_CARACTERES_NAO_PERMITIDOS_NOME = Pattern.compile("[^'\\-\\.\\s\\pL\\pM\\p{Nl}[\\p{InEnclosedAlphanumerics}&&\\p{So}]]");
	private static final Pattern PADRAO_EMAIL_VALIDO = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	private static final Pattern PADRAO_DDD_VALIDO = Pattern.compile("(10)|([1-9][1-9])");
	private static final Pattern PADRAO_NUMERO_TELEFONE_VALIDO = Pattern.compile("[2-9][0-9]{7,8}");

	private List<Mensagem> mensagens = Lists.newArrayList();

	/**
	 * Adiciona uma mensagem de erro na lista de mensagens
	 * 
	 * @param messageKey Chave da mensagem
	 * @param params Parametros da mensagem
	 */
	public void adicionarErro(String messageKey, Object... params) {
		mensagens.add(Mensagem.erro(messageKey, params));
	}

	/**
	 * Indica se existe algum erro na validacao
	 * @return true se existir erro na validacao
	 */
	public boolean possuiErro() {
		return !mensagens.isEmpty();
	}

	/**
	 * Retorna as mensagens de erro
	 * 
	 * @return Mensagens de erro
	 */
	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	/**
	 * Valida se uma determinada string foi informada.
	 * 
	 * @param valor Valor String
	 * @param rotulo Rotulo do campo
	 * @return true se a string informada passar na regra de obrigatoriedade
	 */
	public boolean obrigatorio(String valor, String rotulo) {
		if (StringUtils.isBlank(valor)) {
			adicionarErro("msg.erro.campo.obrigatorio", rotulo);
		} else {
			return true;
		}
		return false;
	}

	/**
	 * Valida se um determinado objeto foi informado.
	 * 
	 * @param objeto Objeto
	 * @param rotulo Rotulo do campo
	 * @return true se o objeto informado passar na regra de obrigatoriedade
	 */
	public boolean obrigatorio(Object objeto, String rotulo) {
		if (objeto == null) {
			adicionarErro("msg.erro.campo.obrigatorio", rotulo);
		} else {
			return true;
		}
		return false;
	}

	/**
	 * Valida as regras de um nome completo.
	 * <ul>
	 * 	<li>Deve ser preenchido</li>
	 * 	<li>Nao pode possuir caracteres especiais. Por exemplo: @, #, (, %, etc.</li>
	 * 	<li>Precisa ser nome completo (mais de 2 palavras)</li>
	 * 	<li>O primeiro nome precisa ter mais de 2 letras</li>
	 * 	<li>O ultimo nome precisa ter mais de 2 letras</li>
	 * </ul>
	 * 
	 * @param nome Nome
	 * @param rotulo Rotulo do campo
	 * @return true se o nome completo passar nas regras
	 */
	public boolean nomeCompleto(String nome, String rotulo) {
		if (nome(nome, rotulo)) {
			// Remove os varios espacos em branco transformando em apenas um (ex.: "Teste     da Silva" -> Teste da Silva)
			nome = nome.replaceAll("\\t", " ").replaceAll("\\s+", " ").trim();
			String[] nomes = nome.split("\\s");
			if (nomes.length < 2) {
				adicionarErro("msg.erro.nome.incompleto", rotulo);
			} else {
				// Nao pode abreviar nenhuma parte do nome
				for (int i = 0; i < nomes.length; i++) {
					if (nomes[i].length() < 2) {
						adicionarErro("msg.erro.nome.incompleto", rotulo);
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Valida as regras de um nome (nao precisa ser completo). Ex.: Jose
	 * <ul>
	 * 	<li>Deve ser preenchido</li>
	 * 	<li>Nao pode possuir caracteres especiais. Por exemplo: @, #, (, %, etc.</li>
	 * 	<li>O primeiro nome precisa ter mais de 2 letras</li>
	 * 	<li>O ultimo nome precisa ter mais de 2 letras</li>
	 * </ul>
	 * @param nome Nome
	 * @param rotulo Rotulo do campo
	 * @return true se o nome passar nas regras
	 */
	public boolean nome(String nome, String rotulo) {
		if (possuiCaracteresValidos(nome, rotulo)) {
			return true;
		}
		return false;
	}

	/**
	 * Valida se o nome possui apenas caracters validos.
	 * 
	 * @param nome Nome
	 * @param rotulo Rotulo do campo
	 * @return true se o nome nao possuir caracteres especiais
	 */
	public boolean possuiCaracteresValidos(String nome, String rotulo) {
		if (obrigatorio(nome, rotulo)) {
			if (PADRAO_CARACTERES_NAO_PERMITIDOS_NOME.matcher(nome).find()) {
				adicionarErro("msg.erro.nome.caracteres.invalido", rotulo);
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se o email informado e valido e adiciona mensagem de erro caso contrario.
	 * Considera valido o seguinte padrao:
	 * 	xxxxx@xxx.com
	 * 	xxxxx@xxx.com.br
	 * 
	 * @param email Email
	 * @param rotulo Rotulo do campo
	 * @return true se o email for valido
	 */
	public boolean email(String email, String rotulo) {
		if (StringUtils.isNotBlank(email) && PADRAO_EMAIL_VALIDO.matcher(email).matches()) {
			return true;
		}
		adicionarErro("msg.erro.email.invalido", rotulo);
		return false;
	}

	/**
	 * Verifica se o DDD do telefone informado e valido e adiciona mensagem de erro caso contrario.
	 * Considera valido o seguinte padrao:
	 * 	- DDD = 10
	 * 	- Algum numero iniciando de 1 a 9 e terminando de 1 a 9
	 * 
	 * @param ddd DDD do telefone
	 * @param rotulo Rotulo do campo
	 * @return true se o email for valido
	 */
	public boolean ddd(String ddd, String rotulo) {
		if (ddd != null && PADRAO_DDD_VALIDO.matcher(String.valueOf(ddd)).matches()) {
			return true;
		}
		adicionarErro("msg.erro.cadastro.proposta.telefone.ddd.invalido", rotulo);
		return false;
	}

	/**
	 * Verifica se o numero do telefone informado e valido e adiciona mensagem de erro caso contrario.
	 * Considera valido o seguinte padrao:
	 * 	- Primeiro digito de 2 a 9;
	 * 	- Seguido de 7 ou 8 digitos de 0 a 9;
	 * @param numeroTelefone Numero do telefone
	 * @param rotulo Rotulo do campo
	 * @return true se o numero do telefone for valido
	 */
	public boolean numeroTelefone(String numeroTelefone, String rotulo) {
		if (obrigatorio(numeroTelefone, MessageFormat.format("Numero do telefone do {0}", rotulo))) {
			return verdadeiro(PADRAO_NUMERO_TELEFONE_VALIDO.matcher(numeroTelefone).matches(), "msg.erro.cadastro.proposta.numero.telefone.invalido", rotulo);
		}
		return false;
	}

	/**
	 * Valida se uma determinada expressao e verdadeira.
	 * 
	 * @param expressao Expressao a ser validada
	 * @param messageKey Chave da mensagem
	 * @param params Parametros da mensagem
	 * @return true se a expressao for verdadeira
	 */
	public boolean verdadeiro(boolean expressao, String messageKey, Object... params) {
		if (expressao) {
			return true;
		}
		adicionarErro(messageKey, params);
		return false;
	}

	/**
	 * Valida se uma determinada expressao e falsa.
	 * 
	 * @param expressao Expressao a ser validada
	 * @param messageKey Chave da mensagem
	 * @param params Parametros da mensagem
	 * @return true se a expressao for falsa
	 */
	public boolean falso(boolean expressao, String messageKey, Object... params) {
		return verdadeiro(!expressao, messageKey, params);
	}
	
	public void validar() {
		if (possuiErro()) {
			throw new BusinessException(getMensagens());
		}
	}

}