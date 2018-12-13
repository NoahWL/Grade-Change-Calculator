package gradeChangeCalculator;

import java.util.ArrayList;
import java.util.List;

public class Course {

	private List<Grade> grades; // List of grades, sorted by date
	private double summativeWeight; // Weight of summative grades for this course. Default = 0.80
	private double formativeWeight; // Weight of formative grades for this course. Default = 0.20

	private boolean sorted; // To determine if the grades should be sorted before returning them
	private double isFinalCalculated; // To determine if the final grade should be re-calculated

	public Course() {
		this(0.80, 0.20);
	}

	public Course(double summativeWeight, double formativeWeight) {
		this.summativeWeight = summativeWeight;
		this.formativeWeight = formativeWeight;

		grades = new ArrayList<Grade>();
		sorted = false;

		isFinalCalculated = 0.0;
	}

	public List<Grade> getGrades() {
		return new ArrayList<Grade>(grades);
	}

	public void changeGrade(String assignment, Fraction score) {
		isFinalCalculated = 0.0;

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
		isFinalCalculated = 0.0;
	}

	public void changeGrade(Grade grade, Fraction score) { // TODO: Don't re-calculate final grade if updated score is same as original score
		int index = grades.indexOf(grade);
		if (index != -1)
			grades.get(index).setScore(score);
		else
			System.out.println("Couldn't find that grade in this course!");

		isFinalCalculated = 0.0;
	}

	public void removeGrade(Grade grade) {
		grades.remove(grade);

		isFinalCalculated = 0.0;
	}

	public void resetScores() {
		for (Grade g : grades) {
			g.resetScore();
		}

		isFinalCalculated = 0.0;
	}

	/*
	 * Sorts list of "grades" by date, from oldest to newest. To be called whenever
	 * "grades" is modified
	 */
	private void sortGrades() {
		if (sorted) // Do not sort if grades are already sorted to save CPUs
			return;

		List<Grade> unsorted = new ArrayList<Grade>(grades);
		List<Grade> sorted = new ArrayList<Grade>();

		for (int i = 0; i < unsorted.size(); i++) {

			Grade earliest = new Grade("13/33/9999999", null, null, null);
			for (Grade g : unsorted) {

				String dateEarly = earliest.getDate();
				int yEarly = Integer.parseInt(dateEarly.split("/")[2]);
				int mEarly = Integer.parseInt(dateEarly.split("/")[0]);
				int dEarly = Integer.parseInt(dateEarly.split("/")[1]);
				//System.out.println(yEarly + " " + mEarly + " " + dEarly);

				String dateNew = g.getDate();
				int yNew = Integer.parseInt(dateNew.split("/")[2]);
				int mNew = Integer.parseInt(dateNew.split("/")[0]);
				int dNew = Integer.parseInt(dateNew.split("/")[1]);
				//System.out.println(yNew + " " + mNew + " " + dNew);

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

	public Fraction getFinalGrade() {
		if (isFinalCalculated != 0.0)
			return new Fraction(isFinalCalculated);

		Fraction summativeGrade = new Fraction(0, 0);
		Fraction formativeGrade = new Fraction(0, 0);

		for (Grade g : grades) {
			if (g.getScore().getDecimal() < 0) {
				continue;
			} else if (g.getCategory().equalsIgnoreCase("Summative")) {
				summativeGrade = summativeGrade.add(g.getScore());
			} else if (g.getCategory().equalsIgnoreCase("Formative")) {
				formativeGrade = formativeGrade.add(g.getScore());
			}
		}

		isFinalCalculated = (formativeGrade.getDecimal() * formativeWeight) + (summativeGrade.getDecimal() * summativeWeight);
		return new Fraction(isFinalCalculated);
	}

	public String toString() {
		StringBuilder allGrades = new StringBuilder();

		for (Grade g : grades) {
			String gradeString = g.getDate() + " " + g.getCategory() + " " + g.getAssignment() + " " + g.getScore()
					+ " " + g.getScore().getRoundedPercentage(2) + "\n";
			allGrades.append(gradeString);
		}

		allGrades.append("\nFinal Grade: " + getFinalGrade().getRoundedPercentage(2) + "%");

		return allGrades.toString();
	}

	public int getAssignmentCount() {
		return grades.size();
	}
}
