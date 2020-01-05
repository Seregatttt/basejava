package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {

	public NotExistStorageException(String uuid) {
		super("getResume " + uuid + " not exist", uuid);
	}
}