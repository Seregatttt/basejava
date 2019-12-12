package com.urise.webapp.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
	// Unique identifier
	private String uuid;
	private String fullName;

	public Resume(String fullName) {
		this(UUID.randomUUID().toString(), fullName);
	}

	public Resume(String uuid, String fullName) {
		this.uuid = uuid;
		this.fullName = fullName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Resume resume = (Resume) o;

		if (fullName != "") {
			if (!uuid.equals(resume.uuid)) {
				return false;
			}
			return fullName.equals(resume.fullName);
		} else {
			return uuid.equals(resume.uuid);
		}
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public String toString() {
		return uuid + '(' + fullName + ')';
	}

	@Override
	public int compareTo(Resume o) {
		int i = fullName.compareTo(o.fullName);
		return i != 0 ? i : uuid.compareTo(o.uuid);
	}
}
