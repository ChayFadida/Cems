package thirdPart;
import java.util.HashMap;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class jsonHandler {
 
    public static <T> HashMap<String, T> convertJsonToArrayHashMap(String jsonString, Type valueType) {
        Gson gson = new Gson();
        Type hashMapArrayType = new TypeToken<HashMap<String, T>>() {}.getType();
        if (jsonString == null || jsonString.isEmpty()) {
            return new HashMap<>();
        }
        try {
            return gson.fromJson(jsonString, hashMapArrayType);
        } catch (JsonSyntaxException e) {
            return new HashMap<>();
        }
    }
    
    public static <T> String convertArrayHashMapToJsonString(HashMap<String, T> hashMap, Type valueType) {
        Gson gson = new Gson();
        Type hashMapArrayType = new TypeToken<HashMap<String, T>>() {}.getType();
        return gson.toJson(hashMap, hashMapArrayType);
    }
    
    

}