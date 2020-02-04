package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

	public interface Action<T> {
		T executeSql(PreparedStatement ps) throws SQLException;
	}

	public ConnectionFactory connectionFactory;

	public SqlHelper(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public <T> T executeSql(String sql, String uuid, Action<T> action) {
		try (Connection conn = connectionFactory.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			return action.executeSql(ps);
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				throw new ExistStorageException(uuid);
			}
			throw new StorageException(e);
		}
	}
}

