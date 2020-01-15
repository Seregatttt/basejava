package com.urise.webapp.storage;

import com.urise.webapp.serializer.ObjectStreamStrategy;

public class FileStorageTest extends AbstractStorageTest {
	public FileStorageTest() {
		super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
	}
}