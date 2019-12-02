package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class TestArrayList {
	private static final Resume RESUME_1 = new Resume("uuid1");
	private static final Resume RESUME_2 = new Resume("uuid2");
	private static final Resume RESUME_3 = new Resume("uuid3");
	private static final Resume RESUME_4 = new Resume("uuid4");
	public static void main(String[] args) {

		ArrayList al = new ArrayList();
		//save
		al.add(RESUME_1);
		al.add(RESUME_2);
		al.add(RESUME_3);
		System.out.println(al);
		//al.add(2, new Resume("uuid555"));

		// getIndex
		int idx = al.indexOf(RESUME_1);
		System.out.println("idx="+idx);
		//getResume
		System.out.println("get="+al.get(1));
		//remove
		al.remove(2);
		System.out.println(al);
		//update
		al.set(0,RESUME_4);
		System.out.println(al);
		//clear
		al.clear();
		System.out.println(al);
	}
}