/**
 * 
 */
package com.epam.snake.model.zoo;

import java.awt.Point;

import com.epam.snake.model.Direction;
import com.epam.snake.model.SnakeGame;

/**
 * The main abstract animal class.
 * 
 * @author Savva_Kodeikin
 *
 */
public abstract class Animal implements Runnable {

	/**
	 * 
	 */
	private static final int WAIT_TIME = 10;

	/**
	 * Position on the map
	 */
	protected Point position;

	/**
	 * Moving direction (UP, LEFT, RIGHT, DOWN)
	 */
	protected Direction direction;

	/**
	 * How much the animal sleeps (millis).
	 */
	protected long sleepTime;

	/**
	 * Is the animal still alive?
	 */
	protected boolean alive = true;

	/**
	 * Main model controller instance
	 */
	protected SnakeGame snakeGame;

	/**
	 * Empty constructor.
	 */
	public Animal() {

	}

	/**
	 * Constructor with specifying game model instance
	 * 
	 * @param snakeGame
	 */
	public Animal(SnakeGame snakeGame) {
		this.snakeGame = snakeGame;
	}

	/**
	 * Set sleep time.
	 * 
	 * @param snakeGame
	 * @param sleepTime time to sleep
	 */
	public Animal(SnakeGame snakeGame, long sleepTime) {
		this.snakeGame = snakeGame;
		this.sleepTime = sleepTime;
	}

	/**
	 * Get the model instance
	 * 
	 * @return SnakeGame model
	 */
	public SnakeGame getModel() {
		return snakeGame;
	}

	/**
	 * This method describes how moving of the animal.
	 */
	public abstract void move();

	/**
	 * While an animal is alive, and the game isn't paused, it moves and sleeps.
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (alive) {
			if (snakeGame.isPaused()) {
				synchronized (this) {
					try {
						while (snakeGame.isPaused()) {
							wait(WAIT_TIME);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			move();

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Waking up the animal
	 */
	public synchronized void wakeUp() {
		if (!snakeGame.isPaused()) {
			notifyAll();
		}
	}
}
