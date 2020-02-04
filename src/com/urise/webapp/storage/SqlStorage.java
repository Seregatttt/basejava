package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
	private SqlHelper sqlHelper;

	public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
		this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
	}

	@Override
	public void clear() {
		sqlHelper.executeSql("DELETE FROM resume", null, ps -> {
			ps.execute();
			return null;
		});
	}

	@Override
	public Resume get(String uuid) {
		Resume r = sqlHelper.executeSql("SELECT * FROM resume r WHERE r.uuid =?", uuid, ps -> {
			ps.setString(1, uuid);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotExistStorageException(uuid);
			}
			return new Resume(uuid, rs.getString("full_name"));
		});
		return r;
	}

	@Override
	public void update(Resume r) {
		sqlHelper.executeSql("UPDATE  resume  SET full_name =? WHERE uuid =?", r.getUuid(), ps -> {
			ps.setString(1, r.getFullName());
			ps.setString(2, r.getUuid());
			int row = ps.executeUpdate();
			if (row == 0) {
				throw new NotExistStorageException(r.getUuid());
			}
			return null;
		});
	}

	@Override
	public void save(Resume r) {
		sqlHelper.executeSql("INSERT INTO resume (uuid, full_name) VALUES (?,?)", r.getUuid(), ps -> {
			ps.setString(1, r.getUuid());
			ps.setString(2, r.getFullName());
			ps.execute();
			return null;
		});
	}

	@Override
	public void delete(String uuid) {
		sqlHelper.executeSql("DELETE  FROM resume r WHERE r.uuid =?", uuid, ps -> {
			ps.setString(1, uuid);
			int row = ps.executeUpdate();
			if (row == 0) {
				throw new NotExistStorageException(uuid);
			}
			return null;
		});
	}

	@Override
	public List<Resume> getAllSorted() {
		String sql = "SELECT uuid uuid, full_name full_name FROM resume r ORDER BY uuid ";
		return sqlHelper.executeSql(sql, null, ps -> {
			ResultSet rs = ps.executeQuery();
			List<Resume> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
			}
			return list;
		});
	}

	@Override
	public int size() {
		String sql = "SELECT count(*) cnt FROM resume ";
		Integer cnt = sqlHelper.executeSql(sql, null, ps -> {
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new StorageException("error is found in size()");
			}
			return rs.getInt(1);
		});
		return cnt;
	}
}