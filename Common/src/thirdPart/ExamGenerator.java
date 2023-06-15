package thirdPart;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.FileOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import entities.Question;
import thirdPart.JsonHandler;
import java.io.FileInputStream;

public class ExamGenerator {
	private PDDocument document = new PDDocument();
    private PDPage page = new PDPage(PDRectangle.A4);
	final int maxQuestionsPerPage = 4;

	public void generateExamPDF(ArrayList<Question> questions, String filePath, String courseId, String testName, String duration) {
	    try {
	        PDPageContentStream contentStream = new PDPageContentStream(document, page);
	        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
	        contentStream.beginText();
	        contentStream.newLineAtOffset(25, 700);
	        int totalPages = (int) Math.ceil((double) questions.size() / maxQuestionsPerPage);
	        int currentPage = 1;
	        int questionIndex = 0;
	        int remainingQuestions = questions.size();

	        // Set the test name as a headline on the first page
	        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
	        contentStream.showText(testName);
	        contentStream.newLineAtOffset(0, -20);

	        // Write the ID, Course Name, and Duration on the first page
	        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
	        contentStream.newLineAtOffset(0, -20);
	        contentStream.showText("Course: " + courseId);
	        contentStream.newLineAtOffset(0, -20);
	        contentStream.showText("Duration: " + duration);
	        contentStream.newLineAtOffset(0, -40);

	        while (remainingQuestions > 0) {
	            // Adjust the Y coordinate for each line
	            float y = 700;

	            // Write the questions for the current page
	            for (int i = 0; i < Math.min(maxQuestionsPerPage, remainingQuestions); i++) {
	                Question question = questions.get(questionIndex);

	                contentStream.newLineAtOffset(0, -60); // Move to the next line
	                contentStream.showText("Question " + (questionIndex + 1) + ":");
	                contentStream.newLineAtOffset(10, 0); // Indent for the choices

	                HashMap<Object, Object> choices = JsonHandler.convertJsonToHashMap(question.getAnswers(), String.class, String.class);

	                for (Object key : choices.keySet()) {
	                    contentStream.newLineAtOffset(0, -20); // Move to the next line
	                    contentStream.showText(key.toString() + ") " + choices.get(key));
	                }

	                contentStream.newLineAtOffset(-10, 0); // Reset the X coordinate to remove indent

	                questionIndex++;
	            }

	            remainingQuestions -= maxQuestionsPerPage;

	            // Move to the next page if there are more questions
	            if (remainingQuestions > 0) {
	                contentStream.endText();
	                contentStream.close();

	                document.addPage(page);
	                page = new PDPage(PDRectangle.A4);
	                contentStream = new PDPageContentStream(document, page);
	                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
	                contentStream.beginText();
	                contentStream.newLineAtOffset(25, 700);

	                currentPage++;
	            }
	        }

	        contentStream.endText();
	        contentStream.close();

	        document.addPage(page);
	        document.save(filePath);
	        document.close();

	        System.out.println("PDF generated successfully at: " + filePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	public void generateExamDoc(ArrayList<Question> questions, String filePath, String courseId, String testName, String duration) {
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

	        int totalPages = (int) Math.ceil((double) questions.size() / maxQuestionsPerPage);
	        int currentPage = 1;
	        int questionIndex = 0;
	        int remainingQuestions = questions.size();

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
	                Question question = questions.get(questionIndex);

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
	
	public static void fillIDFieldInPDF(String filePath, String id) {
	    try (PDDocument document = PDDocument.load(new File(filePath))) {
	        PDPage firstPage = document.getPage(0); // Get the first page of the document
	        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage, PDPageContentStream.AppendMode.APPEND, true, true);
	        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

	        // Set the position of the ID field on the first page
	        float x = 100;
	        float y = firstPage.getMediaBox().getHeight() - 100; // Adjusted y-coordinate

	        // Fill the ID field with the provided ID value
	        contentStream.beginText();
	        contentStream.newLineAtOffset(x, y);
	        contentStream.showText("ID: " + id);
	        contentStream.endText();

	        contentStream.close();

	        document.save(filePath); // Save the modified PDF
	        System.out.println("ID field filled successfully in the PDF: " + filePath);
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



    public static void main(String[] args) {
        Integer questionID = 1;
        String details = "What is the capital of France?";
        String rightAnswer = "Paris";
        Integer questionBank = 123;
        String subject = "Geography";
        String notes = "Some additional notes";
        String answers = "{\"A\": \"Paris\", \"B\": \"London\", \"C\": \"Berlin\"}";
        String courses = "Course 1, Course 2";

        Question question = new Question(questionID, details, rightAnswer, questionBank, subject, answers, notes, courses);

        Integer questionID2 = 2;
        String details2 = "What is the largest planet in our solar system?";
        String rightAnswer2 = "Jupiter";
        Integer questionBank2 = 456;
        String subject2 = "Astronomy";
        String notes2 = "Some additional notes";
        String answers2 = "{\"A\": \"Jupiter\", \"B\": \"Mars\", \"C\": \"Saturn\"}";
        String courses2 = "Course 3, Course 4";

        Question question2 = new Question(questionID2, details2, rightAnswer2, questionBank2, subject2, answers2, notes2, courses2);

        // Create a sample list of questions
        ArrayList<Question> exam = new ArrayList<>();
        exam.add(question);
        exam.add(question2);
        exam.add(question2);
        exam.add(question);
        exam.add(question2);
        exam.add(question2);
        exam.add(question);
        exam.add(question2);
        exam.add(question2);
        exam.add(question);
        exam.add(question2);
        exam.add(question2);
        exam.add(question);
        exam.add(question2);
        exam.add(question2);
        exam.add(question);
        exam.add(question2);
        exam.add(question2);
        exam.add(question);
        exam.add(question2);
        exam.add(question2);

        String filePath = "/Users/chayfadida/Projects/testForCems/exam6.doc";

        ExamGenerator generator = new ExamGenerator();
        //generator.generateExamPDF(exam, filePath, "1234", "Math",  "3 hours");
        generator.generateExamDoc(exam, filePath, "1234", "Math",  "3 hours");
        System.out.println("Exam PDF generated successfully.");
        fillIDFieldInWordDoc(filePath, "318302783");
    }
}








