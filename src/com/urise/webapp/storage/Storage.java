package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

	void clear();

	void update(Resume resume);

	void save(Resume resume);

	Resume get(String uuid);

	void delete(String uuid);

	Resume[] getAll();

	List<Resume> getAllSorted();

	int size();
}
