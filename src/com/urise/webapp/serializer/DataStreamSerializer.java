package com.urise.webapp.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
	
	@Override
	public void doWrite(Resume r, OutputStream os) throws IOException {
		try (DataOutputStream dos = new DataOutputStream(os)) {
			dos.writeUTF(r.getUuid());
			dos.writeUTF(r.getFullName());
			
			Map<ContactType, String> contacts = r.getContacts();
			
			SaveSection ss = (SaveSection<Map.Entry<ContactType, String>>) entry -> {
				dos.writeUTF(entry.getKey().name());
				dos.writeUTF(entry.getValue());
			};
			writeWithExeption(contacts.entrySet(), dos, ss);
			
			Map<SectionType, Section> sections = r.getSections();
			for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
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
						ss = (SaveSection<String>) entry1 -> dos.writeUTF(entry1);
						writeWithExeption(listSection.getList(), dos, ss);
						break;
					case EXPERIENCE:
					case EDUCATION:
						OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
						ss = (SaveSection<Organization>) entry12 -> {
							dos.writeUTF(entry12.getHomePage().getName());
							dos.writeUTF(entry12.getHomePage().getUrl());
							dos.writeInt(entry12.getPositions().size());
							for (Organization.Position pos : entry12.getPositions()) {
								dos.writeUTF(pos.getStartDate().toString());
								dos.writeUTF(pos.getEndDate().toString());
								dos.writeUTF(pos.getTitle());
								
								if (pos.getDescription() != null) {
									dos.writeUTF(pos.getDescription());
								} else {
									dos.writeUTF("");
								}
							}
						};
						writeWithExeption(organizationSection.getOrganizations(), dos, ss);
						break;
				}
			}
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
			
			for (SectionType sectionType : SectionType.values()) {
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
				resume.addSection(SectionType.valueOf(nameSection), section);
			}
			return resume;
		}
	}
	
	@FunctionalInterface
	interface SaveSection<T> {
		void doSave(T t) throws IOException;
	}
	
	private <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, SaveSection<T> ss) throws IOException {
		dos.writeInt(collection.size());
		for (T item : collection) {
			ss.doSave(item);
		}
	}
}

