package com.urise.webapp.storage;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;

public class ResumeTestData {

	public static Resume getResume(String uuid, String fullName) {
		Resume resume = new Resume(uuid, fullName);
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

		resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(new Organization(
				"Java Online Projects", "url_org1",
				new Organization.Position(2013, Month.OCTOBER, 2019, Month.DECEMBER,
						"Author project", "description0"),
				new Organization.Position(2013, Month.OCTOBER, 2019, Month.DECEMBER,
						"position1", "description0")
				)));

		resume.addSection(SectionType.EDUCATION, new OrganizationSection(new Organization(
				"University SP", "www.leningrad.ru",
				new Organization.Position(1993, Month.SEPTEMBER, 1996, Month.JULY,
						"Inginer", "description1"),
				new Organization.Position(1987, Month.SEPTEMBER, 1993, Month.JULY,
						"Aspirant", "description2"))));
		return resume;
	}
}
