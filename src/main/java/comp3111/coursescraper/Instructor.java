package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;


public class Instructor {
	private String name;
	private List<Double> scores = new ArrayList<Double>();

	public Instructor(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addScore(double score) {
		scores.add(score);
	}
	
	public double getAverage() {
		double sum = 0d;
		for(Double score: scores)
			sum += score;
		int length = scores.size();
		return sum / length;
	}
}
