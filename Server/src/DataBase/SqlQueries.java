package DataBase;

import java.util.ArrayList;
import java.util.HashMap;

public class SqlQueries {
	public static HashMap<String, ArrayList<String>> getAllUsers() {
		HashMap<String, ArrayList<String>> queryMap = new HashMap<>();
		ArrayList arrSelect = new ArrayList();
		arrSelect.add("*");
		queryMap.put("SELECT", arrSelect);
		ArrayList arrFrom = new ArrayList();
		arrFrom.add("Student");
		queryMap.put("SELECT", arrSelect);
		return queryMap;
	}
}
