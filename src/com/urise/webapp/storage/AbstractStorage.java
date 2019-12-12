package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

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
		return doGet(searchKey);
	}

	public void delete(String uuid) {
		Object searchKey = getExistSearchKey(uuid);
		doDelete(searchKey);
	}

	private Object getExistSearchKey(String uuid) {
		Object searchKey = getSearchKey(uuid);
		if (isExist(searchKey)) {
			return searchKey;
		} else {
			throw new NotExistStorageException(uuid);
		}
	}

	private Object getNotExistSearchKey(String uuid) {
		Object searchKey = getSearchKey(uuid);
		if (!isExist(searchKey)) {
			return searchKey;
		} else {
			throw new ExistStorageException(uuid);
		}
	}

	public List<Resume> getAllSorted() {
		List<Resume> list = doGetAllSorted();
		Collections.sort(list);
		return list;
	}

	protected abstract void doUpdate(Resume resume, Object searchKey);

	protected abstract void doSave(Resume resume, Object searchKey);

	protected abstract Resume doGet(Object searchKey);

	protected abstract void doDelete(Object searchKey);

	protected abstract Object getSearchKey(String searchKey);

	protected abstract boolean isExist(Object searchKey);

	protected abstract List<Resume> doGetAllSorted();
}
