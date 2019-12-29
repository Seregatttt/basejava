package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
	private Path directory;

	protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

	protected abstract Resume doRead(InputStream is) throws IOException;

	protected AbstractPathStorage(String dir) {
		directory = Paths.get(dir);
		Objects.requireNonNull(directory, "directory must not be null");
		if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
			throw new IllegalArgumentException(dir + " is not directory or is not writable");
		}
	}

	@Override
	public void clear() {
		try {
			List<Path> list = Files.list(directory).collect(Collectors.toList());
			list.forEach(this::doDelete);
		} catch (IOException e) {
			throw new StorageException("Path delete error", null);
		}
	}

	@Override
	public int size() {
		List<Path> list = null;
		try {
			list = Files.list(directory).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (list == null) {
			throw new StorageException("Path read error", null);
		}
		return list.size();
	}

	@Override
	protected Path getSearchKey(String uuid) {
		return Paths.get(directory.toAbsolutePath() + "\\" + uuid);
	}

	@Override
	protected void doUpdate(Resume r, Path path) {
		try {
			doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
		} catch (IOException e) {
			throw new StorageException("Path write error", r.getUuid(), e);
		}
	}

	@Override
	protected boolean isExist(Path path) {
		return path != null;
	}

	@Override
	protected void doSave(Resume r, Path path) {
		try {
			Files.createFile(path);
		} catch (IOException e) {
			throw new StorageException("Couldn't create Path " + path.toString(),
					String.valueOf(path.getFileName()), e);
		}
		doUpdate(r, path);
	}

	@Override
	protected Resume doGet(Path path) {
		try {
			return doRead(new BufferedInputStream(new FileInputStream(path.toFile())));
		} catch (IOException e) {
			throw new StorageException("Path read error", path.toString(), e);
		}
	}

	@Override
	protected void doDelete(Path path) {
		if (path.toFile().delete()) {
			throw new StorageException("Path delete error", path.toString());
		}
	}

	@Override
	protected List<Resume> doCopyAll() {
		List<Path> listPath = null;
		try {
			listPath = Files.list(directory).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (listPath == null) {
			throw new StorageException("Directory read error", null);
		}
		List<Resume> list = new ArrayList<>(listPath.size());
		for (Path Path : listPath) {
			list.add(doGet(Path));
		}
		return list;
	}
}
