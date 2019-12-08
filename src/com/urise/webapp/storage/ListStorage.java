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
	public void doUpdate(Resume resume, int idx) {
		storage.set(idx, resume);
	}

	@Override
	protected void doSave(Resume resume, int idx) {
		storage.add(resume);
	}

	@Override
	public Resume doGet(int idx, String uuid) {
		return storage.get(idx);
	}

	@Override
	public void doDelete(int idx, String uuid) {
		storage.remove(idx);
	}

	@Override
	public Resume[] getAll() {
		return storage.toArray(new Resume[storage.size()]);
	}

	@Override
	public int size() {
		return storage.size();
	}

	protected int getIndex(String uuid) {
		return storage.lastIndexOf(new Resume(uuid));
	}
}
