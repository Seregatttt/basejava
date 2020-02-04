package com.urise;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

import java.sql.SQLException;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
	private final static Storage ARRAY_STORAGE = new SortedArrayStorage();
	
	public static void main(String[] args) {
		Resume r1 = new Resume("uuid1");
		//r1.setUuid("uuid1");
		Resume r2 = new Resume("uuid2");
		//r2.setUuid("uuid2");
		Resume r3 = new Resume("uuid3");
		//r3.setUuid("uuid3");
		
		ARRAY_STORAGE.save(r1);
		ARRAY_STORAGE.save(r2);
		ARRAY_STORAGE.save(r3);

		try {
			System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Size: " + ARRAY_STORAGE.size());
		//System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
		printAll();
		ARRAY_STORAGE.delete(r1.getUuid());
		printAll();
		ARRAY_STORAGE.clear();
		printAll();
		System.out.println("Size: " + ARRAY_STORAGE.size());
	}
	
	static void printAll() {
		System.out.println("\nGet All");
		for (Resume r : ARRAY_STORAGE.getAllSorted()) {
			System.out.println(r);
		}
	}
}