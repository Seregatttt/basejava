package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

	protected void goSave(Resume resume, int idx) {
		storage[size] = resume;
	}

	protected void goDelete(int idx) {
		storage[idx] = storage[size - 1];
		storage[size - 1] = null;
	}

	protected int getIndex(String uuid) {
		for (int i = 0; i < size; i++) {
			if (uuid.equals(storage[i].getUuid())) {
				return i;
			}
		}
		return -1;
	}
}
