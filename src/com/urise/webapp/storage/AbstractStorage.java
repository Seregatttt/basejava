package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

	public void clear() {
		doClear();
	}

	public void update(Resume resume) {
		int idx = getIndex(resume.getUuid());
		if (idx < 0) {
			throw new NotExistStorageException(resume.getUuid());
		} else {
			doUpdate(resume, idx);
		}
	}

	public void save(Resume resume) {
		int idx = getIndex(resume.getUuid());
		if (idx < 0) {
			doSave(resume, idx);
		} else {
			throw new ExistStorageException(resume.getUuid());
		}
	}

	public Resume get(String uuid) {
		int idx = getIndex(uuid);
		if (idx < 0) {
			throw new NotExistStorageException(uuid);
		}
		return doGet(idx);
	}

	public void delete(String uuid) {
		int idx = getIndex(uuid);
		if (idx < 0) {
			throw new NotExistStorageException(uuid);
		} else {
			doDelete(idx);
		}
	}

	protected abstract void doClear();

	protected abstract void doUpdate(Resume resume, int idx);

	protected abstract void doSave(Resume resume, int idx);

	public abstract Resume doGet(int idx);

	public abstract void doDelete(int idx);

	protected abstract void insert(Resume resume, int idx);

	protected abstract void remove(int idx);

	protected abstract int getIndex(String uuid);
}
