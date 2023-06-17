package entities;

import java.io.Serializable;

import javafx.scene.control.TextField;

public class QuestionForExam extends Question implements Serializable{
    private transient TextField score;
    private transient TextField textField;

	public QuestionForExam(Question question,String score) {
		super(question.getQuestionID(), question.getDetails(), question.getRightAnswer(),
				question.getQuestionBank(), question.getSubject(), 
				question.getNotes(), question.getAnswers(),question.getCourses());
		this.score = new TextField(score);
	}
	
	public TextField getScore() {
		return score;
	}
	public void setScore(TextField score) {
		this.score = score;
	}
	
//    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
//        out.defaultWriteObject();
//        // Serialize the score text
//        String scoreText = score.getText();
//        out.writeObject(scoreText);
//    }
//
//    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
//        in.defaultReadObject();
//        // Deserialize the score text
//        String scoreText = (String) in.readObject();
//        score = new TextField(scoreText);
//    }
}

