package edu.ncsu.csc216.wolf_results.race_results;

import java.time.LocalDate;
import java.util.Observable;

import edu.ncsu.csc216.wolf_results.util.RaceTime;

/**
 * Race is a container class. It is an Observable class, in the Observer
 * pattern. The class itself represents a single Race
 * 
 * @author shawngeorge
 *
 */
public class Race extends Observable {
	/** Name of race */
	private String name;
	/** Distance of race */
	private double distance;
	/** Date of race */
	private LocalDate date;
	/** Location of race */
	private String location;
	/** The result of the Race */
	private RaceResultList results;

	/**
	 * Constructs a Race with the given parameters, and assigns the parameters
	 * 
	 * @param n
	 *            -The name of the Race, as a String
	 * @param distance
	 *            -The distance of a race, as a double
	 * @param date
	 *            -The date of the race, of type LocalDate
	 * @param l
	 *            -the location of the race, as a String
	 * 
	 */
	public Race(String n, double distance, LocalDate date, String l) {
		this(n, distance, date, l, new RaceResultList());
	}

	/**
	 * Constructs a Race with the given parameters. It also throws an exception if
	 * any of the parameters are invalid
	 * 
	 * @param n
	 *            -The name of the Race, as a String
	 * @param distance
	 *            -The distance of a race, as a double
	 * @param date
	 *            -The date of the race, of type LocalDate
	 * @param l
	 *            -the location of the race, as a String
	 * @param results
	 *            -The results of the Race, of type RaceResultList
	 */
	public Race(String n, double distance, LocalDate date, String l, RaceResultList results) {
		if (n == null)
			throw new IllegalArgumentException("Invalid input for result");
		if (l == null)
			throw new IllegalArgumentException("Invalid input for result");
		String raceName = n.trim();
		String raceLocation = l.trim();

		if (raceName == null || raceName.equals("")) {
			throw new IllegalArgumentException("Invalid input for result");
		}
		if (distance <= 0) {
			throw new IllegalArgumentException("Invalid input for result");
		}
		if (date == null) {
			throw new IllegalArgumentException("Invalid input for result");
		}
		if (raceLocation == null) {
			throw new IllegalArgumentException("Invalid input for result");
		}
		if (results == null) {
			throw new IllegalArgumentException("Invalid input for result");
		}

		this.name = raceName;
		this.distance = distance;
		this.date = date;
		this.location = raceLocation;
		this.results = results;
	}

	/**
	 * Returns name of race
	 * 
	 * @return name of race, of type String
	 */
	public String getName() {
		return name.trim();
	}

	/**
	 * Returns distance of race
	 * 
	 * @return distance of race
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Gets the date of race
	 * 
	 * @return date of race
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Returns location of race
	 * 
	 * @return location of race
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Returns results of race
	 * 
	 * @return result of race
	 */
	public RaceResultList getResults() {
		return results;
	}

	/**
	 * Adds a race result to the RaceResultList
	 * 
	 * @param result
	 *            result to be added
	 */
	public void addIndividualResult(IndividualResult result) {
		results.addResult(result);

		setChanged();
		notifyObservers(this);
	}

	/**
	 * Sets the distance of the Race
	 * 
	 * @param d
	 *            -The Distance that is to be set, as a double
	 */
	public void setDistance(double d) {
		if (d <= 0) {
			throw new IllegalArgumentException("Invalid Distance");
		}
		this.distance = d;
		setChanged();
		notifyObservers(this);

	}

	/**
	 * The hashcode method, that makes hashcode for name, distance, date, and
	 * location
	 * 
	 * @return the hashcode, as an integer
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * A method that determine if two objects are equal. Generated for name,
	 * distance, date, and location
	 * 
	 * @param obj
	 *            -the object that is being checked if it is equal
	 * @return true/false depending on whether the object is equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Race other = (Race) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * Overrided to string method for the Race class. The string representation of a
	 * Race
	 */
	@Override
	public String toString() {
		return name + " (" + distance + " miles) on " + date + " at " + location;
	}

	/**
	 * Filters the RaceResults based on the given parameters. It will return a
	 * RaceResult list is between minAge and maxAge, as well as the minPace and
	 * maxPace
	 * 
	 * @param minAge
	 *            -The minimum age to be filtered, as a Integer
	 * @param maxAge
	 *            -The maximum age to be filtered, as an Integer
	 * @param minPace
	 *            -The minimum pace to be filtered, as a String
	 * @param maxPace
	 *            -The maximum pace to be filtered, as a String
	 * @return The filtered RaceResults, of type RaceResultsList
	 */
	public RaceResultList filter(int minAge, int maxAge, String minPace, String maxPace) {
		RaceResultList filtered = new RaceResultList();
		RaceTime maxP = new RaceTime(maxPace);
		RaceTime minP = new RaceTime(minPace);

		for (int i = 0; i < results.size(); i++) {
			if (results.getResult(i).getAge() >= minAge && results.getResult(i).getAge() <= maxAge
					&& results.getResult(i).getPace().getTimeInSeconds() >= minP.getTimeInSeconds()
					&& results.getResult(i).getPace().getTimeInSeconds() <= maxP.getTimeInSeconds()) {
				filtered.addResult(results.getResult(i));
			}

		}
		return filtered;

	}

}
