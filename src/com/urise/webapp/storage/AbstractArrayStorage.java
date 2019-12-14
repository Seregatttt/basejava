package com.urise.webapp.storage;

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

	public void doUpdate(Resume resume, Object idx) {
		storage[(int) idx] = resume;
	}

	public void doSave(Resume resume, Object idx) {
		if (size >= storage.length) {
			throw new StorageException("indexStorage is overflow !!!", resume.getUuid());
		} else {
			insert(resume, idx);
			size++;
		}
	}

	public Resume doGet(Object idx) {
		return storage[(int) idx];
	}

	public void doDelete(Object idx) {
		remove(idx);
		storage[size - 1] = null;
		size--;
	}
	// please , i save this fo remember
	//public Resume[] getAll() {return Arrays.copyOfRange(storage,0,size);}

	public int size() {
		return size;
	}

	protected abstract void insert(Resume resume, Object idx);

	protected abstract void remove(Object idx);

	protected boolean isExist(Object searchKey) {
		return (int) searchKey >= 0;
	}

	protected abstract Integer getSearchKey(String uuid);
}
