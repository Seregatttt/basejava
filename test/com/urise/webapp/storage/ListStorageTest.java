package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ListStorageTest {

	private static Storage storage = new ListStorage();

	private static final String UUID_1 = "uuid1";
	private static final Resume RESUME_1 = new Resume(UUID_1);
	private static final String UUID_2 = "uuid2";
	private static final Resume RESUME_2 = new Resume(UUID_2);
	private static final String UUID_3 = "uuid3";
	private static final Resume RESUME_3 = new Resume(UUID_3);
	private static final String UUID_4 = "uuid4";
	private static final Resume RESUME_4 = new Resume(UUID_4);

	@Before
	public void setUp() throws Exception {
		storage.save(RESUME_1);
		storage.save(RESUME_2);
		storage.save(RESUME_3);
	}

	@Test
	public void clear() throws Exception {
		storage.clear();
		assertEquals(0,storage.size());
	}

	@Test
	public void update() throws Exception {
		storage.update(RESUME_1);
		Object ob = storage.get(UUID_1);
		assertEquals(ob,RESUME_1);
	}

	@Test
	public void save() throws Exception {
		storage.save(RESUME_4);
		assertEquals(RESUME_4,storage.get(UUID_4));
	}

	@Test
	public void get() throws Exception {
		Object ob = storage.get(UUID_2);
		assertEquals(ob,RESUME_2);
	}

	@Test
	public void delete() throws Exception {
		storage.delete(UUID_1);
		assertEquals(2,storage.size());
	}

	@Test
	public void getAll() throws Exception {
		//i need  help)))
	}

	@Test
	public void size() throws Exception {
		assertEquals(3,storage.size());
	}

}