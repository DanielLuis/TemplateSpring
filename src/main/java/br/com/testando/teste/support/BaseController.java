package br.com.testando.teste.support;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.WebRequest;

import com.google.common.collect.Lists;

public abstract class BaseController {

	@Autowired(required = false)
	private WebRequest webRequest;

	@Autowired(required = false)
	protected Sessions sessions;

	@Autowired
	private ApplicationContext applicationContext;

	protected void sucesso(String mensagem) {
		adicionarMensagem(Mensagem.sucesso(mensagem));
	}

	protected void erro(BusinessException e) {
		for (Mensagem mensagem : e.getMensagens()) {
			adicionarMensagem(mensagem);
		}
	}

	private void adicionarMensagem(Mensagem mensagem) {
		String msg = applicationContext.getMessage(mensagem.getCodigo(), mensagem.getParametros(), new Locale("pt", "BR"));
		if (TipoMensagem.SUCESSO == mensagem.getTipo()) {
			getMensagensSucesso().add(msg);
		} else {
			getMensagensErro().add(msg);
		}
	}

	private List<String> getMensagensSucesso() {
		return getMensagens("mensagensSucesso");
	}

	private List<String> getMensagensErro() {
		return getMensagens("mensagensErro");
	}

	@SuppressWarnings("unchecked")
	private List<String> getMensagens(String nomeAtributoMensagem) {
		List<String> mensagens = (List<String>) webRequest.getAttribute(nomeAtributoMensagem, WebRequest.SCOPE_REQUEST);

		if (mensagens == null) {
			mensagens = Lists.newArrayList();
			webRequest.setAttribute(nomeAtributoMensagem, mensagens, WebRequest.SCOPE_REQUEST);
		}

		return mensagens;
	}

}