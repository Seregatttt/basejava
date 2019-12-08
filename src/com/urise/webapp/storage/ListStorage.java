package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
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
	public Resume doGet(Object idx, String uuid) {
		return storage.get((int) idx);
	}

	@Override
	public void doDelete(Object idx, String uuid) {
		storage.remove((int) idx);
	}

	@Override
	public Resume[] getAll() {
		return storage.toArray(new Resume[storage.size()]);
	}

	@Override
	public int size() {
		return storage.size();
	}

	protected Object getSearchKey(String uuid) {
		return storage.lastIndexOf(new Resume(uuid));
	}

	@Override
	protected boolean existKey(Object searchKey) {
		return (int) searchKey >= 0;
	}
}
