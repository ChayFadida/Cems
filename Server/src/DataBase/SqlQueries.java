package DataBase;

import java.util.ArrayList;
import java.util.HashMap;

public class SqlQueries {
	public static StringBuilder getAllTable(String table) {
		StringBuilder getAllTable = new StringBuilder();
		String query = "SELECT * FROM ";
		getAllTable.append(query);
		getAllTable.append(table + ';');
		return getAllTable;
	}
}
