package thirdPart;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import entities.Question;
import thirdPart.JsonHandler;
import java.io.FileInputStream;

public class ExamGenerator {

	final int maxQuestionsPerPage = 4;	
	public void generateExamDoc(ArrayList<Object> arrayList2, String filePath, String courseId, String testName, String duration) {
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
	        courseDurationRun.setText("Course: " + courseId + " | Duration: " + duration);

	        int totalPages = (int) Math.ceil((double) arrayList2.size() / maxQuestionsPerPage);
	        int currentPage = 1;
	        int questionIndex = 0;
	        int remainingQuestions = arrayList2.size();

	        while (remainingQuestions > 0) {
	            XWPFParagraph pageHeaderParagraph = document.createParagraph();
	            pageHeaderParagraph.setAlignment(ParagraphAlignment.LEFT);
	            XWPFRun pageHeaderRun = pageHeaderParagraph.createRun();
	            pageHeaderRun.setBold(true);
	            pageHeaderRun.setFontSize(12);
	            pageHeaderRun.setText("Page " + currentPage);
	            XWPFParagraph emptyParagraphAfterPage = document.createParagraph();
	            emptyParagraphAfterPage.setSpacingAfter(0); // Adjust spacing as needed

	            for (int i = 0; i < Math.min(maxQuestionsPerPage, remainingQuestions); i++) {
	                Question question = (Question) arrayList2.get(questionIndex);

	                XWPFParagraph questionParagraph = document.createParagraph();
	                questionParagraph.setAlignment(ParagraphAlignment.LEFT);
	                XWPFRun questionRun = questionParagraph.createRun();
	                questionRun.setBold(true);
	                questionRun.setText("Question " + (questionIndex + 1) + ":");

	                HashMap<Object, Object> choices = JsonHandler.convertJsonToHashMap(question.getAnswers(), String.class, String.class);

	                for (Object key : choices.keySet()) {
	                    XWPFParagraph choiceParagraph = document.createParagraph();
	                    choiceParagraph.setAlignment(ParagraphAlignment.LEFT);
	                    XWPFRun choiceRun = choiceParagraph.createRun();
	                    choiceRun.setText("   " + key.toString() + ") " + choices.get(key));
	                }

	                // Add empty paragraphs for spacing
	                for (int j = 0; j < 5; j++) {
	                    XWPFParagraph emptyParagraph = document.createParagraph();
	                    emptyParagraph.setSpacingAfter(0); // Adjust spacing as needed
	                }

	                questionIndex++;
	            }


	            remainingQuestions -= maxQuestionsPerPage;

	            // Add a page break if there are more questions
	            if (remainingQuestions > 0) {
	                document.createParagraph().setPageBreak(true);
	                currentPage++;
	            }
	        }

	        FileOutputStream out = new FileOutputStream(new File(filePath));
	        document.write(out);
	        out.close();

	        System.out.println("Word document generated successfully at: " + filePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void fillIDFieldInWordDoc(String filePath, String id) {
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
}







