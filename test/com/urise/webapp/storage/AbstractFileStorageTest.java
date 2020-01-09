package com.urise.webapp.storage;

public class AbstractFileStorageTest extends AbstractStorageTest {
	public AbstractFileStorageTest() {
		//super(new AbstractFileStorage(STORAGE_DIR, new ObjectStreamPathStorage()));
		super(new AbstractPathStorage(STORAGE_DIR.getPath(), new ObjectStreamStorage()));
		//работают обе стратегии ObjectStreamPathStorage и ObjectStreamStorage , выбирай любую!!!
	}
}