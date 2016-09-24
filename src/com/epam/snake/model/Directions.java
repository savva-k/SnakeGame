/**
 * 
 */
package com.epam.snake.model;

/**
 * A pair of directions
 * 
 * @author Savva_Kodeikin
 * @version 1.01
 */
public class Directions {
	private Direction directionA;
	private Direction directionB;

	/**
	 * Empty constructor
	 */
	public Directions() {

	}

	/**
	 * Initializing the object
	 * 
	 * @param directionA
	 * @param directionB
	 */
	public Directions(Direction directionA, Direction directionB) {
		this.directionA = directionA;
		this.directionB = directionB;
	}

	/**
	 * @return the directionA
	 */
	public Direction getDirectionA() {
		return directionA;
	}

	/**
	 * @param directionA the directionA to set
	 */
	public void setDirectionA(Direction directionA) {
		this.directionA = directionA;
	}

	/**
	 * @return the directionB
	 */
	public Direction getDirectionB() {
		return directionB;
	}

	/**
	 * @param directionB the directionB to set
	 */
	public void setDirectionB(Direction directionB) {
		this.directionB = directionB;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((directionA == null) ? 0 : directionA.hashCode());
		result = prime * result
				+ ((directionB == null) ? 0 : directionB.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Directions other = (Directions) obj;
		if (directionA != other.directionA)
			return false;
		if (directionB != other.directionB)
			return false;
		return true;
	}
}
