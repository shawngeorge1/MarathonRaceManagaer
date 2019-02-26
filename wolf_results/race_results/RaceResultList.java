package edu.ncsu.csc216.wolf_results.race_results;

import edu.ncsu.csc216.wolf_results.util.RaceTime;
import edu.ncsu.csc216.wolf_results.util.SortedLinkedList;

/**
 * Contains an instance of SortedLinkedList that will hold IndividualResult
 * objects.
 * 
 * @author shawngeorge
 *
 */
public class RaceResultList {
	/** The Sorted Results from the race, of type SortedLinkedList */
	SortedLinkedList<IndividualResult> results;

	/**
	 * Constructs the SortedLinkedList
	 */
	public RaceResultList() {
		results = new SortedLinkedList<IndividualResult>();
	}

	/**
	 * Throws an IllegalArgumentException if result is null. Adds a result to the
	 * sorted liked list.
	 * 
	 * @param result result being added
	 */
	public void addResult(IndividualResult result) {
		if (result == null) {
			throw new IllegalArgumentException("Unable to add result");
		}
		results.add(result);
	}

	/**
	 * Constructs a IndividualResult using the provided parameters and throws
	 * IllegalArgumentException if IndividualResult constructor throws exception.
	 * Adds race to list
	 * 
	 * @param race A parameter of the race
	 * @param name The name, as a String
	 * @param age  The age of the runner, as an integer
	 * @param time The race time, of type RaceTime
	 */
	public void addResult(Race race, String name, int age, RaceTime time) {
		try {
			IndividualResult iv = new IndividualResult(race, name, age, time);
			results.add(iv);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets a Result from the list given an index.
	 * 
	 * @param index index of individual result
	 * @return individual result
	 */
	public IndividualResult getResult(int index) {
		if (index > results.size() || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return results.get(index);
	}

	/**
	 * Gets the size of RaceResultList.
	 * 
	 * @return size of race result list
	 */
	public int size() {
		return results.size();
	}

	/**
	 * Returns the Race result list in the form a 2D array.
	 * 
	 * @return 2D array of the results
	 */
	public String[][] getResultsAsArray() {

		String[][] array = new String[results.size()][4];
		for (int i = 0; i < results.size(); i++) {
			array[i][0] = results.get(i).getName();
			array[i][1] = "" + results.get(i).getAge();
			array[i][2] = results.get(i).getTime().toString();
			array[i][3] = results.get(i).getPace().toString();
		}

		return array;
	}

	/**
	 * Filters RaceResultList based on the given parameters.
	 * 
	 * @param minAge  -The minimum age to be filtered, as a Integer
	 * @param maxAge  -The maximum age to be filtered, as an Integer
	 * @param minPace -The minimum pace to be filtered, as a String
	 * @param maxPace -The maximum pace to be filtered, as a String
	 * 
	 * @return The filtered RaceResults, of type RaceResultsList
	 */
	public RaceResultList filter(int minAge, int maxAge, String minPace, String maxPace) {
		RaceTime maxP = null;
		RaceTime minP = null;

		RaceResultList filtered = new RaceResultList();
		try {
			maxP = new RaceTime(maxPace);
			minP = new RaceTime(minPace);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getAge() >= minAge && results.get(i).getAge() <= maxAge
					&& results.get(i).getPace().getTimeInSeconds() >= minP.getTimeInSeconds()
					&& results.get(i).getPace().getTimeInSeconds() <= maxP.getTimeInSeconds()) {
				filtered.addResult(results.get(i));
			}

		}
		return filtered;

	}

}
