package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;

public class ListStorage extends AbstractStorage {
	private Collection storage = new ArrayList();


	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public void update(Resume resume) {
		int idx = ((ArrayList) storage).indexOf(resume);
		Object ob = ((ArrayList) storage).set(idx, resume);
	}

	@Override
	public void save(Resume resume) {
		storage.add(resume);
	}

	@Override
	public Resume get(String uuid) {
		Resume resume = new Resume(uuid);
		int idx = ((ArrayList) storage).indexOf(resume);
		return (Resume) ((ArrayList) storage).get(idx);
	}

	@Override
	public void delete(String uuid) {
		Resume resume = new Resume(uuid);
		int idx = ((ArrayList) storage).indexOf(resume);
		Object ob = ((ArrayList) storage).remove(idx);

	}

	@Override
	public Resume[] getAll() {
		//help my please.
		// return (Object[]) storage.toArray(); ok ???
		return null;
	}

	@Override
	public int size() {
		return storage.size();
	}
}
