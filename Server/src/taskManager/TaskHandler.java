package taskManager;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskHandler {
	public ArrayList<HashMap<String, Object>> executeUserCommand(Object msg);
}
