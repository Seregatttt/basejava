package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {
	//private Storage storage = new ArrayStorage();
	private Storage storage;

	private static final String UUID_1 = "uuid1";
	private static final String UUID_2 = "uuid2";
	private static final String UUID_3 = "uuid3";
	private static final String UUID_4 = "uuid4";

	public AbstractArrayStorageTest(Storage storage) {
		this.storage = storage;
	}

	@Before
	public void setUp() throws Exception {
		storage.clear();
		storage.save(new Resume(UUID_1));
		storage.save(new Resume(UUID_2));
		storage.save(new Resume(UUID_3));
	}

	@Test
	public void clear() throws Exception {
		storage.clear();
		for (Resume resume : storage.getAll()) {
			System.out.println(resume);
		}
	}

	@Test
	public void update() throws Exception {
		storage.update(storage.get(UUID_1));
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
				storage.save(new Resume("uuid_" + Integer.toString(i)));
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
		for (Resume resume : resumes) {
			System.out.println(resume);
		}
	}

	@Test
	public void size() throws Exception {
		Assert.assertEquals(3, storage.size());
	}

	@Test
	public void get() throws Exception {
		storage.get(UUID_1);
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() throws Exception {
		storage.get("dummy");
	}

}