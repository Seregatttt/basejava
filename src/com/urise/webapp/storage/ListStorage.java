package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
	ArrayList storage = new ArrayList();

	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public void update(Resume resume) {
		storage.set(0,resume);
	}

	@Override
	public void save(Resume resume) {
		storage.add(resume);
	}

	@Override
	public Resume get(String uuid) {
		Resume resume = new Resume(uuid);
		return (Resume) storage.get(storage.indexOf(resume));
	}

	@Override
	public void delete(String uuid) {
		Resume resume = new Resume(uuid);
		storage.remove(storage.indexOf(resume));
	}

	@Override
	public Resume[] getAll() {
		return null;
	}

	@Override
	public int size() {
		return storage.size();
	}

	@Override
	protected int getIndex(String uuid) {
		Resume resume = new Resume(uuid);
		return storage.indexOf(resume);
	}

	@Override
	protected void remove(int idx) {
		storage.remove(idx);
	}
}
