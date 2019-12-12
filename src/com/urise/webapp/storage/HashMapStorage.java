package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HashMapStorage extends AbstractStorage {
	HashMap<String, Resume> storage = new HashMap<>();

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public Resume[] getAll() {
		return new Resume[0];
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
	protected void doUpdate(Resume resume, Object searchKey) {
		storage.replace(resume.getUuid(), resume);
	}

	@Override
	protected void doSave(Resume resume, Object searchKey) {
		storage.put((String) searchKey, resume);
	}

	@Override
	protected Resume doGet(Object searchKey) {
		return storage.get(searchKey);
	}

	@Override
	protected void doDelete(Object searchKey) {
		storage.remove(searchKey);
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
