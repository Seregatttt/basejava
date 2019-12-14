package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {

	private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(o -> (o.getUuid()));

	@Override
	protected void insert(Resume resume, Object idx) {
		int insIdx = -(int) idx - 1;
		System.arraycopy(storage, insIdx, storage, insIdx + 1, size - insIdx);
		storage[insIdx] = resume;
	}

	@Override
	protected void remove(Object idx) {
		int deleteIdx = (int) idx + 1;
		System.arraycopy(storage, deleteIdx, storage, deleteIdx - 1, size - deleteIdx);
	}

	@Override
	protected Integer getSearchKey(String uuid) {
		Resume searchKey = new Resume(uuid, "");
		return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
	}

	@Override
	public List<Resume> doGetAllSorted() {
		return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
	}
}
