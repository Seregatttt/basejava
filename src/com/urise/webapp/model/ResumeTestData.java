package com.urise.webapp.model;

import java.time.Month;
import java.time.YearMonth;

public class ResumeTestData {

	public static void main(String[] args) {
		Resume resume = new Resume("GrigoryKislin");
		resume.setContacts(ContactType.PHONE, "+7(921) 855-0482");
		resume.setContacts(ContactType.SKYPE, "grigory.kislin");
		resume.setContacts(ContactType.MAIL, "gkislin@yandex.ru");
		resume.setContacts(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
		resume.setContacts(ContactType.GITHUB, "https://github.com/gkislin");
		resume.setContacts(ContactType.STATCKOVERFLOW, "https://stackoverflow.com/users/548473 ");
		resume.setContacts(ContactType.HOME_PAGE, "http://gkislin.ru/");
		System.out.println(resume.getFullName());
		System.out.println(resume.getContacts());

		AbstractSection section = new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
		resume.setSections(SectionType.OBJECTIVE, section);
		section = new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
		resume.setSections(SectionType.PERSONAL, section);

		ListStringSection listSection = new ListStringSection();
		listSection.save("С 2013 года: разработка проектов \"Разработка Web приложения\"");
		listSection.save("Реализация двухфакторной аутентификации");
		resume.setSections(SectionType.ACHIEVEMENT, listSection);

		listSection = new ListStringSection();
		listSection.save("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
		listSection.save("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
		resume.setSections(SectionType.QUALIFICATIONS, listSection);

		String nameOrg = "Java Online Projects";
		YearMonth ym1 = YearMonth.of(2013, Month.OCTOBER);
		YearMonth ym2 = YearMonth.of(2019, Month.DECEMBER);
		String position = "Автор проекта.";
		String description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
		Organization org = new Organization(nameOrg, ym1, ym2, position, description);
		OrganizationSection objectSection = new OrganizationSection(org);
		resume.setSections(SectionType.EXPERIENCE, objectSection);

		nameOrg = "Coursera";
		ym1 = YearMonth.of(2013, Month.MARCH);
		ym2 = YearMonth.of(2013, Month.MAY);
		position = "";
		description = "\"Functional Programming Principles in Scala\" by Martin Odersky";
		org = new Organization(nameOrg, ym1, ym2, position, description);
		objectSection = new OrganizationSection(org);
		resume.setSections(SectionType.EDUCATION, objectSection);

		System.out.println(resume.getSections());

	}
}
