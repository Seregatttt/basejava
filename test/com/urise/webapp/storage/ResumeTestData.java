package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;

public class ResumeTestData {
	
	public static Resume getResume(String uuid, String fullName) {
		Resume resume = new Resume(uuid, "GrigoryKislin");
		resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
		resume.addContact(ContactType.SKYPE, "grigory.kislin");
		resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
		resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
		resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
		resume.addContact(ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/548473 ");
		resume.addContact(ContactType.HOME_PAGE, "http://gkislin.ru/");
		System.out.println(resume.getFullName());
		System.out.println(resume.getContact(ContactType.PHONE));
		
		Section section = new TextSection("MyObjectiveInfo");
		resume.addSection(SectionType.OBJECTIVE, section);
		section = new TextSection("MyPersonalInfo");
		resume.addSection(SectionType.PERSONAL, section);
		
		ListSection listSection = new ListSection(new ArrayList<>());
		listSection.save("MyAchievementInfo1");
		listSection.save("MyAchievementInfo2");
		resume.addSection(SectionType.ACHIEVEMENT, listSection);
		
		listSection = new ListSection(new ArrayList<>());
		listSection.save("MyQualificationInfo1");
		listSection.save("MyQualificationInfo2");
		resume.addSection(SectionType.QUALIFICATIONS, listSection);
		
		String nameOrg = "Java Online Projects";
		String url = "www.leningrad.ru";
		String title = "Author project";
		String description = "Creating project";
		Organization org =
				new Organization(nameOrg, url,
						new Organization.Position(2013, Month.OCTOBER, 2019, Month.DECEMBER, title, null));
		OrganizationSection objectSection = new OrganizationSection(org);
		resume.addSection(SectionType.EXPERIENCE, objectSection);
		
		org = new Organization(
				"University SP",
				"www.leningrad.ru",
				new Organization.Position(1993, Month.SEPTEMBER, 1996, Month.JULY,
						"Inginer", description),
				new Organization.Position(1987, Month.SEPTEMBER, 1993, Month.JULY,
						"Aspirant", null));
		objectSection = new OrganizationSection(org);
		resume.addSection(SectionType.EDUCATION, objectSection);
		
		System.out.println(resume.getSection(SectionType.EDUCATION));
		return resume;
	}
	
	
}
