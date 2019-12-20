package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListStringSection extends Section {
	private List<String> list = new ArrayList<>();

	public void save(String text) {
		list.add(text);
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
