package taskManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import ocsf.server.ConnectionToClient;

/**
 * Represents an TaskHandler interface that execute User Command.
 */
public interface TaskHandler {
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg);
}
