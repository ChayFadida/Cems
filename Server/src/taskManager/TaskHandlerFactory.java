package taskManager;

import java.util.HashMap;


public class TaskHandlerFactory {
	@SuppressWarnings("unchecked")
	private static HashMap taskHandler = new HashMap<>() {{
		taskHandler.put("HOD", new HODTaskManager());
		taskHandler.put("Teacher", new TeacherTaskManager());
		
	}};
	
	public static HashMap getTaskHadler() {
		return taskHandler;
	}
}
