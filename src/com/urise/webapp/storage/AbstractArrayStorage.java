package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
	static final int STORAGE_LIMIT = 10_000;
	Resume[] storage = new Resume[STORAGE_LIMIT];
	int size = 0;
	
	public void clear() {
		Arrays.fill(storage, 0, size, null);
		size = 0;
	}
	
	public void doUpdate(Resume resume, Integer idx) {
		storage[idx] = resume;
	}
	
	public void doSave(Resume resume, Integer idx) {
		if (size >= storage.length) {
			throw new StorageException("indexStorage is overflow !!!", resume.getUuid());
		} else {
			insert(resume, idx);
			size++;
		}
	}
	
	public Resume doGet(Integer idx) {
		return storage[idx];
	}
	
	public void doDelete(Integer idx) {
		remove(idx);
		storage[size - 1] = null;
		size--;
	}
	
	public List<Resume> doCopyAll() {
		return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
	}
	
	public int size() {
		return size;
	}
	
	protected abstract void insert(Resume resume, Integer idx);
	
	protected abstract void remove(Integer idx);
	
	protected boolean isExist(Integer searchKey) {
		return searchKey >= 0;
	}
	
	protected abstract Integer getSearchKey(String uuid);
}
