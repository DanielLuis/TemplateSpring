package br.com.testando.teste.support;

import static org.junit.Assert.fail;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.testando.teste.support.BusinessException;
import br.com.testando.teste.support.Mensagem;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/spring/AppTestContext.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public abstract class IntegrationTest {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	protected void assertExceptionTypeWithMessage(Class<? extends Throwable> exceptionType, String mensagem, Object... params) {
		exceptionRule.expect(exceptionType);
		exceptionRule.expect(verifyMessage(mensagem, params));
	}

	protected BaseMatcher<? extends Throwable> verifyMessage(final String codigoMensagem, final Object... params) {
		return new BaseMatcher<Throwable>() {

			@Override
			public boolean matches(Object item) {
				if (item instanceof BusinessException) {
					BusinessException e = (BusinessException) item;
					return hasMessage(e, codigoMensagem, params);
				} else {
					Throwable t = (Throwable) item;
					return codigoMensagem.equals(t.getMessage());
				}
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Verificador de mensagens de exceção");
			}

		};
	}

	protected void assertHasMessage(BusinessException e, String errorKey, Object... params) {
		if (hasMessage(e, errorKey, params))
			return;
		fail("Mensagem: [" + errorKey + "] nao encontrada com os parametros: [" + descricaoParametros(params) + "]\nMensagens encontradas: " + e.getMensagens());
	}

	private boolean hasMessage(BusinessException e, String errorKey, Object... params) {
		for (Mensagem mensagem : e.getMensagens()) {
			if (mensagem.getCodigo().equals(errorKey)) {
				List<Object> messageParams = Lists.newArrayList(mensagem.getParametros());
				if (messageParams.size() == 0 && params.length == 0) {
					return true;
				} else {
					for (Object param : params) {
						if (messageParams.contains(param)) {
							return true;
						}
					}
				}
			}
		}
		return false;
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