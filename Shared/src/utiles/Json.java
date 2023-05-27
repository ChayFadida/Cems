package utiles;

import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashMap;
import org.json.JSONObject;

public class Json {
	
	public class JsonToHashMapConverter {

	    public static HashMap<String, Object> jsonToHashMap(String jsonString) {
	        Gson gson = new Gson();
	        Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
	        return gson.fromJson(jsonString, type);
	    }

	    // Example usage
	    public static void main(String[] args) {
	        String jsonString = "{\"key1\":\"value1\",\"key2\":2,\"key3\":{\"nestedKey\":\"nestedValue\"}}";
	        HashMap<String, Object> hashMap = jsonToHashMap(jsonString);

	        // Print the HashMap contents
	        for (String key : hashMap.keySet()) {
	            System.out.println(key + ": " + hashMap.get(key));
	        }
	    }
	}

}
