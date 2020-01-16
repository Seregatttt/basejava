package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
	private static final long serialVersionUID = 1L;
	private String content;
	
	public TextSection() {
	}
	
	public TextSection(String content) {
		Objects.requireNonNull(content, "content must not be null");
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getTextSection() {
		return content;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		TextSection that = (TextSection) o;
		
		return content != null ? content.equals(that.content) : that.content == null;
	}
	
	@Override
	public int hashCode() {
		return content != null ? content.hashCode() : 0;
	}
	
	@Override
	public String toString() {
		return "\n TextSection{" +
				"content='" + content + '\'' +
				'}' + "\n";
	}
}
