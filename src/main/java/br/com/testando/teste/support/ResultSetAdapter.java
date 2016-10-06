package br.com.testando.teste.support;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Auxilia ao obter os valores primitivos do {@link ResultSet}, fazendo verificacao de nulos.
 * 
 * @author WDEV
 */
public class ResultSetAdapter {

	private ResultSet resultSet;

	/**
	 * Adapter para facilitar o uso do {@link ResultSet}
	 * 
	 * @param resultSet ResultSet que sera chamado em todas operacoes do adapter.
	 */
	public ResultSetAdapter(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	/**
	 * Retorna o valor inteiro ou nulo da coluna informada.
	 * 
	 * @param columnName Nome da coluna
	 * @return Valor inteiro ou nulo.
	 * @throws SQLException Erros {@link SQLException} durante o uso do ResultSet
	 */
	public Integer getInteger(String columnName) throws SQLException {
		Integer value = resultSet.getInt(columnName);
		if (resultSet.wasNull()) {
			return null;
		}
		return value;
	}

	/**
	 * Retorna o valor double ou nulo da coluna informada.
	 * 
	 * @param columnName Nome da coluna
	 * @return Valor double ou nulo.
	 * @throws SQLException Erros {@link SQLException} durante o uso do ResultSet
	 */
	public Double getDouble(String columnName) throws SQLException {
		Double value = resultSet.getDouble(columnName);
		if (resultSet.wasNull()) {
			return null;
		}
		return value;
	}

	/**
	 * Retorna o valor long ou nulo da coluna informada.
	 * 
	 * @param columnName Nome da coluna
	 * @return Valor long ou nulo.
	 * @throws SQLException Erros {@link SQLException} durante o uso do ResultSet
	 */
	public Long getLong(String columnName) throws SQLException {
		Long value = resultSet.getLong(columnName);
		if (resultSet.wasNull()) {
			return null;
		}
		return value;
	}

	/**
	 * Retorna a data/hora ou nulo da coluna informada.
	 * 
	 * @param columnName Nome da coluna
	 * @return {@link LocalDate} ou nulo.
	 * @throws SQLException Erros {@link SQLException} durante o uso do ResultSet
	 */
	public LocalDate getLocalDate(String columnName) throws SQLException {
		LocalDate value = new LocalDate(resultSet.getTimestamp(columnName));
		if (resultSet.wasNull()) {
			return null;
		}
		return value;
	}

	/**
	 * Retorna a data/hora ou nulo da coluna informada.
	 * 
	 * @param columnName Nome da coluna
	 * @return DateTime ou nulo.
	 * @throws SQLException Erros {@link SQLException} durante o uso do ResultSet
	 */
	public DateTime getDateTime(String columnName) throws SQLException {
		DateTime value = new DateTime(resultSet.getTimestamp(columnName));
		if (resultSet.wasNull()) {
			return null;
		}
		return value;
	}

	/**
	 * Retorna o BigDecimal ou nulo da coluna informada.
	 * 
	 * @param columnName Nome da coluna
	 * @return BigDecimal ou nulo.
	 * @throws SQLException Erros {@link SQLException} durante o uso do ResultSet
	 */
	public BigDecimal getBigDecimal(String columnName) throws SQLException {
		BigDecimal value = resultSet.getBigDecimal(columnName);
		if (resultSet.wasNull()) {
			return null;
		}
		return value;
	}

	/**
	 * Retorna a string ou nulo da coluna informada. <b>Remove caracteres brancos no inicio no final da string {@link String#trim()}. </b>
	 * 
	 * @param columnName Nome da coluna
	 * @return String ou nulo.
	 * @throws SQLException Erros {@link SQLException} durante o uso do ResultSet
	 */
	public String getString(String columnName) throws SQLException {
		String value = resultSet.getString(columnName);
		if (resultSet.wasNull()) {
			return null;
		}
		return value.trim();
	}

	/**
	 * Retorna a string ou nulo da coluna informada. Nao altera a string original retornada pelo {@link ResultSet#getString(String)}
	 * 
	 * @param columnName Nome da coluna
	 * @return String ou nulo.
	 * @throws SQLException Erros {@link SQLException} durante o uso do ResultSet
	 */
	public String getNotTrimmedString(String columnName) throws SQLException {
		String value = resultSet.getString(columnName);
		if (resultSet.wasNull()) {
			return null;
		}
		return value;
	}

}