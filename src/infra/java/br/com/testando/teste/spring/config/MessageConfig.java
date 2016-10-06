package br.com.testando.teste.spring.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MessageConfig {

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasenames("messages/message");
		return resourceBundleMessageSource;
	}

	@Bean
	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(new Locale("pt", "BR"));
		return sessionLocaleResolver;
	}

}