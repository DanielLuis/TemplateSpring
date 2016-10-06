package br.com.testando.teste.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum SpringLoader {

	INSTANCE("classpath*:META-INF/spring/AppTestContext.xml");

	private String configLocations;
	private ClassPathXmlApplicationContext context;

	private SpringLoader(String configLocations) {
		this.configLocations = configLocations;
	}

	protected ApplicationContext getContext() {
		if (context == null) {
			context = createApplicationContext();
			context.registerShutdownHook();
		}
		return context;
	}

	private ClassPathXmlApplicationContext createApplicationContext() {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(configLocations);
		return classPathXmlApplicationContext;
	}

	public void autowire(Object bean) {
		getContext().getAutowireCapableBeanFactory().autowireBean(bean);
	}

	public <T> T getBean(Class<T> beanClass) {
		return getContext().getBean(beanClass);
	}

}