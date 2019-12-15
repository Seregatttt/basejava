package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapStorage extends AbstractStorage {
	private Map<String, Resume> storage = new TreeMap<>();

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	protected void doUpdate(Resume r, Object resume) {
		storage.replace(r.getUuid(), r);
	}

	@Override
	protected void doSave(Resume r, Object resume) {
		storage.put(r.getUuid(), r);
	}

	@Override
	protected Resume doGet(Object resume) {
		return (Resume) resume;
	}

	@Override
	protected void doDelete(Object resume) {
		storage.remove(((Resume) resume).getUuid());
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
	protected Resume getSearchKey(String uuid) {
		return storage.get(uuid);
	}

	@Override
	protected boolean isExist(Object resume) {
		return resume != null;
	}


}
