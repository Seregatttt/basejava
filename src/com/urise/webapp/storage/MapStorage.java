package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
	private TreeMap<String, Resume> storage = new TreeMap<>();

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	protected void doUpdate(Resume resume, int idx) {
		storage.replace(resume.getUuid(), resume);
	}

	@Override
	protected void doSave(Resume resume, int idx) {
		storage.put(resume.getUuid(), resume);
	}

	@Override
	protected Resume doGet(int idx, String uuid) {
		return storage.get(uuid);
	}

	@Override
	protected void doDelete(int idx, String uuid) {
		storage.remove(uuid);
	}

	@Override
	public Resume[] getAll() {
		return storage.values().toArray(new Resume[0]);
	}

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	protected int getIndex(String uuid) {
		String[] mapKeys = new String[storage.size()];
		int pos = 0;
		for (Object key : storage.keySet()) {
			mapKeys[pos++] = (String) key;
		}
		int idx = Arrays.binarySearch(mapKeys, uuid);
		return idx;
	}
}
