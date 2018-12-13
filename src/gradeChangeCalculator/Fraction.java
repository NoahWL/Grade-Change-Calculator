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
	
	public Fraction(double decimal) {
		this.numerator = decimal;
		this.denominator = 1;
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
	
	public double getNumerator() {
		return numerator;
	}
	
	public double getDenominator() {
		return denominator;
	}
	
	public String toString() {
		if (numerator >= 1)
			return (int) numerator + "/" + (int) denominator;
		else
			return numerator + "/" + (int) denominator;
	}
	
	public Fraction add(Fraction f) {
		double newNumerator = this.getNumerator() + f.getNumerator();
		double newDenominator = this.getDenominator() + f.getDenominator();
		return new Fraction(newNumerator, newDenominator);
	}
	
	public Fraction add(double numerator, double denominator) {
		double newNumerator = this.getNumerator() + numerator;
		double newDenominator = this.getDenominator() + denominator;
		return new Fraction(newNumerator, newDenominator);
	}
}
