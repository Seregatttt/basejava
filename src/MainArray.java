import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | clear | update | exit): ");
			String[] params = reader.readLine().trim().toLowerCase().split(" ");
			if (params.length < 1 || params.length > 2) {
				System.out.println("Неверная команда.");
				continue;
			}
			String uuid = "?";
			if (params.length == 2) {
				uuid = params[1].intern();
			}
			switch (params[0]) {
				case "update":
					r = new Resume(uuid);
					//r.setUuid(uuid);
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
					r = new Resume(uuid);
					//r.setUuid(uuid);
					ARRAY_STORAGE.save(r);
					printAll();
					break;
				case "delete":
					ARRAY_STORAGE.delete(uuid);
					printAll();
					break;
				case "get":
					if (ARRAY_STORAGE.get(uuid) == null) {
						System.out.println("uuid " + uuid + " not found");
					} else {
						System.out.println(ARRAY_STORAGE.get(uuid).toString());
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
		List all = ARRAY_STORAGE.getAllSorted();
		System.out.println("----------------------------");
		if (all.size() == 0) {
			System.out.println("Empty");
		} else {
			System.out.println(all);
		}
		System.out.println("----------------------------");
	}
}