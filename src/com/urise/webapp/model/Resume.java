package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
	// Unique identifier
	private String uuid;
	private String fullName;

	Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
	Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

	public Resume(String fullName) {
		this(UUID.randomUUID().toString(), fullName);
	}

	public Resume(String uuid, String fullName) {
		Objects.requireNonNull(uuid, "uuid must not be null");
		Objects.requireNonNull(fullName, "fullName must not be null");
		this.uuid = uuid;
		this.fullName = fullName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFullName() {
		return fullName;
	}

	@Override
	public String toString() {
		return uuid + '(' + fullName + ')';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Resume resume = (Resume) o;

		if (!Objects.equals(fullName, "")) {
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
		int result = uuid.hashCode();
		result = 31 * result + fullName.hashCode();
		return result;
	}

	@Override
	public int compareTo(Resume o) {
		int cmp = fullName.compareTo(o.fullName);
		return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
	}
}
