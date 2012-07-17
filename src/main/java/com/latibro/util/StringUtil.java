package com.latibro.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class StringUtil {

	public static String objectToString(Object obj) {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		if (obj == null) {
			return null;
		}

		result.append( obj.getClass().getName() );
		result.append( " Object {" );
		result.append(newLine);

		//determine fields declared in this class only (no fields of superclass)
		Field[] fields = obj.getClass().getDeclaredFields();

		//print field names paired with their values
		for ( Field field : fields  ) {
			field.setAccessible(true);

			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			
			result.append("  ");
			try {
				result.append( field.getName() );
				result.append(": ");
				//requires access to private field:
				result.append( field.get(obj) );
			} catch ( IllegalAccessException ex ) {
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}

}
