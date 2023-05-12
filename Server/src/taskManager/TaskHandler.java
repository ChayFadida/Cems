package taskManager;

import java.util.HashMap;

import ocsf.server.ConnectionToClient;

public interface TaskHandler<V, K> {
	public HashMap<V, K> executeUserCommand(Object msg, ConnectionToClient client);
}
