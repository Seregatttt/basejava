import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainFile {

	public static void main(String[] args) {
		String filePath = ".\\.gitignore";

		File file = new File(filePath);
		try {
			System.out.println(file.getCanonicalPath());
		} catch (IOException e) {
			throw new RuntimeException("Error", e);
		}

		try (FileInputStream fis = new FileInputStream(filePath)) {
			System.out.println("read " + fis.read());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		File dir = new File("./src");
		System.out.println(dir.isDirectory());
		System.out.println(dir.getAbsolutePath());

		String[] list = dir.list();
		System.out.println(Arrays.toString(list));

		if (list != null) {
			for (String name : list) {
				System.out.println("name= " + name);
			}
		}

		filePath = "./src";
		printDir(filePath);
	}

	private static void printDir(String filePath) {

		File dir = new File(filePath);
		int count = dir.getAbsolutePath().length() - dir.getAbsolutePath().replace("\\", "").length();
		int indent = 0;
		File[] files = dir.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					indent = 2;//отступ для файлов в дереве каталогов
				}
				System.out.println(padLeft(file.getName(), (count + indent) * 5));
				if (file.isDirectory()) {
					printDir(file.getAbsolutePath());
				}
			}
		} else {
			throw new RuntimeException("Error: files is null !!! ");
		}
	}

	private static String padLeft(String s, int n) {
		return String.format("%" + n + "s", s);
	}
}