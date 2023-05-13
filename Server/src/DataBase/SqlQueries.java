package DataBase;

import java.util.ArrayList;
import java.util.HashMap;

public class SqlQueries {
	public static HashMap<String, ArrayList<String>> getAllUsers() {
		HashMap<String, ArrayList<String>> queryMap = new HashMap<>();
		ArrayList<String> arrSelect = new ArrayList<>();
		arrSelect.add("*");
		queryMap.put("SELECT", arrSelect);
		ArrayList<String> arrFrom = new ArrayList<>();
		arrFrom.add("Student");
		queryMap.put("SELECT", arrFrom);
		return queryMap;
	}
	
	public static HashMap<String, ArrayList<String>> getAllQuestions() {
		HashMap<String, ArrayList<String>> queryMap = new HashMap<>();
		ArrayList<String> arrSelect = new ArrayList<>();
		arrSelect.add("*");
		queryMap.put("SELECT", arrSelect);
		ArrayList<String> arrFrom = new ArrayList<>();
		arrFrom.add("questions");
		queryMap.put("FROM", arrFrom);
		return queryMap;
	}
}
