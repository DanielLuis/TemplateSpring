package br.com.testando.teste.support;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.convert.support.DefaultConversionService;

import com.google.common.base.Preconditions;

public class PropertyManager {

	private Properties properties;
	private DefaultConversionService defaultConversionService = new DefaultConversionService();
	private String separator = ".";

	public PropertyManager(Properties properties) {
		this.properties = properties;
		Preconditions.checkNotNull(properties);
	}

	public PropertyManager(Properties properties, String sepator) {
		this(properties);
		this.separator = sepator;
	}

	public Properties getSection(String sectionName) {
		return getSection(sectionName, true);
	}

	public Properties getSection(String sectionName, boolean removeSectionName) {
		String prefix = separator == null ? sectionName : sectionName + separator;
		int prefixLength = prefix.length();
		Properties newProperties = new Properties();
		Set<Entry<Object, Object>> entrySet = this.properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			String key = (String) entry.getKey();
			if (key.startsWith(prefix) && key.length() > prefixLength) {
				String newKey = removeSectionName ? key.substring(prefixLength) : key;
				newProperties.setProperty(newKey, (String) entry.getValue());
			}
		}
		return newProperties;
	}

	public <T> T getProperty(String propertyName, Class<T> returnType) {
		return defaultConversionService.convert(this.properties.get(propertyName), returnType);
	}

	public Properties getSections(boolean removeSectionName, String... sections) {
		Properties properties = new Properties();
		for (String section : sections) {
			properties.putAll(getSection(section, removeSectionName));
		}
		return properties;
	}

}