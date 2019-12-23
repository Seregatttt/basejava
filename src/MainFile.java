import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

		String[] list = dir.list();
		System.out.println(list);

		if (list != null) {
			for (String name : list) {
				System.out.println("name= " + name);
			}
		}

		filePath = "C:\\#JAVA\\basejava\\src";
		printDir(filePath);
	}

	public static void printDir(String filePath) {
		int count = filePath.length() - filePath.replace("\\", "").length();
		int indent = 0;
		File dir = new File(filePath);
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				indent = 2;//отступ для файлов в дереве каталогов
			}
			System.out.println(padLeft(file.getName(), (count + indent) * 5));
			if (file.isDirectory()) {
				printDir(file.getAbsolutePath());
			}
		}
	}

	public static String padLeft(String s, int n) {
		return String.format("%" + n + "s", s);
	}
}