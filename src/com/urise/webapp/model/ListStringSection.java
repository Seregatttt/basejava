package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListStringSection extends Section {
	private List<String> list = new ArrayList<>();

	public void save(String text) {
		list.add(text);
	}

	@Override
	public String toString() {
		return "\n ListStringSection{" +
				"list=" + list +
				'}' + "\n";
	}
}
