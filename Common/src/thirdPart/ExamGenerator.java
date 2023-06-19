package thirdPart;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import entities.*;
import thirdPart.JsonHandler;
import java.io.FileInputStream;

public class ExamGenerator {
	/**
	 * Generates an exam document based on the provided questions and saves it to the 
	 * *
	 * @param questions the list of questions for the exam
	 *
	 * @param filePath the file path to save the exam document
	 * @param courseId the ID of the course for the exam
	 * @param duration the duration of the exam in minutes
	
	 */
    private final int maxQuestionsPerPage = 4;

    public void generateExamDoc(ArrayList<Object> questions, String filePath, String courseId, String testName, String duration) {
        try {
            XWPFDocument document = new XWPFDocument();

            XWPFParagraph testNameParagraph = document.createParagraph();
            testNameParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun testNameRun = testNameParagraph.createRun();
            testNameRun.setBold(true);
            testNameRun.setFontSize(16);
            testNameRun.setText(testName);

            XWPFParagraph courseDurationParagraph = document.createParagraph();
            courseDurationParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun courseDurationRun = courseDurationParagraph.createRun();
            courseDurationRun.setBold(true);
            courseDurationRun.setFontSize(12);
            courseDurationRun.setText("Course: " + courseId + " | Duration: " + duration + "M" + " | ID: _____ ");

            int totalPages = (int) Math.ceil((double) questions.size() / maxQuestionsPerPage);
            int currentPage = 1;
            int questionIndex = 0;

            while (questionIndex < questions.size()) {
                if (currentPage > 1) {
                    XWPFParagraph pageHeaderParagraph = document.createParagraph();
                    pageHeaderParagraph.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun pageHeaderRun = pageHeaderParagraph.createRun();
                    pageHeaderRun.setBold(true);
                    pageHeaderRun.setFontSize(12);
                    pageHeaderRun.setText("Page " + currentPage + " of " + totalPages);

                    XWPFParagraph emptyParagraphAfterPage = document.createParagraph();
                    emptyParagraphAfterPage.setSpacingAfter(0); 
                }

                for (int i = 0; i < maxQuestionsPerPage; i++) {
                    if (questionIndex >= questions.size()) {
                        break;  
                    }

                    Question question = (Question) questions.get(questionIndex);

                    XWPFParagraph questionParagraph = document.createParagraph();
                    questionParagraph.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun questionRun = questionParagraph.createRun();
                    questionRun.setBold(true);
                    questionRun.setText("Question " + (questionIndex + 1) + ":");
                    XWPFRun questionRun1 = questionParagraph.createRun();
                    questionRun.setText(question.getDetails());

                    HashMap<Object, Object> choices = JsonHandler.convertJsonToHashMap(question.getAnswers(), String.class, String.class);

                    for (Object key : choices.keySet()) {
                        XWPFParagraph choiceParagraph = document.createParagraph();
                        choiceParagraph.setAlignment(ParagraphAlignment.LEFT);
                        XWPFRun choiceRun = choiceParagraph.createRun();
                        choiceRun.setText("   " + key.toString() + ") " + choices.get(key));
                    }

           
                    for (int j = 0; j < 5; j++) {
                        XWPFParagraph emptyParagraph = document.createParagraph();
                        emptyParagraph.setSpacingAfter(0); 
                    }

                    questionIndex++;
                }

                if (questionIndex < questions.size()) {
                    XWPFParagraph pageBreakParagraph = document.createParagraph();
                    pageBreakParagraph.setPageBreak(true);
                }

                currentPage++;
            }

            FileOutputStream out = new FileOutputStream(new File(filePath));
            document.write(out);
            out.close();

            System.out.println("Word document generated successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
	 * Fills the ID field in a Word document located at the specified file path.
	 * *
	 * @param filePath the file path of the Word document
	 * @param id the ID to fill in the ID field
	 */
	public static void fillIDFieldInWordDoc(String filePath, int id) {
	    try {
	        XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));

	        // Get the second paragraph in the document (index 1)
	        XWPFParagraph courseDurationParagraph = document.getParagraphArray(1);

	        // Create a new run for the ID field
	        XWPFRun idRun = courseDurationParagraph.createRun();
	        idRun.setBold(true);
	        idRun.setFontSize(12);
	        idRun.setText(" | ID: " + id);

	        FileOutputStream out = new FileOutputStream(new File(filePath));
	        document.write(out);
	        out.close();

	        System.out.println("ID field filled successfully in the Word document: " + filePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	 /**
		 * Main method to demonstrate filling the ID field in a Word document.		 * *
		 * @param args the command line arguments	
		 */
	public static void main(String[] args) {
		ExamGenerator.fillIDFieldInWordDoc("/Users/chayfadida/Desktop/try1111.doc", 37);
	}

}







