package edu.ncsu.csc216.wolf_results.manager;

import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.wolf_results.model.io.WolfResultsReader;
import edu.ncsu.csc216.wolf_results.model.io.WolfResultsWriter;
import edu.ncsu.csc216.wolf_results.race_results.RaceList;

/**
 * WolfResultsManager Maintains the data structures for the entire application.
 * 
 * @author Justin Easow
 * @author Shawn George
 *
 */
public class WolfResultsManager extends Observable implements Observer {

	/** The name of the file being worked with, as a String */
	private String filename;
	/** A variable indicating whether or the object has been changed */
	private boolean changed;

	private RaceList list;
	private static WolfResultsManager instance;

	/**
	 * A getter method that returns a single instance of WolfResultsManager
	 * 
	 * @return an instance of WolfResultsManager
	 */
	public static WolfResultsManager getInstance() {
		if (instance == null) {
			instance = new WolfResultsManager();
		}
		return instance;
	}

	/**
	 * The constructor for the WolfResultsManger class that makes an empty RaceList,
	 * and registering as an Observer of the list
	 */
	private WolfResultsManager() {
		try {
			list = new RaceList();
			list.addObserver(this);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		setChanged();
		this.notifyObservers(this);

		list = new RaceList();
	}

	/**
	 * A method that creates new empty RaceList and registers the
	 * WolfResultsManager. It also notifies observers if no exception is thrown
	 */
	public void newList() {
		list = new RaceList();
		this.changed = false;
		list.addObserver(this);
		setChanged();
		this.notifyObservers(this);
	}

	/**
	 * Returns the value stored in the private variable 'changed
	 * 
	 * @return the variable changed, as a boolean
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * A getter method that returns the filename
	 * 
	 * @return the current filename, as a String
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * A simple setter method, to assign new contents to the filename variable
	 * 
	 * @param fn
	 *            -The new filename that is to be assigned, as a String
	 */
	public void setFilename(String fn) {
		if (fn == null || fn.equals("") || fn.equals(" "))
			throw new IllegalArgumentException("Invalid filename");
		String stringFilename = fn.trim();
		if (stringFilename == null || stringFilename.equals("")) {
			throw new IllegalArgumentException("Invalid filename");
		}
		this.filename = stringFilename;

		this.filename.equals(stringFilename);
	}

	/**
	 * A method that loads in the file that is to be used, and adds the
	 * WolfResultsManager as an observer to the RaceList
	 * 
	 * @param fn
	 *            -The filename that is to be loaded, as a String
	 */
	public void loadFile(String fn) {

		setFilename(fn);
		RaceList raceList = WolfResultsReader.readRaceListFile(this.filename);
		this.list = raceList;
		list.addObserver(this);
		this.changed = false;
		setChanged();
		this.notifyObservers(this);
	}

	/**
	 * A method that saves the file with the given filename
	 * 
	 * @param fn
	 *            -The filename that is to be saved, as a String
	 */
	public void saveFile(String fn) {
		setFilename(fn);
		WolfResultsWriter.writeRaceFile(this.filename, list);
	}

	/**
	 * A getter method that returns the RaceList
	 * 
	 * @return the current RaceList
	 */
	public RaceList getRaceList() {
		return list;
	}

	/**
	 * Called whenever WolfResultsManager changes, handles the updating
	 * 
	 * @param arg
	 *            -a parameter of type cookie
	 * @param o
	 *            -an instance of the Observable type
	 * 
	 */
	public void update(Observable o, Object arg) {

		this.changed = true;
		setChanged();
		this.notifyObservers(this);

	}
}
