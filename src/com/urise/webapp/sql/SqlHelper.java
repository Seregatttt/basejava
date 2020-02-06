package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
	private final ConnectionFactory connectionFactory;

	public SqlHelper(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public void executeSql(String sql) {
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

	public <T> T transactionalExecute(SqlTransaction<T> executor) {
		try (Connection conn = connectionFactory.getConnection()) {
			try {
				conn.setAutoCommit(false);
				T res = executor.execute(conn);
				conn.commit();
				return res;
			} catch (SQLException e) {
				conn.rollback();
				throw ExceptionUtil.convertException(e,"");
			}
		} catch (SQLException e) {
			throw new StorageException(e);
		}
	}
}

