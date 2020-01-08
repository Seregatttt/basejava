package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamPathStorage extends AbstractPathStorage implements Strategy {
	
	public ObjectStreamPathStorage(String dir) {
		super(dir);
	}
	
	@Override
	public void doWrite(Resume r, OutputStream os) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
			oos.writeObject(r);
		}
	}
	
	@Override
	public Resume doRead(InputStream is) throws IOException {
		try (ObjectInputStream ois = new ObjectInputStream(is)) {
			return (Resume) ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new StorageException("Error read resume", null, e);
		}
	}
}