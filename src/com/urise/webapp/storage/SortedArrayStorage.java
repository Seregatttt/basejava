package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

	@Override
	protected void insert(Resume resume, int idx) {
		int insIdx = -idx - 1;
		System.arraycopy(storage, insIdx, storage, insIdx + 1, size - insIdx);
		storage[insIdx] = resume;
	}

	@Override
	protected void remove(int idx) {
		int deleteIdx = idx + 1;
		System.arraycopy(storage, deleteIdx, storage, deleteIdx - 1, size - deleteIdx);
	}

	@Override
	protected int getIndex(String uuid) {
		Resume searchKey = new Resume(uuid);
		//searchKey.setUuid(uuid);
		return Arrays.binarySearch(storage, 0, size, searchKey);
	}
}
