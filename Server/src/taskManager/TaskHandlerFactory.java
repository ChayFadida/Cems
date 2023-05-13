package taskManager;

import java.util.HashMap;


public class TaskHandlerFactory {
	public static HashMap<String,TaskHandler> taskHandler = new HashMap<>() {{
		taskHandler.put("HOD", new HODTaskManager());
		taskHandler.put("Lecturer", new LecturerTaskManager());
		taskHandler.put("Student", new StudentTaskManager());
	}};
	public TaskHandlerFactory() {}
	public static HashMap<String,TaskHandler> getTaskHadler() {
		return taskHandler;
	}
}
