package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Test;

import java.util.ArrayList;

public class ListStorageTest {
	private static ArrayList storage = new ArrayList();

	private static final String UUID_1 = "uuid1";
	private static final Resume RESUME_1 = new Resume(UUID_1);

	@Test
	public void save() throws Exception {
		storage.add(RESUME_1);
	}

}