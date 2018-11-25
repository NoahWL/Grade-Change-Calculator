package gradeChangeCalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Grade {
	
	private String date; // Date grade was inserted (used as starting point/marker for reading grades from file).  Format is mm/dd/yy
	private String category; // Can be "Summative" or "Formative"
	private String assignment; // Name of assignment
	private Fraction score; // Score in fraction form
	private final Fraction originalScore; // Original score
	
	public Grade(String date, String category, String assignment, Fraction score) {
		this.date = date;
		this.category = category;
		this.assignment = assignment;
		this.originalScore = this.score = score;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getAssignment() {
		return assignment;
	}
	
	public Fraction getScore() {
		return score;
	}
	
	public void setScore(Fraction newScore) {
		score = newScore;
	}
	
	public void resetScore() {
		score = originalScore;
	}
}
