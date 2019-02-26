/**
 * 
 */
package edu.ncsu.csc216.wolf_results.util;

import java.util.Scanner;

/**
 * The RaceTime class, which represents the race time, and the components within
 * the time
 * 
 * @author Justin Easow
 * @author Shawn George
 *
 */
public class RaceTime {
	/** An integer representing the hours needed to complete a race */
	private int hours;
	/** An integer representing the minutes needed to complete a race */
	private int minutes;
	/** An integer representing the seconds needed to complete a race */
	private int seconds;

	/**
	 * The constructor for the RaceTime class that assigns the hours minutes and
	 * seconds
	 * 
	 * @param hours
	 *            -The hours to complete the race, as an Integer
	 * @param minutes
	 *            -The minutes required to complete a race, as an Integer
	 * @param seconds
	 *            -The seconds required to complete a race, as an Integer
	 * @throws IllegalArgumentException
	 *             -if any of the time components are invalid
	 */
	public RaceTime(int hours, int minutes, int seconds) {
		if (hours < 0)
			throw new IllegalArgumentException("Invalid Time");
		if (minutes < 0 || minutes > 59)
			throw new IllegalArgumentException("Invalid Time");
		if (seconds < 0 || seconds > 59)
			throw new IllegalArgumentException("Invalid Time");
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;

	}

	/**
	 * A constructor of RaceTime, with a parameter of type String
	 * 
	 * @param time
	 *            -The time of the racer, as a String
	 * @throws IllegalArgumentException
	 *             for invalid time format
	 */
	@SuppressWarnings("resource")
	public RaceTime(String time) {
		Scanner scanner = new Scanner(time);
		scanner.useDelimiter(":");
		try {
			hours = scanner.nextInt();
			minutes = scanner.nextInt();
			seconds = scanner.nextInt();
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid Time");
		}
		scanner.close();

		if (hours < 0)
			throw new IllegalArgumentException("Invalid Time");
		if (minutes < 0 || minutes > 59)
			throw new IllegalArgumentException("Invalid Time");
		if (seconds < 0 || seconds > 59)
			throw new IllegalArgumentException("Invalid Time");

	}

	/**
	 * A getter method, that returns the hour component of the race time
	 * 
	 * @return the hour component of the race time, as an integer
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * A getter method, that returns the minute component of the race time
	 * 
	 * @return the minute component of the race time, as an integer
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * A getter method, that returns the seconds component of the race time
	 * 
	 * @return the seconds component of the race time, as an integer
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * A method that returns the total time in seconds of the race
	 * 
	 * @return the total race time in seconds, as an integer
	 */
	public int getTimeInSeconds() {
		return ((hours * 3600) + (minutes * 60) + seconds);
	}

	/**
	 * Returns the race time in the correctly format of 'hh:mm:ss'
	 * 
	 * @return The correct format of the time, as a String
	 */
	public String toString() {
		String m = Integer.toString(minutes);
		String s = Integer.toString(seconds);
		if (m.length() == 1) {
			m = "0" + m;
		}
		if (s.length() == 1) {
			s = "0" + s;
		}

		return hours + ":" + m + ":" + s;
	}

	/**
	 * A method that compare race times, based on their total times
	 * 
	 * @param r
	 *            -The race time of a particular racer, of type RaceTime
	 * @return An integer of the comparison between two race times
	 */
	public int compareTo(RaceTime r) {
		if (this.getTimeInSeconds() > r.getTimeInSeconds()) {
			return 1;
		} else if (this.getTimeInSeconds() < r.getTimeInSeconds()) {
			return -1;
		}
		return 0;
	}

}
