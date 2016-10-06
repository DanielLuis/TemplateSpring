package br.com.testando.teste.validator;

import static org.junit.Assert.fail;

import java.util.List;

import br.com.testando.teste.support.BusinessException;
import br.com.testando.teste.support.Mensagem;

import com.google.common.collect.Lists;

public abstract class BaseValidatorTest {
	
	protected void assertHasMessage(BusinessException e, String errorKey, Object... params) {
		for (Mensagem message : e.getMensagens()) {
			if (message.getCodigo().equals(errorKey)) {
				List<Object> messageParams = Lists.newArrayList(message.getParametros());
				if (messageParams.size() == 0 && params.length == 0) {
					return;
				} else {
					for (Object param : params) {
						if (messageParams.contains(param)) {
							return;
						}
					}
				}
			}
		}
		fail("Mensagem: [" + errorKey + "] nao encontrada com os parametros: [" + descricaoParametros(params) + "]\nMensagens encontradas: " + e.getMensagens());
	}

	private String descricaoParametros(Object[] params) {
		StringBuilder descricao = new StringBuilder();
		for (int i = 0; i < params.length; i++) {
			if (i > 0) {
				descricao.append(",");
			}
			descricao.append(params[i]);
		}
		return descricao.toString();
	}
}