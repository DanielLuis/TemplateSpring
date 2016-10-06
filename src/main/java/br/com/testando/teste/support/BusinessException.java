package br.com.testando.teste.support;

import java.util.List;

import com.google.common.collect.Lists;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -5860326598976776329L;

	private List<Mensagem> mensagens;

	public BusinessException(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public BusinessException(Mensagem mensagem) {
		this.mensagens = Lists.newArrayList(mensagem);
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	@Override
	public String toString() {
		if (mensagens == null) {
			return "";
		}
		return mensagens.toString();
	}

}