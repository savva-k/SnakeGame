/**
 * 
 */
package com.epam.snake.model;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * The directions enumeration, and methods to work with them.
 * 
 * @author Savva_Kodeikin
 *
 */
public enum Direction {
	/**
	 * Direction: up
	 */
	UP {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point makeStep(GameMap map, final Point currentPosition) {
			int newY = checkVerticalBounds(map, currentPosition.y - 1);
			return new Point(currentPosition.x, newY);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Direction getOpposite() {
			return DOWN;
		}

	},

	/**
	 * Direction: left
	 */
	LEFT {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point makeStep(GameMap map, final Point currentPosition) {
			int newX = checkHorizontalBounds(map, currentPosition.x - 1);
			return new Point(newX, currentPosition.y);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Direction getOpposite() {
			return RIGHT;
		}

	},

	/**
	 * Direction: down
	 */
	DOWN {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point makeStep(GameMap map, final Point currentPosition) {
			int newY = checkVerticalBounds(map, currentPosition.y + 1);
			return new Point(currentPosition.x, newY);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Direction getOpposite() {
			return UP;
		}

	},

	/**
	 * Direction: right
	 */
	RIGHT {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point makeStep(GameMap map, final Point currentPosition) {
			int newX = checkHorizontalBounds(map, currentPosition.x + 1);
			return new Point(newX, currentPosition.y);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Direction getOpposite() {
			return LEFT;
		}

	};

	private static final List<Direction> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	/**
	 * Get the random direction.
	 * 
	 * @return random direction
	 */
	public static Direction randomDirection() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	/**
	 * Get possible positions to make a step for the frog, the positions ordered
	 * descending by its hypotenuse
	 * 
	 * @param map game map
	 * @param snakePoint
	 * @param frogPoint
	 * @param safeMove
	 * @return map<Double, Point> where Double - hypotinuse
	 */
	public static Map<Double, Point> getPossiblePositions(GameMap map,
			Point snakePoint, Point frogPoint, boolean safeMove) {
		Map<Double, Point> result = null;

		if (safeMove) {
			result = new TreeMap<Double, Point>(Collections.reverseOrder());
		} else {
			result = new HashMap<Double, Point>();
		}

		for (Direction direction : Direction.values()) {
			Point potentialPosition = direction.makeStep(map, frogPoint);
			result.put(getHypotenuse(snakePoint, potentialPosition),
					potentialPosition);
		}

		return result;

	}

	/**
	 * Get a hypotenuse of a triangle (frogPoint(x, y), snakePoint(x, y),
	 * Point(snakePoint.x, frogPoint.y))
	 * 
	 * @param snakePoint the point of the snake's head
	 * @param frogPoint the point of the frog
	 * @return a hypotenuse we need to compute the farthest point from the snake
	 */
	public static Double getHypotenuse(Point snakePoint, Point frogPoint) {
		double x = Math.abs(snakePoint.getX() - frogPoint.getX());
		double y = Math.abs(snakePoint.getY() - frogPoint.getY());
		return Math.hypot(x, y);
	}

	/**
	 * Checking the horizontal bound and setting the correct value if it's not
	 * correct.
	 * 
	 * @param map Game map
	 * @param x the number to check
	 * @return the correct value
	 */
	protected int checkHorizontalBounds(GameMap map, int x) {
		if (x >= map.getM()) {
			x = 0;
		} else if (x < 0) {
			x = map.getM() - 1;
		}

		return x;
	}

	/**
	 * Checking the vertical bound and setting the correct value if it's not
	 * correct.
	 * 
	 * @param map Game map
	 * @param x the number to check
	 * @return the correct value
	 */
	protected int checkVerticalBounds(GameMap map, int y) {
		if (y >= map.getN()) {
			y = 0;
		} else if (y < 0) {
			y = map.getN() - 1;
		}

		return y;
	}

	/**
	 * Make a step considering the chosen direction.
	 * 
	 * @param map game map
	 * @param currentPosition current coordinates
	 * @return the position after doing a step
	 */
	public abstract Point makeStep(GameMap map, final Point currentPosition);

	/**
	 * Get an opposite direction.
	 * 
	 * @return opposite direction
	 */
	public abstract Direction getOpposite();

	/**
	 * Get the left turn.
	 * 
	 * @param direction the next counterclockwise direction
	 * @return direction
	 */
	public static Direction turnLeft(Direction direction) {
		Direction result = null;

		if (direction == UP) {
			result = LEFT;
		} else if (direction == LEFT) {
			result = DOWN;
		} else if (direction == DOWN) {
			result = RIGHT;
		} else { // if RIGHT
			result = UP;
		}

		return result;
	}

	/**
	 * Get the right turn.
	 * 
	 * @param direction the next counterclockwise direction
	 * @return direction
	 */
	public static Direction turnRight(Direction direction) {
		Direction result = null;

		if (direction == UP) {
			result = RIGHT;
		} else if (direction == RIGHT) {
			result = DOWN;
		} else if (direction == DOWN) {
			result = LEFT;
		} else { // if LEFT
			result = UP;
		}

		return result;
	}
}