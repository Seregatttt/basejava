package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
	private final ConnectionFactory connectionFactory;

	public SqlHelper(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void execute(String sql) {
		executeSql(sql, null, PreparedStatement::execute);
	}

	public <T> T executeSql(String sql, String uuid, SqlExecutor<T> action) {
		try (Connection conn = connectionFactory.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			return action.executeSql(ps);
		} catch (SQLException e) {
			throw ExceptionUtil.convertException(e, uuid);
		}
	}
}

