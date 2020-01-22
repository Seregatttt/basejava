package com.urise.webapp.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.SectionType.valueOf;

public class DataStreamSerializer implements StreamSerializer {
	
	@Override
	public void doWrite(Resume r, OutputStream os) throws IOException {
		try (DataOutputStream dos = new DataOutputStream(os)) {
			dos.writeUTF(r.getUuid());
			dos.writeUTF(r.getFullName());
			
			Map<ContactType, String> contacts = r.getContacts();
			
			writeWithException(contacts.entrySet(), dos, contact -> {
				dos.writeUTF(contact.getKey().name());
				dos.writeUTF(contact.getValue());
			});
			
			Map<SectionType, Section> sections = r.getSections();
			
			writeWithException(sections.entrySet(), dos, entry -> {
				dos.writeUTF(entry.getKey().name());
				switch (entry.getKey()) {
					case OBJECTIVE:
					case PERSONAL:
						TextSection textSection = (TextSection) entry.getValue();
						dos.writeUTF(textSection.getContent());
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						ListSection listSection = (ListSection) entry.getValue();
						writeWithException(listSection.getList(), dos, dos::writeUTF);
						break;
					case EXPERIENCE:
					case EDUCATION:
						OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
						writeWithException(organizationSection.getOrganizations(), dos, (Organization org) -> {
							writeWhenNull(org.getHomePage().getName(), dos);
							writeWhenNull(org.getHomePage().getUrl(), dos);
							writeWithException(org.getPositions(), dos, pos -> {
								dos.writeUTF(pos.getStartDate().toString());
								dos.writeUTF(pos.getEndDate().toString());
								dos.writeUTF(pos.getTitle());
								writeWhenNull(pos.getDescription(), dos);
							});
						});
						break;
				}
			});
		}
	}
	
	@Override
	public Resume doRead(InputStream is) throws IOException {
		try (DataInputStream dis = new DataInputStream(is)) {
			String uuid = dis.readUTF();
			String fullName = dis.readUTF();
			Resume resume = new Resume(uuid, fullName);
			readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
			readWithException(dis, () -> {
				String nameSection = dis.readUTF();
				Section section = null;
				switch (SectionType.valueOf(nameSection)) {
					case OBJECTIVE:
					case PERSONAL:
						section = new TextSection(dis.readUTF());
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						ListSection listSection = new ListSection(new ArrayList<>());
						readWithException(dis, () -> listSection.save(dis.readUTF()));
						section = listSection;
						break;
					case EXPERIENCE:
					case EDUCATION:
						List<Organization> organizations = new ArrayList<>();
						readWithException(dis, () -> {
							Link link = new Link(dis.readUTF(), readWhenNull(dis));
							List<Organization.Position> positions = new ArrayList<>();
							readWithException(dis, () -> {
								LocalDate startDate = LocalDate.parse(dis.readUTF());
								LocalDate endDate = LocalDate.parse(dis.readUTF());
								String title = dis.readUTF();
								String description = readWhenNull(dis);
								positions.add(new Organization.Position(startDate, endDate, title, description));
							});
							organizations.add(new Organization(link, positions));
						});
						section = new OrganizationSection(organizations);
						break;
				}
				resume.addSection(valueOf(nameSection), section);
			});
			return resume;
		}
	}
	
	@FunctionalInterface
	interface StorageWrite<T> {
		void doSave(T t) throws IOException;
		
	}
	
	@FunctionalInterface
	interface StorageRead {
		void doExtract() throws IOException;
		
	}
	
	private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, StorageWrite<T> sw) throws IOException {
		dos.writeInt(collection.size());
		for (T item : collection) {
			sw.doSave(item);
		}
	}
	
	private void readWithException(DataInputStream dis, StorageRead sr) throws IOException {
		int size = dis.readInt();
		for (int i = 0; i < size; i++) {
			sr.doExtract();
		}
	}
	
	private void writeWhenNull(String str, DataOutputStream dos) throws IOException {
		if (str != null) {
			dos.writeUTF(str);
		} else {
			dos.writeUTF("");
		}
	}
	
	private String readWhenNull(DataInputStream dis) throws IOException {
		String res = dis.readUTF();
		if (res.equals("")) {
			return null;
		}
		return res;
	}
}

