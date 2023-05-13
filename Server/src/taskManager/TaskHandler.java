package taskManager;

import java.sql.ResultSet;


import ocsf.server.ConnectionToClient;

public interface TaskHandler {
	public ResultSet executeUserCommand(Object msg);
}
