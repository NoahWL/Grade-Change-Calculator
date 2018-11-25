package gradeChangeCalculator;

import java.util.ArrayList;
import java.util.List;

public class Course {

	private List<Grade> grades; // List of grades, sorted by date
	private boolean sorted; // To determine if the grades should be sorted before returning them

	public Course() {
		 grades = new ArrayList<Grade>();
		 sorted = false;
	}
	
	public List<Grade> getGrades() {
		if (!sorted)
			sortGrades();
		return new ArrayList<Grade>(grades);
	}
	
	public void changeGrade(String assignment, Fraction score) {
		for (Grade g : grades) {
			if (g.getAssignment().equals(assignment)) {
				g.setScore(score);
				return;
			}
		}
		System.out.println("No assignment \"" + assignment + "\" found!");
	}
	
	public void addGrade(String date, String category, String assignment, Fraction score) {
		grades.add(new Grade(date, category, assignment, score));
		sorted = false;
	}
	
	public void changeGrade(Grade grade, Fraction score) {
		int index = grades.indexOf(grade);
		if (index != -1)
			grades.get(index).setScore(score);
		else
			System.out.println("Couldn't find that grade in this course!");
	}
	
	public void removeGrade(Grade grade) {
		grades.remove(grade);
	}
	
	public void resetScores() {
		for (Grade g : grades) {
			g.resetScore();
		}
	}

	private void sortGrades() { // Sorts list of "grades" by date, from oldest to newest. To be called whenever "grades" is modified
		List<Grade> unsorted = new ArrayList<Grade>(grades);
		List<Grade> sorted = new ArrayList<Grade>();

		for (int i = 0; i < unsorted.size(); i++) {
			
			Grade earliest = new Grade("13/33/9999999", null, null, null);
			for (Grade g : unsorted) {
				
				String dateEarly = earliest.getDate();
				int yEarly = Integer.parseInt(dateEarly.split("/")[2]);
				int mEarly = Integer.parseInt(dateEarly.split("/")[0]);
				int dEarly = Integer.parseInt(dateEarly.split("/")[1]);
				System.out.println(yEarly + " " + mEarly + " " + dEarly);

				String dateNew = g.getDate();
				int yNew = Integer.parseInt(dateNew.split("/")[2]);
				int mNew = Integer.parseInt(dateNew.split("/")[0]);
				int dNew = Integer.parseInt(dateNew.split("/")[1]);
				System.out.println(yNew + " " + mNew + " " + dNew);
				
				if (yNew < yEarly)
					earliest = g;
				else if (yNew == yEarly)
					if (mNew < mEarly)
						earliest = g;
					else if (mNew == mEarly)
						if (dNew < dEarly)
							earliest = g;
			}
			
			unsorted.remove(earliest);
			sorted.add(earliest);
		}
		
		this.grades = sorted;
		this.sorted = true;
	}
	
	public String toString() {
		StringBuilder allGrades = new StringBuilder();
		
		for (Grade g : grades) {
			String gradeString = g.getDate() + " " + g.getCategory() + " " + g.getAssignment() + " " + g.getScore() + " " + g.getScore().getRoundedPercentage(2) + "\n";
			allGrades.append(gradeString);
		}
		
		return allGrades.toString();
	}
}
