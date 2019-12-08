package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

	protected void insert(Resume resume, Object idx) {
		storage[size] = resume;
	}

	protected void remove(Object idx) {
		storage[(int) idx] = storage[size - 1];
	}

	protected Object getSearchKey(String uuid) {
		for (int i = 0; i < size; i++) {
			if (uuid.equals(storage[i].getUuid())) {
				return i;
			}
		}
		return -1;
	}
}
