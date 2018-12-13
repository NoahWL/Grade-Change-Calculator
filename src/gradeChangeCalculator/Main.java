package gradeChangeCalculator;

import java.io.File;

public class Main {
	public static void main(String args[]) {
		GradeChangeCalculator gcc = new GradeChangeCalculator(new File("C:\\Users\\Noah\\Desktop\\grades.txt"));
		UI ui = new UI(gcc);
		
		System.out.println(gcc.getGradesAsText());
	}
}
