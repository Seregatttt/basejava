package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
	//private static Storage storage = new ArrayStorage();
	private static Storage storage;

	private static final String UUID_1 = "uuid1";
	private static final Resume RESUME_1 = new Resume(UUID_1);
	private static final String UUID_2 = "uuid2";
	private static final Resume RESUME_2 = new Resume(UUID_2);
	private static final String UUID_3 = "uuid3";
	private static final Resume RESUME_3 = new Resume(UUID_3);
	private static final String UUID_4 = "uuid4";
	private static final Resume RESUME_4 = new Resume(UUID_4);

	public AbstractArrayStorageTest(Storage storage) {
		this.storage = storage;
	}

	@Before
	public void setUp() throws Exception {
		storage.save(RESUME_1);
		storage.save(RESUME_2);
		storage.save(RESUME_3);
	}

	@Test
	public void update() throws Exception {
		storage.update(RESUME_3);
	}

	@Test(expected = NotExistStorageException.class)
	public void updateNotExist() throws Exception {
		storage.update(new Resume("dummy"));
	}

	@Test
	public void save() throws Exception {
		storage.save(RESUME_4);
		assertEquals(RESUME_4, storage.get(UUID_4));
	}

	@Test(expected = ExistStorageException.class)
	public void saveExistResume() throws Exception {
		storage.save(RESUME_3);
	}

	@Test(expected = StorageException.class)
	public void saveOverflowResume() throws Exception {
		storage.clear();
		try {
			for (int i = 0; i < STORAGE_LIMIT; i++) {
				storage.save(new Resume("uuid_" + i));
			}
		} catch (StorageException e) {
			fail("Test fail");
		}
		storage.save(new Resume("uuid_" + Integer.toString(STORAGE_LIMIT + 1)));
	}

	@Test
	public void delete() throws Exception {
		storage.delete(UUID_3);
	}

	@Test(expected = NotExistStorageException.class)
	public void deleteNotExist() throws Exception {
		storage.delete("dummy");
	}

	@Test
	public void getAll() throws Exception {
		Resume[] resumes = storage.getAll();
		assertEquals(3, resumes.length);
	}

	@Test
	public void size() throws Exception {
		assertEquals(3, storage.size());
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
		assertEquals(0, storage.size());
	}
}