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
	private Resume[] storage = new Resume[10000];
	private int indexStorage;

	public void clear() {
		Arrays.fill(storage, null);
		indexStorage = 0;
	}

	public void save(Resume r) {
		if (indexStorage + 1 >= 10000) {
			System.out.println("indexStorage is overflow !!!");
			return;
		}
		if (checkResume(r.toString())) {
			System.out.println("com.urise.webapp.model.Resume  already exist !!!");
			return;
		}
		storage[indexStorage] = r;
		indexStorage++;
	}

	public Resume get(String uuid) {
		if (!checkResume(uuid)) {
			System.out.println("com.urise.webapp.model.Resume not found !!!");
			return null;
		}
		for (int i = 0; i < indexStorage; i++) {
			if (storage[i].toString().equals(uuid)) {
				return storage[i];
			}
		}
		return null;
	}

	public void delete(String uuid) {
		if (!checkResume(uuid)) {
			System.out.println("Resume not found !!!");
			return;
		}
		for (int i = 0; i < indexStorage; i++) {
			if (storage[i].toString().equals(uuid)) {
				storage[i].setUuid(storage[indexStorage - 1].getUuid());
				storage[indexStorage - 1].setUuid(null);
				indexStorage--;
			}
		}
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

	public void list() {
		for (int i = 0; i < indexStorage; i++) {
			System.out.println(storage[i].toString());
		}
	}

	public boolean checkResume(String uuid) {
		for (int i = 0; i < indexStorage; i++) {
			if (storage[i].toString().equals(uuid)) {
				return true;
			}
		}
		return false;
	}

	public void update(Resume r) {
		System.out.println("Input com.urise.webapp.model.Resume for replace:");
		Scanner scan = new Scanner(System.in);
		String value = scan.next();
		if (!checkResume(value)) {
			System.out.println("com.urise.webapp.model.Resume for replace not found !!!");
			return;
		}
		for (int i = 0; i < indexStorage; i++) {
			if (storage[i].toString().equals(value)) {
				storage[i].setUuid(r.toString());
			}
		}
	}
}
