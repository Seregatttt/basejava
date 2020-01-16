package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
	private static final long serialVersionUID = 1L;
	private List<String> list;
	
	public ListSection() {
	}
	
	public ListSection(List<String> list) {
		Objects.requireNonNull(list, "list must not be null");
		this.list = list;
	}
	
	public List<String> getList() {
		return list;
	}
	
	public void save(String text) {
		list.add(text);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		ListSection that = (ListSection) o;
		
		return list.equals(that.list);
	}
	
	@Override
	public int hashCode() {
		return list.hashCode();
	}
	
	@Override
	public String toString() {
		return "\n ListSection{" +
				"list=" + list +
				'}' + "\n";
	}
}
