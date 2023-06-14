package thirdPart;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonHandler {
	private static HashMap<Class<?>, TypeToken<?>> class_token = new HashMap<>(){{
		put(String.class,  new TypeToken<ArrayList<String>>() {});
		put(Integer.class, new TypeToken<ArrayList<Integer>>() {});
		put(Double.class,  new TypeToken<ArrayList<Double>>(){});
	}};
	
	public static <K, V> HashMap<K, V> convertJsonToHashMap(String jsonString, Type keyType, Type valueType) {
	    Gson gson = new Gson();
	    Type hashMapType = TypeToken.getParameterized(HashMap.class, keyType, valueType).getType();
	    if (jsonString == null || jsonString.isEmpty()) {
	        return new HashMap<>();
	    }
	    try {
	    	return gson.fromJson(jsonString, hashMapType);
	    } catch (JsonSyntaxException e) {
	        return new HashMap<>();
	    }
	}

    public static <K, V> HashMap<K, V> convertJsonToHashMap(String jsonString, Type keyType, Type valueType, Type arrayType) {
        Gson gson = new Gson();
        Type hashMapType;


        hashMapType = TypeToken.getParameterized(HashMap.class, keyType, class_token.get(arrayType).getType()).getType();        
        if (jsonString == null || jsonString.isEmpty()) {
            return new HashMap<>();
        }
        try {
        	return gson.fromJson(jsonString, hashMapType);
        } catch (JsonSyntaxException e) {
            return new HashMap<>();
        }
    }

    public static <K, V> String convertHashMapToJson(HashMap<K, V> hashMap, Type keyType, Type valueType) {
        Gson gson = new Gson();
        Type hashMapType = TypeToken.getParameterized(HashMap.class, keyType, valueType).getType();
        return gson.toJson(hashMap, hashMapType);
    }

}