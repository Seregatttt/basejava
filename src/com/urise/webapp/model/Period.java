package com.urise.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Period {
	private final YearMonth startDate;
	private final YearMonth endDate;
	private final String title;
	private final String description;

	public Period(YearMonth startDate, YearMonth endDate, String title, String description) {
		Objects.requireNonNull(startDate, "startDate must not be null");
		Objects.requireNonNull(endDate, "endDate must not be null");
		Objects.requireNonNull(title, "title must not be null");
		this.startDate = startDate;
		this.endDate = endDate;
		this.title = title;
		this.description = description;
	}

	public YearMonth getStartDate() {
		return startDate;
	}

	public YearMonth getEndDate() {
		return endDate;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Period period = (Period) o;

		if (!startDate.equals(period.startDate)) return false;
		if (!endDate.equals(period.endDate)) return false;
		if (!title.equals(period.title)) return false;
		return description != null ? description.equals(period.description) : period.description == null;
	}

	@Override
	public int hashCode() {
		int result = startDate.hashCode();
		result = 31 * result + endDate.hashCode();
		result = 31 * result + title.hashCode();
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "\n Period{" +
				"startDate=" + startDate +
				", endDate=" + endDate +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
