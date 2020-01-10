package com.urise.webapp.storage;

import com.urise.webapp.strategy.ObjectStreamStrategy;

public class PathStorageTest extends AbstractStorageTest {
	public PathStorageTest() {
		super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStreamStrategy()));
	}
}