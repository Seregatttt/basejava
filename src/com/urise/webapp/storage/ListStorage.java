package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
	private List<Resume> storage = new ArrayList<>();

	public void print() {
		System.out.println(storage);
	}

	@Override
	public void doClear() {
		storage.clear();
	}

	@Override
	public void doUpdate(Resume resume, int idx) {
		Object ob = storage.set(idx, resume);
	}

	@Override
	protected void doSave(Resume resume, int idx) {
		storage.add(resume);
	}

	@Override
	public Resume doGet(int idx) {
		return (Resume) storage.get(idx);
	}

	@Override
	public void doDelete(int idx) {
		Object ob = storage.remove(idx);
	}

	@Override
	public Resume[] getAll() {
		return storage.toArray(new Resume[storage.size()]);
	}

	@Override
	public int size() {
		return storage.size();
	}

	protected void insert(Resume resume, int idx) {
		storage.add(resume);
	}

	protected void remove(int idx) {
	}

	protected int getIndex(String uuid) {
		return getIndex(new Resume(uuid));
	}

	protected int getIndex(Resume resume) {
		return storage.lastIndexOf(resume);
	}
}
