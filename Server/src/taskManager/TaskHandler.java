package taskManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;

public interface TaskHandler {
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg);
}
