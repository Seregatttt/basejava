package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.strategy.Strategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
	private Path directory;
	private Strategy strategy;
	
	protected PathStorage(String dir, Strategy strategy) {
		directory = Paths.get(dir);
		Objects.requireNonNull(directory, "directory must not be null");
		if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
			throw new IllegalArgumentException(dir + " is not directory or is not writable");
		}
		this.strategy = strategy;
	}
	
	@Override
	public void clear() {
		getFilesList().forEach(this::doDelete);
	}
	
	@Override
	public int size() {
		return ((int) getFilesList().count());
	}
	
	@Override
	protected Path getSearchKey(String uuid) {
		return directory.resolve(uuid);
	}
	
	@Override
	protected void doUpdate(Resume r, Path path) {
		try {
			strategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
		} catch (IOException e) {
			throw new StorageException("Path write error", r.getUuid(), e);
		}
	}
	
	@Override
	protected boolean isExist(Path path) {
		return Files.isRegularFile(path);
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
			return strategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
		} catch (IOException e) {
			throw new StorageException("Path read error", path.toString(), e);
		}
	}
	
	@Override
	protected void doDelete(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new StorageException("Path delete error", path.toString(), e);
		}
	}
	
	@Override
	protected List<Resume> doCopyAll() {
		return getFilesList().map(this::doGet).collect(Collectors.toList());
	}
	
	private Stream<Path> getFilesList() {
		try {
			return Files.list(directory);
		} catch (IOException e) {
			throw new StorageException("getFilesList is  error", null, e);
		}
	}
}
