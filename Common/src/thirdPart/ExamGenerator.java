package thirdPart;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import entities.*;

public class ExamGenerator {

    private final int maxQuestionsPerPage = 4;
	/**
	 * Generates an exam document based on the provided questions and saves it to the 
	 * *
	 * @param questions the list of questions for the exam
	 *
	 * @param filePath the file path to save the exam document
	 * @param courseId the ID of the course for the exam
	 * @param duration the duration of the exam in minutes
	
	 */
    @SuppressWarnings("resource")
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
                    emptyParagraphAfterPage.setSpacingAfter(0); // Adjust spacing as needed
                }

                for (int i = 0; i < maxQuestionsPerPage; i++) {
                    if (questionIndex >= questions.size()) {
                        break;  // No more questions, exit the loop
                    }

                    Question question = (Question) questions.get(questionIndex);

                    XWPFParagraph questionParagraph = document.createParagraph();
                    questionParagraph.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun questionRun = questionParagraph.createRun();
                    questionRun.setBold(true);
                    questionRun.setText("Question " + (questionIndex + 1) + ":");
                    XWPFRun questionRun1 = questionParagraph.createRun();
                    questionRun1.setText(question.getDetails());

                    HashMap<String, String> choices =  JsonHandler.convertJsonToHashMap(question.getAnswers(), String.class, String.class);
                    List<String> sortedKeys = new ArrayList<>(choices.keySet());
                    Collections.sort(sortedKeys);
                    
                    for (Object key : sortedKeys) {
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
}







