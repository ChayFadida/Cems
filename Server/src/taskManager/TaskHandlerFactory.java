package taskManager;

import java.util.HashMap;

import DataBase.DBController;


public class TaskHandlerFactory {
	private static TaskHandler ManagerHandler = new HODTaskManager();
	private static TaskHandler LecturerHandler = new LecturerTaskManager();
	private static TaskHandler StudentHandler = new StudentTaskManager();
	public static HashMap<String,TaskHandler> taskHandler = new HashMap<>();
	private static TaskHandlerFactory instance;
	
	public TaskHandlerFactory(){
		taskHandler.put("HOD", ManagerHandler);
		taskHandler.put("Lecturer", LecturerHandler);
		taskHandler.put("Student", StudentHandler);
	}
	
	public static HashMap<String,TaskHandler> getTaskHadler() {
		return taskHandler;
	}
	
	
	public static synchronized TaskHandlerFactory getInstance() {
		if (instance == null) {
			instance = new TaskHandlerFactory();
		}
		return instance;
	}
}
