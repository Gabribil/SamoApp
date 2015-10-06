package nb.samoa.gestion_pedidos.model.web_distributed;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONSerializator {
	
	private static String getGetterName(String fieldName){
		return "get"+Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1);
	}
	
	public static JSONObject serializeToJSON(Object objectToSerialize) {
		JSONObject toRet = new JSONObject();

		for(Method m:objectToSerialize.getClass().getDeclaredMethods()){
			if(m.getAnnotation(JSONSerializableMember.class)!=null){
				try {
					m.setAccessible(true);
					String propertyName = Character.toLowerCase(m.getName().charAt(3))+m.getName().substring(4);
					toRet.put(propertyName, m.invoke(objectToSerialize));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for(Field f:objectToSerialize.getClass().getDeclaredFields()){
			if(f.getAnnotation(JSONSerializableMember.class)!=null){

				try {
					f.setAccessible(true);
					toRet.put(f.getName(), f.get(objectToSerialize));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException | IllegalAccessException e3) {
					// Cannot access the anotated field, trying to find the getter
					try {
						Method m = objectToSerialize.getClass().getDeclaredMethod(getGetterName(f.getName()));
						m.setAccessible(true);
						toRet.put(f.getName(), m.invoke(objectToSerialize));
					} catch (InvocationTargetException | IllegalAccessException | SecurityException | NoSuchMethodException e) {
						// Cannot access the annotated field, and there is no getter
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
		

		return toRet;
	}	

	@Target({FIELD,METHOD})
	@Retention(RUNTIME)
	public @interface JSONSerializableMember{}
}
