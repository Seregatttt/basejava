package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
	private final Link homePage;
	private final List<Period> periods = new ArrayList<>();

	public Organization(String name, String url, YearMonth startDate, YearMonth endDate, String title, String description) {
		Objects.requireNonNull(name, "name must not be null");
		Objects.requireNonNull(startDate, "startDate must not be null");
		Objects.requireNonNull(endDate, "endDate must not be null");
		Objects.requireNonNull(title, "title must not be null");
		this.homePage = new Link(name, url);
		this.periods.add(new Period(startDate, endDate, title, description));
	}

	public void addPeriod(YearMonth startDate, YearMonth endDate, String title, String description) {
		this.periods.add(new Period(startDate, endDate, title, description));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Organization that = (Organization) o;

		if (!homePage.equals(that.homePage)) return false;
		return periods.equals(that.periods);
	}

	@Override
	public int hashCode() {
		int result = homePage.hashCode();
		result = 31 * result + periods.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "\n Organization{" +
				"homePage=" + homePage +
				", periods=" + periods +
				'}';
	}
}
