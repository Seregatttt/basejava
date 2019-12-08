package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

	public void update(Resume resume) {
		Object searchKey = getExistSearchKey(resume.getUuid());
		doUpdate(resume, searchKey);
	}

	public void save(Resume resume) {
		Object searchKey = getNotExistSearchKey(resume.getUuid());
		doSave(resume, searchKey);
	}

	public Resume get(String uuid) {
		Object searchKey = getExistSearchKey(uuid);
		return doGet(searchKey, uuid);
	}

	public void delete(String uuid) {
		Object searchKey = getExistSearchKey(uuid);
		doDelete(searchKey, uuid);
	}

	private Object getExistSearchKey(String uuid) {
		Object searchKey = getSearchKey(uuid);
		if (existKey(searchKey)) {
			return searchKey;
		} else {
			throw new NotExistStorageException(uuid);
		}
	}

	private Object getNotExistSearchKey(String uuid) {
		Object searchKey = getSearchKey(uuid);
		if (!existKey(searchKey)) {
			return searchKey;
		} else {
			throw new ExistStorageException(uuid);
		}
	}

	protected abstract void doUpdate(Resume resume, Object searchKey);

	protected abstract void doSave(Resume resume, Object searchKey);

	protected abstract Resume doGet(Object searchKey, String uuid);

	protected abstract void doDelete(Object searchKey, String uuid);

	protected abstract Object getSearchKey(String uuid);

	protected abstract boolean existKey(Object searchKey);
}
