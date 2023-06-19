package taskManager;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Represents an TaskHandler interface that execute User Command.
 */
public interface TaskHandler {
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg);
}
