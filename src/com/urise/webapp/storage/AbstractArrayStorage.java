package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
	protected static final int STORAGE_LIMIT = 10_000;
	protected Resume[] storage = new Resume[STORAGE_LIMIT];
	protected int size = 0;

	public void clear() {
		Arrays.fill(storage, 0, size, null);
		size = 0;
	}

	public void update(Resume resume) {
		int idx = getIndex(resume.getUuid());
		if (idx < 0) {
			System.out.println("Resume " + resume.getUuid() + " for update not found !!!");
			return;
		}
		storage[idx] = resume;
	}

	public void save(Resume resume) {
		int idx = getIndex(resume.getUuid());
		System.out.println("idx=" + idx);

		if (size >= storage.length) {
			System.out.println("indexStorage   is overflow !!!");
		} else if (idx >= 0) {
			System.out.println("Resume " + resume.getUuid() + " already exist !!!");
		} else {
			goSave(resume, idx);
			size++;
		}
	}

	public Resume get(String uuid) {
		int idx = getIndex(uuid);
		if (idx >= 0) {
			return storage[idx];
		}
		System.out.println("Resume " + uuid + " not found !!!");
		return null;
	}

	public void delete(String uuid) {
		int idx = getIndex(uuid);
		if (idx < 0) {
			System.out.println("Resume " + uuid + " not  found !!!");
		} else {
			goDelete(idx);
			size--;
		}
	}

	public Resume[] getAll() {
		return Arrays.copyOfRange(storage, 0, size);
	}

	public int size() {
		return size;
	}

	protected abstract int getIndex(String uuid);

	protected abstract void goSave(Resume resume, int idx);

	protected abstract void goDelete(int idx);
}
