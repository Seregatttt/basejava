package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
	private static final long serialVersionUID = 1L;
	// Unique identifier
	private String uuid;
	private String fullName;
	private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
	private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

	public Resume() {
	}

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

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Map<ContactType, String> getContacts() {
		return contacts;
	}

	public Map<SectionType, Section> getSections() {
		return sections;
	}

	public String getContact(ContactType type) {
		return contacts.get(type);
	}

	public void addContact(ContactType contactType, String value) {
		this.contacts.put(contactType, value);
	}

	public void delContact(ContactType contactType) {
		this.contacts.remove(contactType);
	}

	public Section getSection(SectionType type) {
		return sections.get(type);
	}

	public void addSection(SectionType sectionType, Section section) {
		this.sections.put(sectionType, section);
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
			if (!uuid.equals(resume.uuid)) return false;
			if (fullName != null ? !fullName.equals(resume.fullName) : resume.fullName != null) return false;
			if (contacts != null ? !contacts.equals(resume.contacts) : resume.contacts != null) return false;
			return sections != null ? sections.equals(resume.sections) : resume.sections == null;
		} else {
			return uuid.equals(resume.uuid);
		}
	}

	@Override
	public int hashCode() {
		int result = uuid.hashCode();
		result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
		result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
		result = 31 * result + (sections != null ? sections.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(Resume o) {
		int cmp = fullName.compareTo(o.fullName);
		return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
	}

}
