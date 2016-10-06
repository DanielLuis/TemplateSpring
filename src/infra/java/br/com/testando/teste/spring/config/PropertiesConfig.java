package br.com.testando.teste.spring.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class PropertiesConfig {

	private static final Logger LOG = LoggerFactory.getLogger(PropertiesConfig.class);

	@Bean
	public Properties applicationProperties(ResourceLoader resourceLoader) throws Exception {
		Properties applicationProperties = new Properties();

		String ambiente = getAmbiente();
		applicationProperties.setProperty("AMBIENTE", ambiente);

		carregarProperties(applicationProperties, resourceLoader.getResource("classpath:application.properties"));
		carregarProperties(applicationProperties, resourceLoader.getResource("classpath:application." + ambiente + ".properties"));

		return applicationProperties;
	}

	private void carregarProperties(Properties applicationProperties, Resource resource) throws IOException {
		LOG.info("Carregando properties: de '{}'", resource);
		if (resource.exists()) {
			Properties resourceProperties = PropertiesLoaderUtils.loadProperties(resource);
			LOG.info("Propriedades carregadas '{}'", Collections.list(resourceProperties.propertyNames()));
			applicationProperties.putAll(resourceProperties);
		} else {
			LOG.info("Arquivo properties nao encontrado", resource);
		}
	}

	private String getAmbiente() {
		String ambiente = System.getProperty("AMBIENTE");
		if (ambiente == null) {
			ambiente = System.getenv("AMBIENTE");
		}
		if (ambiente == null) {
			ambiente = "DESENVOLVIMENTO";
		}
		return ambiente;
	}

}