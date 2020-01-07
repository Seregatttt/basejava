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
		
		printDir(dir, " ");
	}
	
	public static void printDir(File dir, String offset) {
		File[] files = dir.listFiles();
		
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					System.out.println(offset + "File: " + file.getName());
				} else if (file.isDirectory()) {
					System.out.println(offset + "Dir: " + file.getName());
					printDir(file, offset + "  ");
				}
			}
		}
	}
}