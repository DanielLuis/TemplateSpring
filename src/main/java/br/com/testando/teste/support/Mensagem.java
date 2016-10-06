package br.com.testando.teste.support;

import java.io.Serializable;

import br.com.testando.teste.support.Mensagem;
import br.com.testando.teste.support.TipoMensagem;

import com.google.common.collect.Lists;

public class Mensagem implements Serializable {

	private static final long serialVersionUID = -7685410903099866556L;

	private String codigo;
	private Object[] parametros;
	private TipoMensagem tipo;

	public static Mensagem sucesso(String codigo, Object... parametros) {
		return new Mensagem(codigo, TipoMensagem.SUCESSO, parametros);
	}

	public static Mensagem erro(String codigo, Object... parametros) {
		return new Mensagem(codigo, TipoMensagem.ERRO, parametros);
	}

	private Mensagem(String codigo, TipoMensagem tipo, Object... parametros) {
		this.codigo = codigo;
		this.tipo = tipo;
		this.parametros = parametros;
	}

	public String getCodigo() {
		return codigo;
	}

	public Object[] getParametros() {
		return parametros;
	}

	public TipoMensagem getTipo() {
		return tipo;
	}

	@Override
	public String toString() {
		if (getParametros() != null && getParametros().length > 0) {
			return codigo + " com os par�metros: " + Lists.newArrayList(getParametros());
		}
		return codigo;
	}

}