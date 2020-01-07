package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
	private List<Resume> storage = new ArrayList<>();
	
	@Override
	public void clear() {
		storage.clear();
	}
	
	@Override
	public void doUpdate(Resume resume, Integer searchKey) {
		storage.set(searchKey, resume);
	}
	
	@Override
	protected void doSave(Resume resume, Integer searchKey) {
		storage.add(resume);
	}
	
	@Override
	public Resume doGet(Integer searchKey) {
		return storage.get(searchKey);
	}
	
	@Override
	public void doDelete(Integer searchKey) {
		storage.remove(searchKey.intValue());
	}
	
	@Override
	public List<Resume> doCopyAll() {
		return new ArrayList<>(storage);
	}
	
	@Override
	public int size() {
		return storage.size();
	}
	
	protected Integer getSearchKey(String searchKey) {
		return storage.lastIndexOf(new Resume(searchKey, ""));
	}
	
	@Override
	protected boolean isExist(Integer searchKey) {
		return searchKey >= 0;
	}
}
