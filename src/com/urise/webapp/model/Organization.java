package com.urise.webapp.model;

import java.time.YearMonth;

public class Organization {
	private String name;
	private YearMonth startDate;
	private YearMonth endDate;
	private String title;
	private String description;

	public Organization(String name, YearMonth startDate, YearMonth endDate, String title, String description) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Organization{" +
				"name='" + name + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
