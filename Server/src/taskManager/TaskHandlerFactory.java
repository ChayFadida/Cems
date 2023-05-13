package taskManager;

import java.util.HashMap;


public class TaskHandlerFactory {
	private static HashMap<String,TaskHandler> taskHandler = new HashMap<>() {{
		taskHandler.put("HOD", new HODTaskManager());
		taskHandler.put("Teacher", new TeacherTaskManager());
		taskHandler.put("Student", new StudentTaskManager());
	}};
	
	public static HashMap<String,TaskHandler> getTaskHadler() {
		return taskHandler;
	}
}
