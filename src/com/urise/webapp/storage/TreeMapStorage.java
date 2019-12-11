package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
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
	protected Resume doGet(Object idx, String uuid) {
		return storage.get(uuid);
	}

	@Override
	protected void doDelete(Object idx, String uuid) {
		storage.remove(uuid);
	}

	@Override
	public Resume[] getAll() {
		return storage.values().toArray(new Resume[0]);
	}

	@Override
	public List<Resume> getAllSorted() {
		//List<Resume> list = Arrays.asList(storage.values().toArray(new Resume[0]));
		List<Resume> list = new ArrayList<Resume>(storage.values());
		Collections.sort(list);
		return list;
	}

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	protected Object getSearchKey(String searchKey) {
		return searchKey;
	}

	@Override
	protected boolean isExistKey(Object searchKey) {
		return storage.containsKey(searchKey);
	}


}
