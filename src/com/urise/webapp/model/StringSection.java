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
	public String toString() {
		return "\n StringSection{" +
				"title='" + title + '\'' +
				'}' + "\n";
	}
}
