package entities;

import java.util.HashMap;

public class QuestionBank {
	private String name;
	private int lecturerId;
	HashMap<String,Integer> questions;
	/**
	 *QuestionBank constructor
	 *@param name,lecturerId.
	 *
	 * */
	public QuestionBank(String name, int lecturerId) {
		this.name = name;
		this.lecturerId = lecturerId;
	}
	/**
	 *QuestionBank constructor
	 *@param name,lecturerId,Questions.
	 *
	 * */
	public QuestionBank(String name, int lecturerId, HashMap<String, Integer> questions) {
		this.name = name;
		this.lecturerId = lecturerId;
		this.questions = questions;
	}
	/**
	 *name getter
	 *@return return the name of QuestionBank
	 * */
	public String getName() {
		return name;
	}
	/**
	 *name setter
	 *@param name
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 *lecturerId getter
	 *@return return the lecturerId of QuestionBank
	 * */
	public int getLecturerId() {
		return lecturerId;
	}
	/**
	 *lecturerId setter
	 *@param lecturerId
	 * */
	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
	}
	/**
	 *Questions getter
	 *@return return the questions of QuestionBank
	 * */
	public HashMap<String, Integer> getQuestions() {
		return questions;
	}
	/**
	 *Questions setter
	 *@param questions hashmap
	 * */
	public void setQuestions(HashMap<String, Integer> questions) {
		this.questions = questions;
	}
	/**
	 *Question String getter
	 *@return return a formated string of an QuestionBank
	 * */
	@Override
	public String toString() {
		return "QuestionBank [name=" + name + ", lecturerId=" + lecturerId + "]";
	}
	
}
