package comp3111.coursescraper;

import java.util.ArrayList;
import java.util.List;


public class Instructor {
	private String name;
	private List<Double> scores = new ArrayList<Double>();

	/**
	 * Default Constructor
	 * @param name name of the instructor
	 */
	public Instructor(String name) {
		this.name = name;
	}
	
	/**
	 * Set instructor name
	 * @param name name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get instructor name
	 * @return name of the instructor
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Add SFQ score entry of instructor
	 * @param score score to add
	 */
	public void addScore(double score) {
		scores.add(score);
	}
	
	/**
	 * Get the simple average of the (unadjusted) SFQ score of instructor over courses they teach
	 * @return Average SFQ score
	 */
	public double getAverage() {
		double sum = 0d;
		for(Double score: scores)
			sum += score;
		int length = scores.size();
		return sum / length;
	}
}
