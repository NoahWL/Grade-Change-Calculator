package gradeChangeCalculator;

public class Fraction {
	private double numerator;
	private double denominator;
	
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public Fraction(double numerator, double denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public double getDecimal() {
		if (denominator == 0)
			return 0.0;
		else
			return numerator / denominator;
	}
	
	public double getPercentage() {
		return getDecimal() * 100;
	}
	
	public double getRoundedPercentage(int decimalPlaces) {
		if (decimalPlaces > 10)
			decimalPlaces = 10;
		double roundNum = Math.pow(10, decimalPlaces);
		return Math.round(getPercentage() * roundNum) / roundNum;
	}
	
	public String toString() {
		return (int) numerator + "/" + (int) denominator;
	}
}
