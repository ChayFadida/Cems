package DataBase;

import java.util.ArrayList;
import java.util.HashMap;

public class SqlQueries {
	
	/** this method gets whole table from db
	 *@param  table  the table to execute the query from
	 *@return getAllTable string of the desired query
	 * */
	public static String getAllTable(String table) {
		StringBuilder getAllTable = new StringBuilder();
		String query = "SELECT * FROM ";
		getAllTable.append(query);
		getAllTable.append(table + ';');
		return getAllTable.toString();
	}
	
	/** this method update the question table by id
	 *@param param is arraylist = ["question_number", "question",
	 *								"id"]
	 *@return query updateQuestionById string of the desired query
	 * */
	public static String updateQuestionById(ArrayList<String> param) {
		String query= "UPDATE questions SET question_number = '"
				+ param.get(1)+"', question = '"+ param.get(2)+"' WHERE id ="+param.get(0);
		return query;
	}
}
