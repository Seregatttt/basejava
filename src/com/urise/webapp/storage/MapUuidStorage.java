package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
	Map<String, Resume> storage = new HashMap<>();

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public List<Resume> doCopyAll() {
		return new ArrayList<>(storage.values());
	}

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	protected void doUpdate(Resume resume, String searchKey) {
		storage.replace(searchKey, resume);
	}

	@Override
	protected void doSave(Resume resume, String searchKey) {
		storage.put(searchKey, resume);
	}

	@Override
	protected Resume doGet(String searchKey) {
		return storage.get(searchKey);
	}

	@Override
	protected void doDelete(String searchKey) {
		storage.remove(searchKey);
	}

	@Override
	protected String getSearchKey(String searchKey) {
		return searchKey;
	}

	@Override
	protected boolean isExist(String searchKey) {
		return storage.containsKey(searchKey);
	}
}
