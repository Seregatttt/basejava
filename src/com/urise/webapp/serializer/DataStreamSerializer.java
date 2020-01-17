package com.urise.webapp.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
	
	@Override
	public void doWrite(Resume r, OutputStream os) throws IOException {
		try (DataOutputStream dos = new DataOutputStream(os)) {
			dos.writeUTF(r.getUuid());
			dos.writeUTF(r.getFullName());
			
			Map<ContactType, String> contacts = r.getContacts();
			dos.writeInt(contacts.size());
			for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
				dos.writeUTF(entry.getKey().name());
				dos.writeUTF(entry.getValue());
			}
			
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
						dos.writeInt(listSection.getList().size());
						for (String str : listSection.getList()) {
							dos.writeUTF(str);
						}
						break;
					
					case EXPERIENCE:
					case EDUCATION:
						OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
						dos.writeInt(organizationSection.getOrganizations().size());
						for (Organization org : organizationSection.getOrganizations()) {
							dos.writeUTF(org.getHomePage().getName());
							dos.writeUTF(org.getHomePage().getUrl());
							dos.writeInt(org.getPositions().size());
							for (Organization.Position pos : org.getPositions()) {
								dos.writeUTF(pos.getStartDate().toString());
								dos.writeUTF(pos.getEndDate().toString());
								dos.writeUTF(pos.getTitle());
								if (pos.getDescription() != null) {
									dos.writeUTF(pos.getDescription());
								} else {
									dos.writeUTF("");
								}
							}
						}
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
			
			String nameSection;
			for (SectionType sectionType : SectionType.values()) {
				switch (sectionType) {
					case OBJECTIVE:
					case PERSONAL:
						resume.addSection(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
						break;
					case ACHIEVEMENT:
					case QUALIFICATIONS:
						nameSection = dis.readUTF();
						size = dis.readInt();
						ListSection listSection = new ListSection(new ArrayList<>());
						
						for (int i = 0; i < size; i++) {
							listSection.save(dis.readUTF());
						}
						resume.addSection(SectionType.valueOf(nameSection), listSection);
						break;
					case EXPERIENCE:
					case EDUCATION:
						nameSection = dis.readUTF();
						int sizeOrg1 = dis.readInt();
						List<Organization> organizations = new ArrayList<>();
						for (int i = 0; i < sizeOrg1; i++) {//loop Organizations
							Link link = new Link(dis.readUTF(), dis.readUTF());
							int sizePos1 = dis.readInt();
							List<Organization.Position> positions = new ArrayList<>();
							for (int j = 0; j < sizePos1; j++) {//loop Positions
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
						resume.addSection(SectionType.valueOf(nameSection), new OrganizationSection(organizations));
						break;
				}
			}
			return resume;
		}
	}
}

