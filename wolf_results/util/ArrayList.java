/**
 * 
 */
package edu.ncsu.csc216.wolf_results.util;

/**
 * The class for the ArrayList data structure that is used in other parts of the
 * project
 * 
 * @author Justin Easow
 * @author Shawn George
 *
 */
public class ArrayList implements List {
	/**
	 * A constant value required for GUI classes that implement the Serializable
	 * class, of type long
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1;
	/** An integer value that can be incremented, to increase the capactiy */
	@SuppressWarnings("unused")
	private static final int RESIZE = 1;
	/** A field representing an array of objects */
	@SuppressWarnings("unused")
	private Object[] list;
	/** An field representing the size of the ArrayList, as an Integer */
	private int size;
	/** The inital size of the array storing elements */
	private final static int INIT_SIZE = 10;

	/**
	 * A parameterless constructor for the ArrayList class
	 */
	public ArrayList() {
		list = new Object[INIT_SIZE];
		size = 0;
	}

	/**
	 * The second constructor of the ArrayList class with a parameter of capacity
	 * 
	 * @param capacity
	 *            -The capacity of the Array list, as an IntegerF
	 */
	public ArrayList(int capacity) {
		if (capacity == 0) {
			throw new IllegalArgumentException("Cannot create an ArrayList with capacity of 0");
		}
		list = new Object[capacity];
	}

	/**
	 * A boolean returning true or false depending on whether the list is changed as
	 * a result of the call
	 * 
	 * @param o
	 *            -A parameter of type Object, representing an instance of Object
	 * @return true/false depending on whether the list is changed
	 */
	public boolean add(Object o) {
		if (o == null) {
			throw new NullPointerException();
		}
		if (contains(o)) {
			throw new IllegalArgumentException("Already contains that object");
		}
		list[size++] = o;
		if (size == list.length) {
			growArray();
		}
		return true;
	}

	/**
	 * Adds an object o, at a specified index in the Array list
	 * 
	 * @param index
	 *            -The index of where the object is to be added, as an Integer
	 * @param o
	 *            -The object that is to be added to the ArrayList
	 */
	public void add(int index, Object o) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (o == null) {
			throw new NullPointerException();
		}
		if (contains(o)) {
			throw new IllegalArgumentException();
		}
		if (size == list.length) {
			growArray();
		}

		size++;
		for (int i = size - 1; i >= 0; i--) {
			if (index == i) {
				list[i] = o;
				return;
			} else {
				list[i] = list[i - 1];
			}
		}
	}

	/**
	 * Grows Array if array has reached capacity
	 */
	private void growArray() {
		Object[] growedList = new Object[size * 2];
		for (int i = 0; i < list.length; i++) {
			growedList[i] = list[i];
		}
		list = growedList;

	}

	/**
	 * A class the returns a boolean whether the object contains something specific
	 * 
	 * @param o
	 *            -A parameter of type Object, representing an instance of Object
	 * @return true/false depending on whether it is contained
	 */
	public boolean contains(Object o) {
		for (int i = 0; i < size; i++) {
			if (list[i].equals(o)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Returns the object at a given index.
	 * 
	 * @param index
	 *            -The index of the Object that is to be returned
	 * @return The object that is to be received
	 */
	public Object get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}

		return list[index];
	}

	/**
	 * Returns the index of the object passed in
	 * 
	 * @param o
	 *            -The object that the index is needed for
	 * @return the index of where the object is, as an Integer
	 */
	public int indexOf(Object o) {
		for (int i = 0; i < size; i++) {
			if (list[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * A method that determines whether or not the Array list is empty
	 * 
	 * @return true/false depending on whether the array list has contents, or does
	 *         not
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * A method that removes an object from the array list at a specific index
	 * 
	 * @param index
	 *            -The index of the Object that is to be removed
	 * @return The object that has been removed from the list
	 */
	public Object remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		Object element = list[index];
		if (index == this.size - 1) {
			list[index] = null;
			size--;
		} else {
			for (int i = index; i < this.size - 1; i++) {
				list[i] = list[i + 1];
			}
			list[size - 1] = null;
			size--;
		}
		return element;
	}

	/**
	 * A method that returns the size of the array list
	 * 
	 * @return the size of the array list, as an integer
	 */
	public int size() {
		return size;
	}
}
