/**
 * 
 */
package com.epam.snake.model.zoo;

import java.awt.Point;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.log4j.Logger;

import com.epam.snake.model.Cell;
import com.epam.snake.model.DirectedPoint;
import com.epam.snake.model.Direction;
import com.epam.snake.model.GameMap;
import com.epam.snake.model.SnakeGame;

/**
 * This is the snake class.
 * 
 * @author Savva_Kodeikin
 *
 */
public class Snake extends Animal {

	private static final int MINIMAL_SNAKE_LENGTH = 2;

	private static final Logger logger = Logger.getLogger(Snake.class);

	private Deque<DirectedPoint> snakePoints;
	private GameMap map;
	private boolean timeToGrow = false;
	private boolean dying = false;
	private boolean canMakeTurn = false;

	/**
	 * The snake constructor.
	 * 
	 * @param snakeGame model
	 * @param snakeLength the initial length of snake
	 * @param snakeSleepTime the time that the snake sleeps after moving
	 */
	public Snake(SnakeGame snakeGame, int snakeLength, int snakeSleepTime) {
		super(snakeGame);
		this.sleepTime = snakeSleepTime;
		this.map = snakeGame.getMap();

		snakePoints = new ConcurrentLinkedDeque<DirectedPoint>();
		direction = Direction.RIGHT;

		for (int i = 0; i < snakeLength; i++) {
			DirectedPoint point = new DirectedPoint(i, 0, direction);
			snakePoints.add(point);
			map.getCell(point).setAnimalContentWithoutUpdate(this);
		}

		position = snakePoints.getLast();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void move() {
		logger.debug("Entering move() method");

		canMakeTurn = true;
		DirectedPoint newPosition = new DirectedPoint(direction.makeStep(map,
				position), direction);

		Cell nextCell = map.getCell(newPosition);
		nextCell.lock();

		checkCell(nextCell, newPosition);
		calculatePoints(nextCell, newPosition);

		nextCell.unlock();

		checkHealth();
		logger.debug("Leaving move() method");
	}

	/**
	 * Get snake points.
	 * 
	 * @return deque<DirectedPoint> with all points that contains the snake
	 */
	public Deque<DirectedPoint> getPoints() {
		return snakePoints;
	}

	/**
	 * Get the direction of the snake.
	 * 
	 * @return Current direction of the snake.
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Get the point of the snake's head.
	 * 
	 * @return the last point in the snakePoint deque
	 */
	public DirectedPoint getHead() {
		return snakePoints.getLast();
	}

	/**
	 * Get the point with the direction and body part of a snake.
	 * 
	 * @param usualPoint - usual point like (x, y)
	 * @return DirectedPoint with Direction direction and SnakeBodyPart bodyPart
	 */
	public DirectedPoint getDirectedPoint(Point usualPoint) {
		logger.debug(String.format(
				"Entering getDirectedPoint(Point point) method, point = %s",
				usualPoint));

		if (usualPoint == null)
			return null;
		DirectedPoint result = null;

		for (DirectedPoint directedPoint : snakePoints) {
			if (directedPoint.equals(usualPoint)) {
				result = directedPoint;
				break;
			}
		}

		logger.debug(String.format(
				"Leaving getDirectedPoint() method, result = %s", result));
		return result;
	}

	/**
	 * Turns the snake to the left
	 */
	public void turnLeft() {
		if (canMakeTurn) {
			direction = Direction.turnLeft(direction);
			canMakeTurn = false;
		}
	}

	/**
	 * Turns the snake to the right
	 */
	public void turnRight() {
		if (canMakeTurn) {
			direction = Direction.turnRight(direction);
			canMakeTurn = false;
		}
	}

	/**
	 * Increase snake's length (turns flag "timeToGrow" into "true")
	 */
	public void increaseLength() {
		timeToGrow = true;
	}

	/**
	 * Decrease snake's length
	 */
	public void decreaseLength() {
		if (snakePoints.size() > MINIMAL_SNAKE_LENGTH) {
			cutTail();
		}
	}

	/**
	 * The snake is died, maybe it's bitten itself or it's eaten a blue frog.
	 */
	public void kill() {
		wakeUp();
		if (!dying) {
			logger.debug("The snake was killed");
			dying = true;
		}
	}

	/**
	 * Get the point which is next to the current one.
	 * 
	 * @param currentPoint current point
	 * @return the point next to the current one
	 */
	public DirectedPoint getNextPoint(Point currentPoint) {
		logger.debug(String.format(
				"Entering getNextPoint(Point point) method, point = %s",
				currentPoint));
		DirectedPoint[] snkPoints = getPoints().toArray(
				new DirectedPoint[getPoints().size()]);
		DirectedPoint result = null;

		for (int i = 0; i < snkPoints.length; i++) {
			if (currentPoint.equals(snkPoints[i])) {
				if (snkPoints.length > (i + 1)) {
					result = snkPoints[i + 1];
					break;
				}
			}
		}

		logger.debug(String.format(
				"Leaving getNextPoint() method, result = %s", result));
		return result;
	}

	/**
	 * Get the snake's tail.
	 * 
	 * @return the first element from snakePoints
	 */
	public DirectedPoint getTail() {
		return snakePoints.getFirst();
	}

	/**
	 * Returns true if the snake has bitten itself.
	 * 
	 * @return boolean, should the snake die?
	 */
	public boolean isDying() {
		return dying;
	}

	/*
	 * delete the tail (a small piece of) of the snake
	 */
	private void cutTail() {
		map.getCell(snakePoints.pollFirst()).setAnimalContent(null);
	}

	/*
	 * Eating an animal. If the animal is the snake, then the snake dies.
	 */
	private void eat(Animal metAnimal) {
		logger.debug(String.format("The snake ate an animal: %s", metAnimal
				.getClass().getSimpleName()));
		Frog frog = null;

		try {
			frog = (Frog) metAnimal;
			snakeGame.getFrogPool().eatFrog(this, frog);
		} catch (ClassCastException e) {
			dying = true;
		}
	}

	/*
	 * Checking the next cell, eating and maybe growing if there is something
	 * eatable.
	 */
	private void checkCell(Cell cell, DirectedPoint newPosition) {
		logger.debug(String
				.format("Entering checkCell(Cell c, DirectedPoint dp) method, c = %s, dp = %s",
						cell, newPosition));
		Animal metAnimal = cell.getAnimalContent();

		if (metAnimal != null && !newPosition.equals(getTail())) {
			eat(metAnimal);
		}

		if (timeToGrow) {
			timeToGrow = false;
		} else {
			cutTail();
		}

		logger.debug("Leaving checkCell() method");
	}

	/*
	 * Calculating snake points and setting it on the map.
	 */
	private void calculatePoints(Cell cell, DirectedPoint newPosition) {
		logger.debug(String
				.format("Entering calculatePoints(Cell c, DirectedPoint dp) method, c = %s, dp = %s",
						cell, newPosition));

		snakePoints.add(newPosition);
		cell.setAnimalContent(this);
		map.getCell(position).setAnimalContent(this);
		map.getCell(getTail()).setAnimalContent(this);
		position = newPosition;

		logger.debug("Leaving calculatePoints() method");
	}

	/*
	 * If there is something wrong with the snake's health, stopping the game.
	 */
	private void checkHealth() {
		if (dying) {
			this.alive = false;
			if (!snakeGame.isStopped()) {
				snakeGame.stop();
			}
		}
	}

}
