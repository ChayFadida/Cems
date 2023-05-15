package DataBase;

import java.util.ArrayList;
import java.util.HashMap;

public class SqlQueries {
	public static String getAllTable(String table) {
		StringBuilder getAllTable = new StringBuilder();
		String query = "SELECT * FROM ";
		getAllTable.append(query);
		getAllTable.append(table + ';');
		return getAllTable.toString();
	}
	public static String updateQuestionById(ArrayList<String> param) {
		String query= "UPDATE questions SET question_number = '"
				+ param.get(1)+"', question = '"+ param.get(2)+"' WHERE id ="+param.get(0);
		return query;
	}
}
