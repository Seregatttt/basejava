package com.urise.webapp.storage;

import com.urise.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
	protected static final File STORAGE_DIR = Config.get().getStorageDir();
	protected Storage storage;
	//protected static Storage storage = new ListStorage(); //for local test
	private static final String UUID_1 = "uuid1";
	private static final Resume RESUME_1 = ResumeTestData.getResume(UUID_1, "name1");
	private static final String UUID_2 = "uuid2";
	private static final Resume RESUME_2 = ResumeTestData.getResume(UUID_2, "name2");
	private static final String UUID_3 = "uuid3";
	private static final Resume RESUME_3 = ResumeTestData.getResume(UUID_3, "name3");
	private static final String UUID_4 = "uuid4";
	private static final Resume RESUME_4 = ResumeTestData.getResume(UUID_4, "name4");

	AbstractStorageTest(Storage storage) {
		this.storage = storage;
	}

	@Before
	public void setUp() throws Exception {
		storage.clear();
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
		Resume newResume = ResumeTestData.getResume(UUID_1, "New Name");
		newResume.addContact(ContactType.PHONE, "123456789"); // case1  update PHONE
		newResume.addContact(ContactType.SKYPE, "testSkype"); // case1  update SKYPE
		newResume.addContact(ContactType.MAIL, "testSkype");  // case2  add    MAIL
		newResume.delContact(ContactType.HOME_PAGE);               // case3  del    HOME_PAGE
		storage.update(newResume);
		assertTrue(newResume.equals(storage.get(UUID_1)));
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