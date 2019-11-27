package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
	static final int STORAGE_LIMIT = 10_000;
	Resume[] storage = new Resume[STORAGE_LIMIT];
	int size = 0;

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
		} else if (idx < 0) {
			insert(resume, idx);
			size++;
		} else {
			System.out.println("Resume " + resume.getUuid() + " already exist !!!");
		}
	}

	public Resume get(String uuid) {
		int idx = getIndex(uuid);
		if (idx < 0) {
			System.out.println("Resume " + uuid + " not found !!!");
			return null;
		}
		return storage[idx];
	}

	public void delete(String uuid) {
		int idx = getIndex(uuid);
		if (idx < 0) {
			System.out.println("Resume " + uuid + " not  found !!!");
		} else {
			remove(idx);
			storage[size - 1] = null;
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

	protected abstract void insert(Resume resume, int idx);

	protected abstract void remove(int idx);
}
