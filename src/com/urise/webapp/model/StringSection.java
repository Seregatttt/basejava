package com.urise.webapp.model;

import java.util.Objects;

public class StringSection extends AbstractSection {
	private final String content;

	public String getContent() {
		return content;
	}

	public StringSection(String content) {
		Objects.requireNonNull(content, "content must not be null");
		this.content = content;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StringSection that = (StringSection) o;

		return content != null ? content.equals(that.content) : that.content == null;
	}

	@Override
	public int hashCode() {
		return content != null ? content.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "\n StringSection{" +
				"content='" + content + '\'' +
				'}' + "\n";
	}
}
