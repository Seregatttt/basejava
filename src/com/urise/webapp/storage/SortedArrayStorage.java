package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {

	private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
		@Override
		public int compare(Resume o1, Resume o2) {
			return (o1.getUuid()).compareTo(o2.getUuid());
		}
	};

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
	protected Object getSearchKey(String uuid) {
		Resume searchKey = new Resume(uuid, "");
		return (int) Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
	}

	@Override
	public List<Resume> doGetAllSorted() {
		List<Resume> list = Arrays.asList(Arrays.copyOfRange(storage, 0, size));
		return list;
	}
}
