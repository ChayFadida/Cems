package taskManager;

import java.sql.ResultSet;


import ocsf.server.ConnectionToClient;

public interface TaskHandler<V, K> {
	public ResultSet executeUserCommand(Object msg, ConnectionToClient client);
}
