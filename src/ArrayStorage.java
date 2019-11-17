import java.util.Arrays;

/**
 * Array based storage for Resumes
 *
 * varning comment
 */
public class ArrayStorage {
	private Resume[] storage = new Resume[10000];
	private int indexStorage;

	void clear() {
		Arrays.fill(storage, null);
		indexStorage = 0;
	}

	public void save(Resume r) {
		storage[indexStorage] = r;
		indexStorage++;
	}

	public Resume get(String uuid) {
		for (int i = 0; i < indexStorage; i++) {
			if (storage[i].toString().equals(uuid)) {
				return storage[i];
			}
		}
		return null;
	}

	void delete(String uuid) {
		boolean checkDelete = false;
		for (int i = 0; i < indexStorage; i++) {
			if (storage[i].toString().equals(uuid)) {
				storage[i].uuid = null;
				checkDelete = true;
			}
			if (checkDelete) {
				if (i + 1 < indexStorage) {
					storage[i].uuid = storage[i + 1].uuid;
				}
			}
		}
		if (checkDelete) {
			indexStorage--;
		}
	}

	/**
	 * @return array, contains only Resumes in storage (without null)
	 */

	public Resume[] getAll() {
		return Arrays.copyOf(storage, indexStorage);
	}

	public int size() {
		return indexStorage;
	}

	public void list() {
		for (int i = 0; i < indexStorage; i++) {
			System.out.println(storage[i].toString());
		}
	}


}
