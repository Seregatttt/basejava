package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
	
	protected void insert(Resume resume, Integer idx) {
		storage[size] = resume;
	}
	
	protected void remove(Integer idx) {
		storage[(int) idx] = storage[size - 1];
	}
	
	protected Integer getSearchKey(String searchKey) {
		for (int i = 0; i < size; i++) {
			if (searchKey.equals(storage[i].getUuid())) {
				return i;
			}
		}
		return -1;
	}
}
