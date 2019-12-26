package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

	private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

	public void update(Resume resume) {
		LOG.info("Update " + resume);
		SK searchKey = getExistSearchKey(resume.getUuid());
		doUpdate(resume, searchKey);
	}

	public void save(Resume resume) {
		LOG.info("Save " + resume);
		SK searchKey = getNotExistSearchKey(resume.getUuid());
		doSave(resume, searchKey);
	}

	public Resume get(String uuid) {
		LOG.info("Get " + uuid);
		SK searchKey = getExistSearchKey(uuid);
		return doGet(searchKey);
	}

	public void delete(String uuid) {
		LOG.info("Delete " + uuid);
		SK searchKey = getExistSearchKey(uuid);
		doDelete(searchKey);
	}

	private SK getExistSearchKey(String uuid) {
		SK searchKey = getSearchKey(uuid);
		if (isExist(searchKey)) {
			return searchKey;
		} else {
			LOG.warning("GetResume " + uuid + " not exist");
			throw new NotExistStorageException(uuid);
		}
	}

	private SK getNotExistSearchKey(String uuid) {
		SK searchKey = getSearchKey(uuid);
		if (!isExist(searchKey)) {
			return searchKey;
		} else {
			LOG.warning("GetResume " + uuid + " already exist");
			throw new ExistStorageException(uuid);
		}
	}

	public List<Resume> getAllSorted() {
		LOG.info("getAllSorted");
		List<Resume> list = doCopyAll();
		Collections.sort(list);
		return list;
	}

	protected abstract void doUpdate(Resume resume, SK searchKey);

	protected abstract void doSave(Resume resume, SK searchKey);

	protected abstract Resume doGet(SK searchKey);

	protected abstract void doDelete(SK searchKey);

	protected abstract SK getSearchKey(String searchKey);

	protected abstract boolean isExist(SK searchKey);

	protected abstract List<Resume> doCopyAll();
}
