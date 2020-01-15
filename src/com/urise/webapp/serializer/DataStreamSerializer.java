package com.urise.webapp.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DataStreamSerializer implements StreamSerializer {
	private static final Logger LOG = Logger.getLogger(DataStreamSerializer.class.getName());
	private Section section;
	
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
				
				if ((entry.getKey() == SectionType.OBJECTIVE) || (entry.getKey() == SectionType.PERSONAL)) {
					section = entry.getValue();
					TextSection textSection = (TextSection) section;
					dos.writeUTF(textSection.getContent());
				}
				
				if ((entry.getKey() == SectionType.ACHIEVEMENT) || (entry.getKey() == SectionType.QUALIFICATIONS)) {
					ListSection listSection = (ListSection) entry.getValue();
					dos.writeInt(listSection.getList().size());
					for (String str : listSection.getList()) {
						dos.writeUTF(str);
					}
				}
				
				if ((entry.getKey() == SectionType.EXPERIENCE) || (entry.getKey() == SectionType.EDUCATION)) {
					OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
					dos.writeInt(organizationSection.getOrganizations().size());
					for (Organization org : organizationSection.getOrganizations()) {
						dos.writeUTF(org.getHomePage().getName());
						dos.writeUTF(org.getHomePage().getUrl());
						dos.writeInt(org.getPositions().size());
						for (Organization.Position pos : org.getPositions()) {
							LocalDateAdapter localDateAdapter = new LocalDateAdapter();
							dos.writeUTF(localDateAdapter.marshal(pos.getStartDate()));
							dos.writeUTF(localDateAdapter.marshal(pos.getEndDate()));
							dos.writeUTF(pos.getTitle());
							if (pos.getDescription() != null) {
								dos.writeUTF(pos.getDescription());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Exception doWrite =" + e.toString());
			e.printStackTrace();
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
			resume.addSection(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
			resume.addSection(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
			
			String nameSection;
			for (int jj = 0; jj < 2; jj++) {
				nameSection = dis.readUTF();
				size = dis.readInt();
				ListSection listSection = new ListSection(new ArrayList<>());
				
				for (int i = 0; i < size; i++) {
					listSection.save(dis.readUTF());
				}
				resume.addSection(SectionType.valueOf(nameSection), listSection);
			}
			
			for (int jj = 0; jj < 2; jj++) {
				nameSection = dis.readUTF();
				int sizeOrg1 = dis.readInt();
				List<Organization> organizations = new ArrayList<>();
				for (int i = 0; i < sizeOrg1; i++) {//loop Organizations
					Link link = new Link(dis.readUTF(), dis.readUTF());
					int sizePos1 = dis.readInt();
					List<Organization.Position> positions = new ArrayList<>();
					LocalDateAdapter localDateAdapter = new LocalDateAdapter();
					for (int j = 0; j < sizePos1; j++) {//loop Positions
						LocalDate startDate = localDateAdapter.unmarshal(dis.readUTF());
						LocalDate endDate = localDateAdapter.unmarshal(dis.readUTF());
						String title = dis.readUTF();
						String description = null;
						if (SectionType.valueOf(nameSection) == SectionType.EXPERIENCE) {
							description = dis.readUTF();
						}
						positions.add(new Organization.Position(startDate, endDate, title, description));
					}
					organizations.add(new Organization(link, positions));
				}
				resume.addSection(SectionType.valueOf(nameSection), new OrganizationSection(organizations));
			}
			return resume;
		} catch (Exception e) {
			LOG.info("Exception doRead =" + e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
