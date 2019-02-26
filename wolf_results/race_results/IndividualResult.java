package edu.ncsu.csc216.wolf_results.race_results;

import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.wolf_results.util.RaceTime;

/**
 * Represents an individual result. This class is a container class for the
 * information of Individual Results. This class is also an observer in the
 * Observer pattern
 * 
 * @author Shawn George
 * @author Justin Easow
 *
 */
public class IndividualResult implements Comparable<IndividualResult>, Observer {
	/** Name of Individual Result */
	private String name;
	/** Age of Individual Result */
	private int age;
	/** Pace of an individual result */
	private RaceTime pace;
	/** Time of an individual result */
	private RaceTime time;
	/** Race of individual result */
	private Race race;

	/**
	 * Constructs an IndividualResult given the parameters. Throws an
	 * IllegalArgumentException if the name, age, or time are invalid
	 * 
	 * @param race race -The Race component of an individuals results, of type Race
	 * @param name name -The name of the racer, of type String
	 * @param age  age -The age of the Racer, of type Integer
	 * @param time time -The individuals time, of time RaceTime
	 */
	public IndividualResult(Race race, String name, int age, RaceTime time) {
		if (race == null)
			throw new IllegalArgumentException("Race is Illegal");
		if (name == null || name.equals("") || name.trim().length() == 0)
			throw new IllegalArgumentException("Name is Illegal");
		if (age < 0)
			throw new IllegalArgumentException("Age is Illegal");
		if (time == null)
			throw new IllegalArgumentException("Time is Illegal");
		this.race = race;
		race.addObserver(this);
		this.name = name;
		this.age = age;
		this.time = time;
		updatePace();
	}

	/**
	 * A getter method that returns an individual race
	 * 
	 * @return an individual race
	 */
	public Race getRace() {
		return race;
	}

	/**
	 * A getter method, that gets the name of Individual Result
	 * 
	 * @return individual result
	 */
	public String getName() {
		return name.trim();
	}

	/**
	 * Gets the age of individual result
	 * 
	 * @return age of individual result
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Gets the time of Individual Result
	 * 
	 * @return time of individual result
	 */
	public RaceTime getTime() {
		return time;
	}

	/**
	 * Gets the pace of an Individual Result
	 * 
	 * @return pace of individual result
	 */
	public RaceTime getPace() {
		return pace;
	}

	/**
	 * Returns an Individual Result to a string.
	 * 
	 * @return String
	 */
	public String toString() {
		return ("IndividualResult [name=" + this.getName() + ", age=" + this.getAge() + ", time=" + this.getTime()
				+ ", pace=" + this.getPace() + "]");
	}

	/**
	 * The pace is updated if the Race that the IndividualResult is observing
	 * notified its observers of a change.
	 * 
	 * @param obs obs
	 * @param obj obj
	 */
	public void update(Observable obs, Object obj) {
		updatePace();

	}

	/**
	 * A helper method that updates the pace.
	 */
	private void updatePace() {
		double distance = this.race.getDistance();

		int timeSec = this.time.getTimeInSeconds();
		int raceTime = (timeSec / (int) distance);

		int timeHours = (int) raceTime / 3600;
		int timeMinutes = ((int) raceTime / 60) % 60;
		int timeSeconds = (raceTime % 60);
		this.pace = new RaceTime(timeHours, timeMinutes, timeSeconds);

	}

	/**
	 * Compares two IndividualResult objects based on their time
	 */
	@Override
	public int compareTo(IndividualResult o) {
		return time.compareTo(((IndividualResult) (o)).getTime());

	}

}
