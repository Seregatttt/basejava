package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.fail;

public class AbstractArrayStorageTest extends AbstractStorageTest {

	AbstractArrayStorageTest(Storage storage) {
		super(storage);
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
		storage.save(new Resume("uuid_" + (STORAGE_LIMIT + 1)));
	}
}
