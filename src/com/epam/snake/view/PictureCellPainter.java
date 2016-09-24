/**
 * 
 */
package com.epam.snake.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.epam.snake.model.Cell;
import com.epam.snake.model.DirectedPoint;
import com.epam.snake.model.Direction;
import com.epam.snake.model.Directions;
import com.epam.snake.model.ICellPainter;
import com.epam.snake.model.zoo.Animal;
import com.epam.snake.model.zoo.BlueFrog;
import com.epam.snake.model.zoo.GreenFrog;
import com.epam.snake.model.zoo.RedFrog;
import com.epam.snake.model.zoo.Snake;

/**
 * This class paints the game map using pictures.
 * 
 * @author Savva_Kodeikin
 *
 */
public class PictureCellPainter implements ICellPainter {

	private static final Logger logger = Logger
			.getLogger(PictureCellPainter.class);
	private static final float SNAKE_IMAGE_SIZE_DELIMITER = 1;
	private static final float FROG_IMAGE_SIZE_DELIMITER = 1.2f;

	private static Image greenFrogImage;
	private static Image redFrogImage;
	private static Image blueFrogImage;

	private static Image snakeHeadRight;
	private static Image snakeHeadLeft;
	private static Image snakeHeadUp;
	private static Image snakeHeadDown;

	private static Image snakeTailRight;
	private static Image snakeTailLeft;
	private static Image snakeTailUp;
	private static Image snakeTailDown;

	private static Image bodyHorizontal;
	private static Image bodyVertical;

	private static Image bodyDownLeft;
	private static Image bodyDownRight;
	private static Image bodyUpLeft;
	private static Image bodyUpRight;

	private static Image snakeDeadHeadRight;
	private static Image snakeDeadHeadLeft;
	private static Image snakeDeadHeadUp;
	private static Image snakeDeadHeadDown;

	private static Image ground;

	private Graphics2D graphics;
	private int scale = SwingGui.SCALE;

	static {
		try {
			greenFrogImage = ImageIO.read(new File("resources/green-frog.png"));
			redFrogImage = ImageIO.read(new File("resources/red-frog.png"));
			blueFrogImage = ImageIO.read(new File("resources/blue-frog.png"));

			snakeHeadRight = ImageIO.read(new File("resources/head-r.png"));
			snakeHeadLeft = ImageIO.read(new File("resources/head-l.png"));
			snakeHeadUp = ImageIO.read(new File("resources/head-u.png"));
			snakeHeadDown = ImageIO.read(new File("resources/head-d.png"));

			snakeDeadHeadRight = ImageIO.read(new File("resources/dead-r.png"));
			snakeDeadHeadLeft = ImageIO.read(new File("resources/dead-l.png"));
			snakeDeadHeadUp = ImageIO.read(new File("resources/dead-u.png"));
			snakeDeadHeadDown = ImageIO.read(new File("resources/dead-d.png"));

			snakeTailRight = ImageIO.read(new File("resources/tail-r.png"));
			snakeTailLeft = ImageIO.read(new File("resources/tail-l.png"));
			snakeTailUp = ImageIO.read(new File("resources/tail-u.png"));
			snakeTailDown = ImageIO.read(new File("resources/tail-d.png"));

			bodyHorizontal = ImageIO.read(new File("resources/body-h.png"));
			bodyVertical = ImageIO.read(new File("resources/body-v.png"));

			bodyDownLeft = ImageIO.read(new File("resources/turn-l-d.png"));
			bodyDownRight = ImageIO.read(new File("resources/turn-d-r.png"));
			bodyUpLeft = ImageIO.read(new File("resources/turn-l-u.png"));
			bodyUpRight = ImageIO.read(new File("resources/turn-u-r.png"));

			ground = ImageIO.read(new File("resources/ground.png"));

		} catch (IOException e) {
			logger.error("Resource loading error: " + e.getMessage());
			System.exit(0);
		}
	}

	private static Map<Directions, Image> images = new HashMap<Directions, Image>();

	static {
		images.put(new Directions(Direction.UP, Direction.DOWN), bodyVertical);
		images.put(new Directions(Direction.UP, Direction.UP), bodyVertical);
		images.put(new Directions(Direction.DOWN, Direction.DOWN), bodyVertical);
		images.put(new Directions(Direction.DOWN, Direction.UP), bodyVertical);

		images.put(new Directions(Direction.LEFT, Direction.RIGHT),
				bodyHorizontal);
		images.put(new Directions(Direction.LEFT, Direction.LEFT),
				bodyHorizontal);
		images.put(new Directions(Direction.RIGHT, Direction.RIGHT),
				bodyHorizontal);
		images.put(new Directions(Direction.RIGHT, Direction.LEFT),
				bodyHorizontal);

		images.put(new Directions(Direction.UP, Direction.LEFT), bodyDownLeft);
		images.put(new Directions(Direction.RIGHT, Direction.DOWN),
				bodyDownLeft);

		images.put(new Directions(Direction.UP, Direction.RIGHT), bodyDownRight);
		images.put(new Directions(Direction.LEFT, Direction.DOWN),
				bodyDownRight);

		images.put(new Directions(Direction.RIGHT, Direction.UP), bodyUpLeft);
		images.put(new Directions(Direction.DOWN, Direction.LEFT), bodyUpLeft);

		images.put(new Directions(Direction.LEFT, Direction.UP), bodyUpRight);
		images.put(new Directions(Direction.DOWN, Direction.RIGHT), bodyUpRight);
	}

	/**
	 * PictureCellPainter constructor
	 */
	public PictureCellPainter() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void drawCell(Cell cell) {
		fillCell(cell);
		drawAnimal(cell);
	}

	/**
	 * If the animal inside the cell is a snake, call drawSnake(Snake) method,
	 * else choose the frog instance and draw the appropriate image.
	 * {@inheritDoc}
	 */
	@Override
	public void drawAnimal(Cell cell) {
		Animal animal = cell.getAnimalContent();

		if (animal != null) {
			if (animal instanceof Snake) {

				Snake snake = (Snake) animal;
				drawSnake(cell, snake);

			} else {

				drawFrog(cell, animal);

			}
		}
	}

	/*
	 * Drawing a frog.
	 */
	private void drawFrog(Cell cell, Animal animal) {
		Rectangle rect = null;
		Image image = null;

		if (animal instanceof BlueFrog) {
			image = blueFrogImage;
		} else if (animal instanceof RedFrog) {
			image = redFrogImage;
		} else if (animal instanceof GreenFrog) {
			image = greenFrogImage;
		} else {
			throw new IllegalArgumentException();
		}

		rect = getCoordinates(cell, FROG_IMAGE_SIZE_DELIMITER);
		graphics.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
	}

	/*
	 * Drawing the snake.
	 */
	private void drawSnake(Cell cell, Snake snake) {
		Rectangle rect = getCoordinates(cell, SNAKE_IMAGE_SIZE_DELIMITER);
		DirectedPoint currentPoint = snake.getDirectedPoint(cell.getPoint());
		DirectedPoint nextPoint = snake.getNextPoint(cell.getPoint());

		Image image = getSnakeImage(snake, currentPoint, nextPoint);

		graphics.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
		if (snake.isDying() && currentPoint.equals(snake.getHead())) {
			graphics.drawImage(
					getDeadHeadImage(snake.getHead().getDirection()), rect.x,
					rect.y, rect.width, rect.height, null);
		}

	}

	/*
	 * Get snake image considering current cell and next cell directions
	 */
	private Image getSnakeImage(Snake snake, DirectedPoint currentPoint,
			DirectedPoint nextPoint) {
		Image image = null;

		if (nextPoint != null) {
			Direction currentDirection = currentPoint.getDirection();
			Direction nextDirection = nextPoint.getDirection();

			if (currentPoint.equals(snake.getTail())) {
				image = getTailImage(nextDirection);
			} else {
				image = getBodyImage(currentDirection, nextDirection);
			}

		} else {
			image = getHeadImage(currentPoint.getDirection());
		}

		return image;
	}

	/*
	 * Get an image considering directions of current and next points.
	 */
	private Image getBodyImage(Direction currPoint, Direction nextPoint) {
		return images.get(new Directions(currPoint, nextPoint));
	}

	/*
	 * Get a head image
	 */
	private Image getHeadImage(Direction direction) {
		Image result = null;

		if (direction == Direction.DOWN) {
			result = snakeHeadDown;
		} else if (direction == Direction.UP) {
			result = snakeHeadUp;
		} else if (direction == Direction.LEFT) {
			result = snakeHeadLeft;
		} else {
			result = snakeHeadRight;
		}

		return result;
	}

	/*
	 * Get a dead head image
	 */
	private Image getDeadHeadImage(Direction direction) {
		Image result = null;

		if (direction == Direction.DOWN) {
			result = snakeDeadHeadDown;
		} else if (direction == Direction.UP) {
			result = snakeDeadHeadUp;
		} else if (direction == Direction.LEFT) {
			result = snakeDeadHeadLeft;
		} else {
			result = snakeDeadHeadRight;
		}

		return result;
	}

	/*
	 * Get a tail image
	 */
	private Image getTailImage(Direction direction) {
		Image result = null;

		if (direction == Direction.DOWN) {
			result = snakeTailDown;
		} else if (direction == Direction.UP) {
			result = snakeTailUp;
		} else if (direction == Direction.LEFT) {
			result = snakeTailLeft;
		} else {
			result = snakeTailRight;
		}

		return result;
	}

	/*
	 * Get coords, width and height for painting
	 */
	private Rectangle getCoordinates(Cell cell, float delimiter) {
		int w = (int) (scale / delimiter);
		int h = w;
		int x = cell.getM() * scale + scale / 2 - w / 2;
		int y = cell.getN() * scale + scale / 2 - h / 2;

		return new Rectangle(x, y, w, h);
	}

	/*
	 * Fill the cell with a background image
	 */
	private void fillCell(Cell cell) {
		int x = cell.getM() * scale;
		int y = cell.getN() * scale;
		graphics.drawImage(ground, x, y, scale, scale, null);
	}

}
