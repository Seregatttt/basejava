package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Array based storage for Resumes
 * <p>
 * varning comment
 */
public class ArrayStorage {
	private Resume[] storage = new Resume[10_000];
	private int indexStorage;

	public void clear() {
		Arrays.fill(storage, 0, indexStorage, null);
		indexStorage = 0;
	}

	public void save(Resume resume) {
		if (indexStorage >= storage.length) {
			System.out.println("indexStorage is overflow !!!");
			return;
		}
		if (checkResume(resume.getUuid()) >= 0) {
			System.out.println("Resume " + resume.getUuid() + " already exist !!!");
			return;
		}
		storage[indexStorage] = resume;
		indexStorage++;
	}

	public Resume get(String uuid) {
		int idx = checkResume(uuid);
		if (idx >= 0) {
			return storage[idx];
		}
		System.out.println("Resume " + uuid + " not found !!!");
		return null;
	}

	public void delete(String uuid) {
		int idx = checkResume(uuid);
		if (idx < 0) {
			System.out.println("Resume " + uuid + " not found !!!");
			return;
		}
		storage[idx] = storage[indexStorage - 1];
		storage[indexStorage - 1] = null;
		indexStorage--;
	}

	/**
	 * @return array, contains only Resumes in storage (without null)
	 */

	public Resume[] getAll() {
		return Arrays.copyOf(storage, indexStorage);
	}

	public int size() {
		return indexStorage;
	}

	public void update(Resume resume) {
		int idx = checkResume(resume.getUuid());
		if (idx < 0) {
			System.out.println("Resume " + resume.getUuid() + " for update not found !!!");
			return;
		}
		storage[idx] = resume;
	}

	private int checkResume(String uuid) {
		for (int i = 0; i < indexStorage; i++) {
			if (storage[i].getUuid().equals(uuid)) {
				return i;
			}
		}
		return -1;
	}
}
