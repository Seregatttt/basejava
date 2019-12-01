package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
	//private static Storage storage = new ArrayStorage();
	private static Storage storage;

	private static final String UUID_1 = "uuid1";
	private static final String UUID_2 = "uuid2";
	private static final String UUID_3 = "uuid3";
	private static final String UUID_4 = "uuid4";

	AbstractArrayStorageTest(Storage vstorage) {
		System.out.println("AbstractArrayStorageTest "+storage);
		storage = vstorage;
	}

	@BeforeClass
	public static void setUp() throws Exception {
		System.out.println(storage);
		storage.save(new Resume(UUID_1));
		storage.save(new Resume(UUID_2));
		storage.save(new Resume(UUID_3));
	}

	@Test
	public void update() throws Exception {
		storage.update(new Resume(UUID_3));
	}

	@Test(expected = NotExistStorageException.class)
	public void updateNotExist() throws Exception {
		storage.update(new Resume("xxx"));
	}

	@Test
	public void save() throws Exception {
		storage.save(new Resume(UUID_4));
	}

	@Test(expected = ExistStorageException.class)
	public void saveExistResume() throws Exception {
		storage.save(new Resume(UUID_3));
	}

	@Test(expected = StorageException.class)
	public void saveOverflowResume() throws Exception {
		storage.clear();
		try {
			for (int i = 0; i < STORAGE_LIMIT; i++) {
				storage.save(new Resume("uuid_" + i));
			}
		} catch (StorageException e) {
			Assert.fail("Test fail");
		}
		storage.save(new Resume("uuid_" + Integer.toString(STORAGE_LIMIT + 1)));
	}

	@Test
	public void delete() throws Exception {
		storage.delete(UUID_3);
	}

	@Test(expected = NotExistStorageException.class)
	public void deleteNotExist() throws Exception {
		storage.delete("xxx");
	}

	@Test
	public void getAll() throws Exception {
		Resume[] resumes = storage.getAll();
		Assert.assertEquals(STORAGE_LIMIT - 1, storage.size());
	}

	@Test
	public void size() throws Exception {
		Assert.assertEquals(STORAGE_LIMIT - 1, storage.size());
	}

	@Test
	public void get() throws Exception {
		storage.get(UUID_1);
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() throws Exception {
		storage.get("dummy");
	}

	@Test
	public void clear() throws Exception {
		storage.clear();
		Assert.assertEquals(0, storage.size());
	}
}