package gradeChangeCalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GradeChangeCalculator {

	private double summativePercentage; // Default = 0.80
	private double formativePercentage; // Default = 0.20
	private File gradeFile;
	private Course course;

	public GradeChangeCalculator(File grades) {
		this(80, 20, grades);
	}

	public GradeChangeCalculator(int summativePercentage, int formativePercentage, File grades) {
		this.summativePercentage = summativePercentage / 100.0;
		this.formativePercentage = formativePercentage / 100.0;
		gradeFile = grades;

		readGrades();
	}

	private void readGrades() {
		Course course = new Course();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(gradeFile));

			String line = reader.readLine();
			while (line != null) {
				line = line.replaceAll("\t", "_");
				// System.out.println("\n" + line);
				String date = line.substring(0, 10);
				String category = line.substring(11, 20);
				String assignment = line.substring(33, line.indexOf("_", 33)).trim();
				String scoreString = line.substring(line.indexOf("excluded from final grade") + 26,
						line.indexOf("_", line.indexOf("excluded from final grade") + 26)).trim();
				// System.out.println(date + " " + category + " " + assignment + " " +
				// scoreString);
				Fraction score;
				if (scoreString.contains("-"))
					score = new Fraction(-1, Double.parseDouble(scoreString.split("/")[1]));
				else
					score = new Fraction(Double.parseDouble(scoreString.split("/")[0]), Double.parseDouble(scoreString.split("/")[1]));

				course.addGrade(date, category, assignment, score);

				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.course = course;
	}

	public Course getCourse() {
		return course;
	}

	public String getGradesAsText() {
		return this.course.toString();
	}

}
