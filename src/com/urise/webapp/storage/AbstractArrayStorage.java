package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
	static final int STORAGE_LIMIT = 10_000;
	Resume[] storage = new Resume[STORAGE_LIMIT];
	int size = 0;

	public void clear() {
		Arrays.fill(storage, 0, size, null);
		size = 0;
	}

	public void update(Resume resume) {
		int idx = getIndex(resume.getUuid());
		if (idx < 0) {
			throw new NotExistStorageException(resume.getUuid());
		}
		storage[idx] = resume;
	}

	public void save(Resume resume) {
		int idx = getIndex(resume.getUuid());

		if (size >= storage.length) {
			throw new StorageException("indexStorage is overflow !!!", resume.getUuid());
		} else if (idx < 0) {
			insert(resume, idx);
			size++;
		} else {
			throw new ExistStorageException(resume.getUuid());
		}
	}

	public Resume get(String uuid) {
		int idx = getIndex(uuid);
		if (idx < 0) {
			throw new NotExistStorageException(uuid);
		}
		return storage[idx];
	}

	public void delete(String uuid) {
		int idx = getIndex(uuid);
		if (idx < 0) {
			throw new NotExistStorageException(uuid);
		} else {
			remove(idx);
			storage[size - 1] = null;
			size--;
		}
	}

	public Resume[] getAll() {
		return Arrays.copyOfRange(storage, 0, size);
	}

	public int size() {
		return size;
	}

	protected abstract void insert(Resume resume, int idx);

	protected abstract void remove(int idx);

	protected abstract int getIndex(String uuid);
}
