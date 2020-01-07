package com.urise.webapp.model;

import java.time.Month;
import java.util.ArrayList;

public class ResumeTestData {
	
	public static Resume getResume(String uuid, String fullName) {
		Resume resume = new Resume(uuid, "GrigoryKislin");
		resume.setContacts(ContactType.PHONE, "+7(921) 855-0482");
		resume.setContacts(ContactType.SKYPE, "grigory.kislin");
		resume.setContacts(ContactType.MAIL, "gkislin@yandex.ru");
		resume.setContacts(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
		resume.setContacts(ContactType.GITHUB, "https://github.com/gkislin");
		resume.setContacts(ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/548473 ");
		resume.setContacts(ContactType.HOME_PAGE, "http://gkislin.ru/");
		System.out.println(resume.getFullName());
		System.out.println(resume.getContacts(ContactType.PHONE));
		
		AbstractSection section = new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
		resume.setSections(SectionType.OBJECTIVE, section);
		section = new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
		resume.setSections(SectionType.PERSONAL, section);
		
		ListStringSection listSection = new ListStringSection(new ArrayList<>());
		listSection.save("С 2013 года: разработка проектов \"Разработка Web приложения\"");
		listSection.save("Реализация двухфакторной аутентификации");
		resume.setSections(SectionType.ACHIEVEMENT, listSection);
		
		listSection = new ListStringSection(new ArrayList<>());
		listSection.save("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
		listSection.save("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
		resume.setSections(SectionType.QUALIFICATIONS, listSection);
		
		String nameOrg = "Java Online Projects";
		String url = "www.leningrad.ru";
		String title = "Автор проекта.";
		String description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
		Organization org =
				new Organization(nameOrg, url,
						new Organization.Position(2013, Month.OCTOBER, 2019, Month.DECEMBER, title, description));
		OrganizationSection objectSection = new OrganizationSection(org);
		resume.setSections(SectionType.EXPERIENCE, objectSection);
		
		org = new Organization(
				"Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
				"www.leningrad.ru",
				new Organization.Position(1993, Month.SEPTEMBER, 1996, Month.JULY,
						"Инженер (программист Fortran, C)", null),
				new Organization.Position(1987, Month.SEPTEMBER, 1993, Month.JULY,
						"Аспирантура (программист С, С++)", null));
		objectSection = new OrganizationSection(org);
		resume.setSections(SectionType.EDUCATION, objectSection);
		
		System.out.println(resume.getSections(SectionType.EDUCATION));
		return resume;
	}
	
	
}
