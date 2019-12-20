package com.urise.webapp.model;

public class StringSection extends Section {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public StringSection(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StringSection that = (StringSection) o;

		return title != null ? title.equals(that.title) : that.title == null;
	}

	@Override
	public int hashCode() {
		return title != null ? title.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "\n StringSection{" +
				"title='" + title + '\'' +
				'}' + "\n";
	}
}
