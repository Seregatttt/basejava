package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class AbstractFileStorage extends AbstractStorage<File> implements AutoCloseable {
	private File directory;

	protected AbstractFileStorage(File directory) {
		requireNonNull(directory, "directory must not be null");
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
		}
		if (!directory.canRead() || !directory.canWrite()) {
			throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
		}
		this.directory = directory;
	}

	@Override
	public void clear() {
		if (directory.listFiles() != null) {
			for (File file : directory.listFiles()) {
				file.delete();
			}
		}
	}

	@Override
	public int size() {
		String[] list = directory.list();
		return list.length;
	}

	@Override
	protected File getSearchKey(String uuid) {
		return new File(directory, uuid);
	}

	@Override
	protected void doUpdate(Resume r, File file) {
		doSave(r, file);
	}

	@Override
	protected boolean isExist(File file) {
		return file.exists();
	}

	@Override
	protected void doSave(Resume r, File file) {
		try {
			file.createNewFile();
			doWrite(r, file);
		} catch (IOException e) {
			throw new StorageException("IO error", file.getName(), e);
		}
	}

	protected abstract void doWrite(Resume r, File file) throws IOException;

	protected abstract Resume doRead(File file) throws IOException;

	@Override
	protected Resume doGet(File file) {
		try {
			return doRead(file);
		} catch (IOException e) {
			throw new StorageException("IO error (doGet)", file.getName(), e);
		}
	}

	@Override
	protected void doDelete(File file) {
		file.delete();
	}

	@Override
	protected List<Resume> doCopyAll() {
		List<Resume> list = new ArrayList<>();
		for (File file : directory.listFiles()) {
			try {
				list.add(doRead(file));
			} catch (IOException e) {
				throw new StorageException("IO error (doCopyAll)", file.getName(), e);
			}
		}
		return list;
	}
}