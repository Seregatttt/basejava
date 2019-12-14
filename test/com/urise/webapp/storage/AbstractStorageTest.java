package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
	protected Storage storage;
	//protected static Storage storage = new MapStorage(); //for local test
	private static final String UUID_1 = "uuid1";
	private static final Resume RESUME_1 = new Resume(UUID_1, "name1");
	private static final String UUID_2 = "uuid2";
	private static final Resume RESUME_2 = new Resume(UUID_2, "name2");
	private static final String UUID_3 = "uuid3";
	private static final Resume RESUME_3 = new Resume(UUID_3, "name3");
	private static final String UUID_4 = "uuid4";
	private static final Resume RESUME_4 = new Resume(UUID_4, "name4");

	AbstractStorageTest(Storage storage) {
		this.storage = storage;
	}

	@Before
	public void setUp() throws Exception {
		storage.save(RESUME_3);
		storage.save(RESUME_2);
		storage.save(RESUME_1);
	}

	@Test
	public void clear() throws Exception {
		storage.clear();
		assertEquals(0, storage.size());
	}

	@Test
	public void update() throws Exception {
		storage.update(RESUME_3);
		assertEquals(RESUME_3, storage.get(UUID_3));
	}

	@Test(expected = NotExistStorageException.class)
	public void updateNotExist() throws Exception {
		storage.update(new Resume("dummy"));
	}

	@Test
	public void save() throws Exception {
		storage.save(RESUME_4);
		assertEquals(4, storage.size());
		assertEquals(RESUME_4, storage.get(UUID_4));
	}

	@Test(expected = ExistStorageException.class)
	public void saveExistResume() throws Exception {
		storage.save(RESUME_3);
	}

	@Test
	public void get() throws Exception {
		assertEquals(RESUME_1, storage.get(UUID_1));
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() throws Exception {
		storage.get("dummy");
	}

	@Test(expected = NotExistStorageException.class)
	public void delete() throws Exception {
		storage.delete(UUID_3);
		storage.get(UUID_3);
		assertEquals(2, storage.size());
	}

	@Test(expected = NotExistStorageException.class)
	public void deleteNotExist() throws Exception {
		storage.delete("dummy");
	}

	@Test
	public void size() throws Exception {
		assertEquals(3, storage.size());
	}

	@Test
	public void getAllSorted() throws Exception {
		List<Resume> list = storage.getAllSorted();
		assertEquals(3, list.size());
		assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), list);
	}

}