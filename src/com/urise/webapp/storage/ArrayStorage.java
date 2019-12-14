package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

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

	protected Integer getSearchKey(String searchKey) {
		for (int i = 0; i < size; i++) {
			if (searchKey.equals(storage[i].getUuid())) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public List<Resume> doGetAllSorted() {
		return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
	}
}
