package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListStringSection extends AbstractSection {
	private static final long serialVersionUID = 1L;
	private final List<String> list;

	public void save(String text) {
		list.add(text);
	}

	public ListStringSection(List<String> list) {
		Objects.requireNonNull(list, "list must not be null");
		this.list = list;
	}

	public List<String> getList() {
		return list;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ListStringSection that = (ListStringSection) o;

		return list.equals(that.list);
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public String toString() {
		return "\n ListStringSection{" +
				"list=" + list +
				'}' + "\n";
	}
}
