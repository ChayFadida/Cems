package taskManager;

import java.sql.ResultSet;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;

public interface TaskHandler {
	public ArrayList executeUserCommand(Object msg);
}
