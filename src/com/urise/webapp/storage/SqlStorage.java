package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
	private SqlHelper sqlHelper;

	public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
		this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
	}

	@Override
	public void clear() {
		sqlHelper.executeSql("DELETE FROM resume ", null, ps -> {
			ps.execute();
			return null;
		});
	}

	@Override
	public Resume get(String uuid) {
		return sqlHelper.executeSql("" +
						"    SELECT * FROM resume r " +
						" LEFT JOIN contact c " +
						"        ON r.uuid = c.resume_uuid " +
						"     WHERE r.uuid =?", uuid,
				ps -> {
					ps.setString(1, uuid);
					ResultSet rs = ps.executeQuery();
					if (!rs.next()) {
						throw new NotExistStorageException(uuid);
					}
					Resume r = new Resume(uuid, rs.getString("full_name"));
					do {
						String value = rs.getString("value");
						ContactType type = ContactType.valueOf(rs.getString("type"));
						r.addContact(type, value);
					} while (rs.next());
					return r;
				});
	}

	@Override
	public void update(Resume r) {
		sqlHelper.executeSql("UPDATE  resume  SET full_name =? WHERE uuid =?", r.getUuid(), ps -> {
			ps.setString(1, r.getFullName());
			ps.setString(2, r.getUuid());
			if (ps.executeUpdate() == 0) {
				throw new NotExistStorageException(r.getUuid());
			}
			return null;
		});
	}

	@Override
	public void save(Resume r) {
		sqlHelper.<Void>executeSql("INSERT INTO resume (uuid, full_name) VALUES (?,?)", r.getUuid(), ps -> {
			ps.setString(1, r.getUuid());
			ps.setString(2, r.getFullName());
			ps.execute();
			return null;
		});
		for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
			sqlHelper.<Void>executeSql("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", r.getUuid(),
					ps -> {
						ps.setString(1, r.getUuid());
						ps.setString(2, e.getKey().name());
						ps.setString(3, e.getValue());
						return null;
					});
		}
	}

	@Override
	public void delete(String uuid) {
		sqlHelper.executeSql("DELETE  FROM resume r WHERE r.uuid =?", uuid, ps -> {
			ps.setString(1, uuid);
			if (ps.executeUpdate() == 0) {
				throw new NotExistStorageException(uuid);
			}
			return null;
		});
	}

	@Override
	public List<Resume> getAllSorted() {
		return sqlHelper.executeSql("SELECT * FROM resume r ORDER BY full_name, uuid ", null, ps -> {
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
		return sqlHelper.executeSql("SELECT count(*) cnt FROM resume ", null, ps -> {
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new StorageException("error is found in size()");
			}
			return rs.getInt(1);
		});
	}
}