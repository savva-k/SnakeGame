/**
 * 
 */
package com.epam.snake.model.zoo;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import com.epam.snake.model.Cell;
import com.epam.snake.model.Direction;
import com.epam.snake.model.GameMap;
import com.epam.snake.model.SnakeGame;
import com.epam.snake.model.zoo.frog_behavior.IFrogBehavior;

/**
 * An abstract frog class.
 * 
 * @author Savva_Kodeikin
 *
 */
public abstract class Frog extends Animal {

	private static final double RUNAWAY_COEFFICIENT = 0.8;
	private static final int SLEEP_COEFFICIENT = 3;

	/**
	 * Game map instance
	 */
	protected GameMap map;

	/**
	 * Snake instance
	 */
	protected Snake snake;

	/**
	 * Frog behavior
	 */
	protected IFrogBehavior behavior;

	/**
	 * Frog constructor.
	 * 
	 * @param snakeGame model
	 * @param behavior frog behavior
	 */
	public Frog(SnakeGame snakeGame, IFrogBehavior behavior) {
		super(snakeGame);
		this.behavior = behavior;

		map = snakeGame.getMap();
		position = getFreeCell();
		snake = snakeGame.getSnake();
		sleepTime = snake.sleepTime * SLEEP_COEFFICIENT;

	}

	/**
	 * @return IFrogBehavior behavior
	 */
	public IFrogBehavior getEatenBehavior() {
		return behavior;
	}

	/**
	 * Killing the poor animal.
	 */
	public void kill() {
		alive = false;
		wakeUp();
	}

	/**
	 * Moving algorithm {@inheritDoc}
	 */
	@Override
	public void move() {
		Cell cell = null;
		Double currentPointHypo = Direction.getHypotenuse(snake.getHead(),
				position);
		Cell currentCell = map.getCell(position);
		boolean safeMove = Math.random() <= RUNAWAY_COEFFICIENT;

		/*
		 * Checking for free cell to move and moving
		 */
		for (Map.Entry<Double, Point> e : Direction.getPossiblePositions(map,
				snake.getHead(), position, safeMove).entrySet()) {

			if (e.getKey() <= currentPointHypo && safeMove) {
				continue;
			}

			cell = map.getCell(e.getValue());

			if (tryMove(cell, currentCell)) {
				break;
			}
		}
	}

	/*
	 * Try to move from the current cell to the target cell. Returns true if the
	 * attempt is successful.
	 */
	private boolean tryMove(Cell targetCell, Cell currentCell) {
		boolean result = false;

		if (targetCell.tryLock()) {
			try {
				/*
				 * if the cell is not locked by the snake, or the other frog
				 */
				if (currentCell.tryLock()) {
					try {

						if (targetCell.getAnimalContent() == null) {
							currentCell.setAnimalContent(null);
							targetCell.setAnimalContent(this);
							position = targetCell.getPoint();
							result = true;
						}

						/*
						 * Unlock current (already previous) cell.
						 */
					} finally {
						currentCell.unlock();
					}
				}
			} finally {
				targetCell.unlock();
			}
		}

		return result;
	}

	/*
	 * Get a free place to put a frog.
	 */
	private Point getFreeCell() {

		Point position = null;
		boolean found = false;
		Random random = new Random();
		Cell cell;

		while (!found) {

			int randomM = random.nextInt(map.getM());
			int randomN = random.nextInt(map.getN());
			cell = map.getCell(randomM, randomN);
			cell.lock();

			if (cell.getAnimalContent() == null) {
				position = new Point(randomM, randomN);
				map.getCell(position).setAnimalContent(this);
				found = true;
			}

			cell.unlock();

		}

		return position;
	}

}
