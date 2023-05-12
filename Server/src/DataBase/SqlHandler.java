package DataBase;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class SqlHandler {
	private static HashMap<String, ArrayList<String>> queryMap = new HashMap<>() {{
		put("SELECT", null);
		put("FROM", null);
		put("WHERE", null);		
	}};		
	
	public static ArrayList getSelect() {
		return queryMap.get("SELECT");
	}
	
	public static ArrayList getFrom() {
		return queryMap.get("FROM");
	}
	
	public static ArrayList getWhere() {
		return queryMap.get("WHERE");
	}
	
	public SqlHandler getQuery(HashMap<String, ArrayList<String>> query_info) {
		for (String key : query_info.keySet())
			query_info.put(key, query_info.get(key));
		return this;
	}
}
