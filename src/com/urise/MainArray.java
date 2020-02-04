package com.urise;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage implementation
 * *
 * (just run, no need to understand)
 */
public class MainArray {
	private final static Storage ARRAY_STORAGE = new SortedArrayStorage();
	//private final static Storage ARRAY_STORAGE = new ArrayStorage();
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Resume r;
		
		while (true) {
			System.out.println("I am " + ARRAY_STORAGE.getClass());
			System.out.print("Введите одну из команд - (list | save fullName  | delete uuid | get uuid | clear | update uuid fullName | exit): ");
			String[] params = reader.readLine().trim().toLowerCase().split(" ");
			if (params.length < 1 || params.length > 2) {
				System.out.println("Неверная команда.");
				continue;
			}
			String param = "?";
			if (params.length > 1) {
				param = params[1].intern();
			}
			switch (params[0]) {
				case "update":
					r = new Resume(param, params[2]);
					ARRAY_STORAGE.update(r);
					printAll();
					break;
				case "list":
					printAll();
					break;
				case "size":
					System.out.println("size = " + ARRAY_STORAGE.size());
					break;
				case "save":
					r = new Resume(param);
					//r.setUuid(uuid);
					ARRAY_STORAGE.save(r);
					printAll();
					break;
				case "delete":
					ARRAY_STORAGE.delete(param);
					printAll();
					break;
				case "get":
					try {
						if (ARRAY_STORAGE.get(param) == null) {
							System.out.println("uuid " + param + " not found");
						} else {
							System.out.println(ARRAY_STORAGE.get(param).toString());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;
				case "clear":
					ARRAY_STORAGE.clear();
					printAll();
					break;
				case "exit":
					return;
				default:
					System.out.println("Неверная команда.");
					break;
			}
		}
	}
	
	private static void printAll() {
		List<Resume> all = ARRAY_STORAGE.getAllSorted();
		System.out.println("----------------------------");
		if (all.size() == 0) {
			System.out.println("Empty");
		} else {
			System.out.println(all);
		}
		System.out.println("----------------------------");
	}
}