package controllersTest;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import client.ConnectionServer;
import common.CreateNewExamManagerIF;
import controllersClient.ChooseProfileController;
import controllersClient.LogInController;
import controllersHod.HODmenuController;
import controllersLecturer.CreateNewExamController;
import controllersLecturer.LecturerMenuController;
import controllersStudent.StudentMenuController;
import controllersTest.ClientLogInTest.StubLogInManager;
import entities.Hod;
import entities.Lecturer;
import entities.QuestionForExam;
import entities.Student;
import entities.Super;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateNewExamTest {
	
    TextField code;
    TextField lecNotes;
    TextField studNotes;
    TextField name;
    TextField subject;
    TextField duration;
    ArrayList<Object> qSelected;
    ArrayList<QuestionForExam> qArr;
    CreateNewExamController createNewExamController;
    StubCreateNewExamManager stubCreateNewExamManager;
    
	private class StubCreateNewExamManager implements CreateNewExamManagerIF{

		@Override
		public String getSubject() {
			return subject.getText();
		}

		@Override
		public String getCode() {
			return code.getText();
		}

		@Override
		public String getDuration() {
			return duration.getText();
		}

		@Override
		public String getLecNotes() {
			return lecNotes.getText();
		}

		@Override
		public String getStudNotes() {
			return studNotes.getText();
		}

		@Override
		public String getName() {
			return name.getText();
		}

		@Override
		public void addAllSelectedToArray(ObservableList<QuestionForExam> selectedItems) {
			qSelected.addAll(qArr);
		}

		@Override
		public ArrayList<Object> getSelected() {
			return qSelected;
		}

		@Override
		public void initializeSelected() {
			qSelected = new ArrayList<>();
			
		}

		@Override
		public void createExam(String code, String duration, String lecNotes,
				String studNotes, String name, String subject) {
			return;
		}
	}
	
	@BeforeEach
	void setUp() throws Exception {
		stubCreateNewExamManager= new StubCreateNewExamManager();
		createNewExamController = new CreateNewExamController(stubCreateNewExamManager);
	}
	
	@Test
	void createExamSucsess() {  

	}

	@Test
	void scoreTooHightTest() {

	}
	
	@Test
	void scoreTooLowTest() {
	   
	}

	@Test
	void allFildesEmptyTest() {
	   
	}

	@Test
	void wrongExamCodeTest() {
	        
	}

	@Test
	void questionScoreIsZeroTest() { 

	}

	@Test
	void questionScoreIsNegativeTest() { 

	}

	@Test
	void NegativeDurationTest() {
	        
	}
	       
	@Test
	void durationNotNumberTest() {
	        
	}

	@Test
	void durationIsZeroTest() {
	        
	}

	@Test
	void someFiledsAreEmptyTest() {
	        
	}
	
}
