package taskManager;

import java.util.HashMap;

import DataBase.DBController;


public class TaskHandlerFactory {
	private static TaskHandler ManagerHandler = new HODTaskManager();
	private static TaskHandler LecturerHandler = new LecturerTaskManager();
	private static TaskHandler StudentHandler = new StudentTaskManager();
	private static TaskHandler UserHandler = new UserTaskManager();
	public static HashMap<String,TaskHandler> taskHandler = new HashMap<>();
	private static TaskHandlerFactory instance;
	
	
	/**
	 *constructor for the factory
	 *implement all the users kind and the instances
	 * */
	public TaskHandlerFactory(){
		taskHandler.put("HOD", ManagerHandler);
		taskHandler.put("Lecturer", LecturerHandler);
		taskHandler.put("Student", StudentHandler);
		taskHandler.put("User", UserHandler);

	}
	
	/**
	 *return the task handler dict
	 *@return taskHandler 
	 * */
	public static HashMap<String,TaskHandler> getTaskHandler() {
		return taskHandler;
	}
	
	
	/**
	 *singletone design pattern
	 *@return instance 
	 * */
	public static synchronized TaskHandlerFactory getInstance() {
		if (instance == null) {
			instance = new TaskHandlerFactory();
		}
		return instance;
	}
}
