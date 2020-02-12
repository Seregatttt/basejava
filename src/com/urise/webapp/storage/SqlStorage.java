package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
	private SqlHelper sqlHelper;

	public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
	}

	@Override
	public void clear() {
		sqlHelper.executeSql("DELETE FROM resume ");
	}

	@Override
	public Resume get(String uuid) {
		return sqlHelper.executeSql("" +
						"    SELECT * FROM resume r " +
						" LEFT JOIN contact c " +
						"        ON r.uuid = c.resume_uuid " +
						" LEFT JOIN section s " +
						"        ON r.uuid = s.resume_uuid " +
						"     WHERE r.uuid =?", uuid,
				ps -> {
					ps.setString(1, uuid);

					ResultSet rs = ps.executeQuery();
					if (!rs.next()) {
						throw new NotExistStorageException(uuid);
					}
					Resume r = new Resume(uuid, rs.getString("full_name"));

					do {
						addContact(rs, r);
						addSection(rs, r);
					} while (rs.next());


					return r;
				});
	}

	@Override
	public void update(Resume r) {
		sqlHelper.transactionalExecute(conn -> {
			try (PreparedStatement ps = conn.prepareStatement("UPDATE  resume  SET full_name =? WHERE uuid =?")) {
				ps.setString(1, r.getFullName());
				ps.setString(2, r.getUuid());
				ps.execute();
				if (ps.executeUpdate() == 0) {
					throw new NotExistStorageException(r.getUuid());
				}
			}
			deleteContacts(r, conn);
			insertContacts(r, conn);

			deleteSection(r, conn);
			insertSections(r, conn);

			return null;
		});
	}

	@Override
	public void save(Resume r) {
		sqlHelper.transactionalExecute(conn -> {
					try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
						ps.setString(1, r.getUuid());
						ps.setString(2, r.getFullName());
						ps.execute();
					}
					insertContacts(r, conn);
					insertSections(r, conn);
					return null;
				}
		);
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
		return sqlHelper.transactionalExecute(conn -> {
					Map<String, Resume> storage = new LinkedHashMap<>();
					try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name,uuid")) {
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							String uuid = rs.getString("uuid");
							String full_name = rs.getString("full_name");
							storage.put(uuid, new Resume(uuid, full_name));
						}
					}
					try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact c ")) {
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							Resume resume = storage.get(rs.getString("resume_uuid"));
							addContact(rs, resume);
							storage.put(rs.getString("resume_uuid"), resume);
						}
					}

					try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s ")) {
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							Resume resume = storage.get(rs.getString("resume_uuid"));
							addSection(rs, resume);
							storage.put(rs.getString("resume_uuid"), resume);
						}
					}
					return new ArrayList<>(storage.values());
				}
		);
	}

	private void addContact(ResultSet rs, Resume r) throws SQLException {
		String value = rs.getString("value");
		if (value == null) {
			return;
		}
		ContactType type = ContactType.valueOf(rs.getString("type"));
		r.addContact(type, value);
	}

	private void addSection(ResultSet rs, Resume r) throws SQLException {
		String value = rs.getString("section_value");
		if (value == null) {
			return;
		}
		SectionType sectionType = SectionType.valueOf(rs.getString("section_type"));
		switch (sectionType) {
			case OBJECTIVE:
			case PERSONAL:
				r.addSection(sectionType, new TextSection(value));
				break;
			case ACHIEVEMENT:
			case QUALIFICATIONS:
				r.addSection(sectionType, new ListSection(Arrays.asList(value.split("\n"))));
				break;
		}
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

	private void deleteContacts(Resume r, Connection conn) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid =?")) {
			ps.setString(1, r.getUuid());
			ps.execute();
		}
	}

	private void deleteSection(Resume r, Connection conn) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid =?")) {
			ps.setString(1, r.getUuid());
			ps.execute();
		}
	}

	private void insertContacts(Resume r, Connection conn) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
			for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
				ps.setString(1, r.getUuid());
				ps.setString(2, e.getKey().name());
				ps.setString(3, e.getValue());
				ps.addBatch();
			}
			ps.executeBatch();
		}
	}

	private void insertSections(Resume r, Connection conn) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement("" +
				"INSERT INTO section (resume_uuid, section_type, section_value)" +
				" VALUES (?,?,?)")) {
			for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
				ps.setString(1, r.getUuid());
				ps.setString(2, e.getKey().name());
				switch (e.getKey()) {
					case OBJECTIVE:
					case PERSONAL:
						ps.setString(3, ((TextSection) e.getValue()).getContent());
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						ps.setString(3, String.join("\n", ((ListSection) e.getValue()).getList()));
						break;
				}
				ps.addBatch();
			}
			ps.executeBatch();
		}
	}
}