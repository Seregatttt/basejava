package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class TreeMapStorage extends AbstractStorage {
	private Map<String, Resume> storage = new TreeMap<>();

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	protected void doUpdate(Resume resume, Object idx) {
		storage.replace(resume.getUuid(), resume);
	}

	@Override
	protected void doSave(Resume resume, Object idx) {
		storage.put(resume.getUuid(), resume);
	}

	@Override
	protected Resume doGet(Object idx) {
		return storage.get(idx);
	}

	@Override
	protected void doDelete(Object idx) {
		storage.remove(idx);
	}

	@Override
	public Resume[] getAll() {
		return storage.values().toArray(new Resume[0]);
	}

	@Override
	public List<Resume> doGetAllSorted() {
		List<Resume> list = new ArrayList<Resume>(storage.values());
		return list;
	}

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	protected Object getSearchKey(String searchKey) {
		return (String) searchKey;
	}

	@Override
	protected boolean isExist(Object searchKey) {
		return storage.containsKey(searchKey);
	}


}
