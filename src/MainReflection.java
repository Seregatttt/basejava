
import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
	
	public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Resume r = new Resume("name");
		Field field = r.getClass().getDeclaredFields()[0];
		field.setAccessible(true);
		System.out.println(field.getName());
		System.out.println(field.get(r));
		field.set(r, "new_uuid");
		System.out.println(r);
		
		// TODO : invoke r.toString via reflection
		System.out.println("field.toString()= " + field.get(r).toString());
		
		Method methods = r.getClass().getMethod("toString");
		Object res = methods.invoke(r);
		System.out.println(res);
	}
}