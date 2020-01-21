package com.urise.webapp.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.SectionType.valueOf;
import static com.urise.webapp.model.SectionType.values;

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
			int size = dis.readInt();
			for (int i = 0; i < size; i++) {
				resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
			}
			int sizeSections = dis.readInt();
			for (SectionType sectionType : values()) {
				String nameSection = null;
				Section section = null;
				switch (sectionType) {
					case OBJECTIVE:
					case PERSONAL:
						
						nameSection = dis.readUTF();
						section = new TextSection(dis.readUTF());
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						nameSection = dis.readUTF();
						size = dis.readInt();
						ListSection listSection = new ListSection(new ArrayList<>());
						
						for (int i = 0; i < size; i++) {
							listSection.save(dis.readUTF());
						}
						section = listSection;
						break;
					case EXPERIENCE:
					case EDUCATION:
						nameSection = dis.readUTF();
						int sizeOrg = dis.readInt();
						List<Organization> organizations = new ArrayList<>();
						for (int i = 0; i < sizeOrg; i++) {//loop Organizations
							Link link = new Link(dis.readUTF(), dis.readUTF());
							int sizePos = dis.readInt();
							List<Organization.Position> positions = new ArrayList<>();
							for (int j = 0; j < sizePos; j++) {//loop Positions
								LocalDate startDate = LocalDate.parse(dis.readUTF());
								LocalDate endDate = LocalDate.parse(dis.readUTF());
								String title = dis.readUTF();
								String description = dis.readUTF();
								if (description.equals("")) {
									description = null;
								}
								positions.add(new Organization.Position(startDate, endDate, title, description));
							}
							organizations.add(new Organization(link, positions));
						}
						section = new OrganizationSection(organizations);
						break;
				}
				resume.addSection(valueOf(nameSection), section);
			}
			return resume;
		}
	}
	
	@FunctionalInterface
	interface SaveSection<T> {
		void doSave(T t) throws IOException;
		
	}
	
	private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, SaveSection<T> ss) throws IOException {
		dos.writeInt(collection.size());
		for (T item : collection) {
			ss.doSave(item);
		}
	}
	
	private void writeWhenNull(String str, DataOutputStream dos) throws IOException {
		if (str != null) {
			dos.writeUTF(str);
		} else {
			dos.writeUTF("");
		}
	}
}

