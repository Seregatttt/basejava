package com.urise.webapp.model;

public enum ContactType {
	PHONE("phone"),
	SKYPE("skype"),
	MAIL("mail"),
	LINKEDIN("linkedin"),
	GITHUB("github"),
	STATCKOVERFLOW("stackoverflow"),
	HOME_PAGE("home_page");

	private final String title;

	ContactType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}