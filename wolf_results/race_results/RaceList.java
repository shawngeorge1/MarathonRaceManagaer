package edu.ncsu.csc216.wolf_results.race_results;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.wolf_results.util.ArrayList;

/**
 * Observable class RaceList. Holds Race objects and allows RaceManager to
 * manipulate Race objects.
 * 
 * @author shawngeorge
 *
 */
public class RaceList extends Observable implements Observer {

	private ArrayList list;

	/**
	 * Constructs the ArrayList that stores Races.
	 */
	public RaceList() {
		list = new ArrayList();
	}

	/**
	 * The method throws IllegalArgumentException if race is null. Adds race to
	 * list. Observer is added for the race. Observers of RaceList are notified of
	 * the change.
	 * 
	 * @param race Race that is added
	 */
	public void addRace(Race race) {
		if (race == null)
			throw new IllegalArgumentException("Invalid Race");
		list.add(race);
		race.addObserver(this);
		setChanged();
		this.notifyObservers();
	}

	/**
	 * The method constructs a Race using the provided parameters and throws
	 * IllegalArgumentException if Race constructor throws exception. Adds race to
	 * list. Observer is added for the race. Observers of RaceList are notified of
	 * the change.
	 * 
	 * @param name     The name of the Race, as a String
	 * @param distance time of race
	 * @param date     date of race
	 * @param location The location of the race, as a String
	 */
	public void addRace(String name, double distance, LocalDate date, String location) {
		Race race = new Race(name, distance, date, location);
		list.add(race);
		race.addObserver(this);
		setChanged();
		this.notifyObservers();
	}

	/**
	 * Removes race at index idx. Observers of RaceList are notified of the change.
	 * 
	 * @param index index of race
	 */
	public void removeRace(int index) {
		list.remove(index);
		setChanged();
		this.notifyObservers();
	}

	/**
	 * Gets race at index idx. Throws IndexOutOfBoundsException if idx is out of
	 * bounds.
	 * 
	 * @param index index of race
	 * @return Race at index
	 */
	public Race getRace(int index) {
		if (index < 0 || index > list.size()) {
			throw new IndexOutOfBoundsException();
		}
		return (Race) list.get(index);
	}

	/**
	 * Returns number of races in list.
	 * 
	 * @return number of races in list
	 */
	public int size() {
		return list.size();
	}

	/**
	 * This method is called if the Race that the RaceList is observing notified its
	 * observers of a change. If the Observable is an instance of Race, then the
	 * observers of the RaceList will be updated.
	 * 
	 * @param obs Observable object
	 * @param obj object
	 */
	public void update(Observable obs, Object obj) {
		if (obs instanceof Race) {
			setChanged();
			notifyObservers(obs);
		}
	}

}
