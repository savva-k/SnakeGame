/**
 * 
 */
package com.epam.snake.model;

import java.awt.Point;

/**
 * A point that has a direction.
 * 
 * @author Savva_Kodeikin
 *
 */
public class DirectedPoint extends Point {

	private static final String DIRECTED_POINT_TO_STRING_FORMAT = "(%s, %s), %s";
	private static final long serialVersionUID = 1L;
	private Direction direction;

	/**
	 * Create a point with x, y coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public DirectedPoint(int x, int y) {
		super(x, y);
	}

	/**
	 * Create a point with Point p.x, p.y coordinates
	 * 
	 * @param p
	 */
	public DirectedPoint(Point p) {
		super(p);
	}

	/**
	 * Create a point with x, y coordinates and Direction direction
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 */
	public DirectedPoint(int x, int y, Direction direction) {
		super(x, y);
		this.direction = direction;
	}

	/**
	 * Create a point with Point p.x, p.y coordinates and Direction direction
	 * 
	 * @param p
	 * @param direction
	 */
	public DirectedPoint(Point p, Direction direction) {
		super(p);
		this.direction = direction;
	}

	/**
	 * Get the point direction
	 * 
	 * @return direction of this point
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Set the point direction
	 * 
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format(DIRECTED_POINT_TO_STRING_FORMAT, x, y, direction);
	}

}
