package com.urise.webapp.model;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;

public class ResumeTestData {

	public static Resume GetResume(String uuid, String fullName) {
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
		YearMonth ym1 = YearMonth.of(2013, Month.OCTOBER);
		YearMonth ym2 = YearMonth.of(2019, Month.DECEMBER);
		String position = "Автор проекта.";
		String description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
		Organization org = new Organization(nameOrg, url, ym1, ym2, position, description);
		OrganizationSection objectSection = new OrganizationSection(org);
		resume.setSections(SectionType.EXPERIENCE, objectSection);

		nameOrg = "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики";
		url = "www.leningrad.ru";
		ym1 = YearMonth.of(1993, Month.SEPTEMBER);
		ym2 = YearMonth.of(1996, Month.JULY);
		position = "Инженер (программист Fortran, C)";
		description = null;
		org = new Organization(nameOrg, url, ym1, ym2, position, description);
		ym1 = YearMonth.of(1987, Month.SEPTEMBER);
		ym2 = YearMonth.of(1993, Month.JULY);
		position = "Аспирантура (программист С, С++)";
		description = null;
		org.addPeriod(ym1, ym2, position, description);
		objectSection = new OrganizationSection(org);
		resume.setSections(SectionType.EDUCATION, objectSection);

		System.out.println(resume.getSections(SectionType.EDUCATION));
		return resume;
	}


}
