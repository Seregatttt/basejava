package com.urise.webapp.storage;

public class AbstractPathStorageTest extends AbstractStorageTest {
	public AbstractPathStorageTest() {
		super(new AbstractPathStorage(STORAGE_DIR.getPath(), new ObjectStreamPathStorage()));
		//super(new AbstractPathStorage(STORAGE_DIR.getPath(), new ObjectStreamStorage()));
		//работают обе стратегии ObjectStreamPathStorage и ObjectStreamStorage , выбирай любую!!!
	}
}