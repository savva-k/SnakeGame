/**
 * 
 */
package com.epam.snake.model;

import java.awt.Point;
import java.util.concurrent.locks.ReentrantLock;

import com.epam.snake.model.zoo.Animal;

/**
 * A cell of a map.
 * 
 * @author Savva_Kodeikin
 *
 */
public class Cell {

	private static final String CELL_TO_STRING_FORMAT = "Cell (%s, %s)";

	private Animal content = null;
	private SnakeGame snakeGame;
	private int m;
	private int n;
	private ReentrantLock lock = new ReentrantLock();

	/**
	 * Create a cell with coordinates M, N.
	 * 
	 * @param snakeGame model
	 * @param m
	 * @param n
	 */
	public Cell(SnakeGame snakeGame, int m, int n) {
		this.snakeGame = snakeGame;
		this.m = m;
		this.n = n;
		update();
	}

	/**
	 * Set the content of this cell.
	 * 
	 * @param animal to set into this cell
	 */
	public void setAnimalContent(Animal animal) {
		lock.lock();
		try {
			this.content = animal;
			update();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Set the content of this cell without updating cell.
	 * 
	 * @param animal to set into this cell
	 */
	public void setAnimalContentWithoutUpdate(Animal animal) {
		lock.lock();
		try {
			this.content = animal;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Get animal that is in this cell.
	 * 
	 * @return Animal that consists this cell, or null if the cell is empty.
	 */
	public Animal getAnimalContent() {
		return content;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format(CELL_TO_STRING_FORMAT, m, n);
	}

	/**
	 * Get m (x) coordinate
	 * 
	 * @return m
	 */
	public int getM() {
		return m;
	}

	/**
	 * Get n (y) coordinate
	 * 
	 * @return n
	 */
	public int getN() {
		return n;
	}

	/**
	 * Lock the cell
	 */
	public void lock() {
		lock.lock();
	}

	/**
	 * Try to lock the cell
	 * 
	 * @return is the result successful
	 */
	public boolean tryLock() {
		return lock.tryLock();
	}

	/**
	 * Unlock the cell
	 */
	public void unlock() {
		lock.unlock();
	}

	/**
	 * Check if the cell is locked.
	 * 
	 * @return boolean, is this cell locked or not.
	 */
	public boolean isLocked() {
		return lock.isLocked();
	}

	/**
	 * Send notification to observers that this cell was updated.
	 */
	public void update() {
		snakeGame.repaintCell(this);
	}

	/**
	 * Get the coordinates of this cell.
	 * 
	 * @return new Point(size_m, size_n)
	 */
	public Point getPoint() {
		return new Point(m, n);
	}
}