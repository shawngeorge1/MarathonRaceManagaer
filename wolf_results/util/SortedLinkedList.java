package edu.ncsu.csc216.wolf_results.util;

/**
 * The SortedLinkedList class that implement the SortedList interface, that also
 * contains a data structure of linked Nodes
 * 
 * @author Justin Easow
 * @author Shawn George
 *
 * @param <E>
 *            A parameter of type <E>,used in the SortedLinkedList class
 */
public class SortedLinkedList<E extends Comparable<E>> {

	private int size;
	Node head;

	/**
	 * The parameterless constructor for the SortedLinkedList class
	 */
	public SortedLinkedList() {
		size = 0;
		head = null;
	}

	/**
	 * A method that returns the size of the SortedLinkedList
	 * 
	 * @return the same of the list, as an integer
	 */
	public int size() {
		return size;
	}

	/**
	 * A method that returns whether or not the SortedLinkedList is empty
	 * 
	 * @return true/false depending on whether the the list is empty or has contents
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * A class the returns a boolean whether the E contains something specific
	 * 
	 * @param e
	 *            -A parameter of type E, representing an instance of E
	 * @return true/false depending on whether it is contained
	 */
	public boolean contains(E e) {
		Node temp = head;
		while (temp != null) {
			if (temp.value.equals(e)) {
				return true;
			} else {
				temp = temp.next;
			}
		}
		return false;
	}

	/**
	 * A class the returns a boolean whether the E can be added to the sorted linked
	 * list
	 * 
	 * @param e
	 *            -A parameter of type E, representing an instance of E
	 * @return true/false depending on whether it can be added
	 */
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (this.contains(e)) {
			throw new IllegalArgumentException("Element already exists in list");
		}
		if (size == 0) {
			head = new Node(e, null);
			size++;
			return true;
		}

		if (e.compareTo(head.value) < 0) {
			Node current = new Node(e, head);
			head = current;
			size++;
			return true;
		}
		Node temp = head;
		while (temp != null && temp.next != null) {
			if (temp.next.value.compareTo(e) < 0) {
				temp = temp.next;
			} else {
				temp.next = new Node(e, temp.next);
				size++;
				return true;
			}
		}
		temp.next = new Node(e, temp.next);
		size++;
		return true;

	}

	/**
	 * Returns the E at a given index.
	 * 
	 * @param index
	 *            -The index of the E that is to be returned
	 * @return The E that is to be received
	 */
	public E get(int index) {
		Node temp = head;
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i < index; i++) {
			temp = temp.next;
		}
		return temp.value;
	}

	/**
	 * A method that removes an E from the sorted linked list at a specific index,
	 * and returns the Element that was removed
	 * 
	 * @param index
	 *            -The index of the Object that is to be removed
	 * @return The object that has been removed from the list
	 */
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			E element = head.value;
			head = head.next;
			size--;
			return element;
		}

		Node temp = head;

		for (int i = 0; i < index - 1; i++) {
			temp = temp.next;
		}
		E element = temp.next.value;
		temp.next = temp.next.next;
		size--;
		return element;

	}

	/**
	 * Returns the index of the E passed in
	 * 
	 * @param e
	 *            -The E that the index is needed for
	 * @return the index of where the E is, as an Integer
	 */
	public int indexOf(E e) {
		Node temp = head;
		for (int i = 0; i < size; i++) {
			if (temp.value == e) {
				return i;
			}
			temp = temp.next;
		}
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + size;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		SortedLinkedList other = (SortedLinkedList) obj;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	/**
	 * A method that will generate the toString formatting for the sorted linked
	 * list
	 * 
	 * @return the toString content, as a String
	 */
	public String toString() {
		if (head == null) {
			return "[]";
		} else if (head.next == null) {
			return "[" + head.value + "]";
		}
		Node temp = head;
		String text = "[";
		while (temp != null) {
			text += temp.value;
			if (temp.next != null) {
				text += ", ";
			} else {
				text += "]";
			}
			temp = temp.next;
		}

		return text;

	}

	/**
	 * An inner class within SortedLinkedList,that contains an E, as well as a
	 * reference to the next node
	 * 
	 * @author Justin Easow
	 * @author Shawn George
	 *
	 */
	public class Node {
		/** Variable representing data being held */
		public E value;
		Node next;

		/**
		 * A constructor for the node class
		 * 
		 * @param value
		 *            -the data of the node, of type E
		 * @param next
		 *            -A parameter representing the next node
		 */
		public Node(E value, Node next) {
			this.value = value;
			this.next = next;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("unchecked")
			Node other = (Node) obj;
			if (next == null) {
				if (other.next != null)
					return false;
			} else if (!next.equals(other.next))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

	}
}
