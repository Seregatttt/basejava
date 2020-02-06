package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<T> {
	T executeSql(PreparedStatement st) throws SQLException;
}
