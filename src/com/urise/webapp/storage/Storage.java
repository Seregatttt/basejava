package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.sql.SQLException;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

	void clear();

	void update(Resume resume);

	void save(Resume resume);

	Resume get(String uuid) throws SQLException;

	void delete(String uuid);

	List<Resume> getAllSorted();

	int size();
}
