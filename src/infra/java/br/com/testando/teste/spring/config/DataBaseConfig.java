package br.com.testando.teste.spring.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.testando.teste.support.PropertyManager;

@Configuration
@EnableTransactionManagement
public class DataBaseConfig {

	@Bean(destroyMethod = "close")
	public DataSource dataSource(Properties applicationProperties) throws Exception {
		return obterConexaoViaProperties(applicationProperties);
	}
	
	protected DataSource obterConexaoViaJndi(Properties applicationProperties) throws Exception {
		String jndi = applicationProperties.getProperty("datasource.jndi");

		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName(jndi);
		jndiObjectFactoryBean.afterPropertiesSet();

		return (DataSource) jndiObjectFactoryBean.getObject();
	}
	
	protected DataSource obterConexaoViaProperties(Properties applicationProperties) throws Exception {
		Properties datasourceProperties = new PropertyManager(applicationProperties).getSection("datasource");
		DataSource dataSource = new DataSource(DataSourceFactory.parsePoolProperties(datasourceProperties));
		prepararConexao(dataSource, datasourceProperties);
		return dataSource;
	}

	private void prepararConexao(DataSource dataSource, Properties datasourceProperties) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}