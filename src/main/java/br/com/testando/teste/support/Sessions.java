package br.com.testando.teste.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@Component
public class Sessions {

	private static final int REQUEST_ATTRIBUTE_SESSION_SCOPE = RequestAttributes.SCOPE_SESSION;

	@Autowired(required = false)
	protected WebRequest webRequest;

	@SuppressWarnings("unchecked")
	public <T> T getOrCreate(String key, T value) {
		if (webRequest.getAttribute(key, REQUEST_ATTRIBUTE_SESSION_SCOPE) == null) {
			set(key, value);
		}
		return (T) webRequest.getAttribute(key, REQUEST_ATTRIBUTE_SESSION_SCOPE);
	}

	public <T> T getOrCreate(T value) {
		return getOrCreate(value.getClass().getSimpleName(), value);
	}

	public <T> void set(String key, T value) {
		webRequest.setAttribute(key, value, REQUEST_ATTRIBUTE_SESSION_SCOPE);
	}

	public <T> void set(T value) {
		set(value.getClass().getSimpleName(), value);
	}

	public void remove(String key) {
		webRequest.removeAttribute(key, REQUEST_ATTRIBUTE_SESSION_SCOPE);
	}

	public <T> void remove(Class<T> keyType) {
		remove(keyType.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> type) {
		return (T) webRequest.getAttribute(key, REQUEST_ATTRIBUTE_SESSION_SCOPE);
	}

	public <T> T get(Class<T> type) {
		return get(type.getSimpleName(), type);
	}

}