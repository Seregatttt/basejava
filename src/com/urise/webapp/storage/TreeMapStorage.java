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
	protected void doUpdate(Resume resume, Object idx) {
		storage.replace(resume.getUuid(), resume);
	}

	@Override
	protected void doSave(Resume resume, Object idx) {
		storage.put(resume.getUuid(), resume);
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
	public List<Resume> doGetAllSorted() {
		return new ArrayList<>(storage.values());
	}

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	protected Resume getSearchKey(String searchKey) {
		return storage.get(searchKey);
	}

	@Override
	protected boolean isExist(Object resume) {
		return resume != null;
	}


}
