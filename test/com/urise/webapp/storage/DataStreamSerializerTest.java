package com.urise.webapp.storage;

import com.urise.webapp.serializer.DataStreamSerializer;

public class DataStreamSerializerTest extends AbstractStorageTest {
	
	public DataStreamSerializerTest() {
		super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
	}
}