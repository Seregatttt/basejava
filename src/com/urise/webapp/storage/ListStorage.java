package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
	private List<Resume> storage = new ArrayList<>();

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public void doUpdate(Resume resume, Object idx) {
		storage.set((int) idx, resume);
	}

	@Override
	protected void doSave(Resume resume, Object idx) {
		storage.add(resume);
	}

	@Override
	public Resume doGet(Object idx) {
		return storage.get((int) idx);
	}

	@Override
	public void doDelete(Object idx) {
		storage.remove((int) idx);
	}

	@Override
	public Resume[] getAll() {
		return storage.toArray(new Resume[storage.size()]);
	}

	@Override
	public List<Resume> doGetAllSorted() {
		return storage;
	}

	@Override
	public int size() {
		return storage.size();
	}

	protected Object getSearchKey(String searchKey) {
		return (int) storage.lastIndexOf(new Resume(searchKey, ""));
	}

	@Override
	protected boolean isExist(Object searchKey) {
		return (int) searchKey >= 0;
	}
}
