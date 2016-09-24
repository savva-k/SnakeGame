/**
 * 
 */
package com.epam.snake.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.epam.snake.controller.MouseController;
import com.epam.snake.model.Cell;
import com.epam.snake.model.GameMap;
import com.epam.snake.model.ICellPainter;
import com.epam.snake.model.SnakeGame;
import com.epam.snake.model.zoo.Snake;
import com.epam.snake.observer.IObserver;

/**
 * Main JPanel, where the game is drawn. When a Cell from a model is notifying
 * this panel that there are some changes, it repaints with double buffering,
 * using BufferedImage
 * 
 * @author Savva_Kodeikin
 *
 */
public class SwingGameMap extends JPanel implements IObserver {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	private static final int COMPONENT_WIDTH = 800;
	private static final int COMPONENT_HEIGHT = 600;
	private transient ICellPainter painter = new PictureCellPainter();
	private transient SwingGui view;
	private transient SnakeGame snakeGame;
	private transient GameMap map;
	private transient BufferedImage fullImage;
	private transient BufferedImage visibleImage;
	private boolean needScroll;
	private int width;
	private int height;
	private Point scrollOffset = new Point(0, 0);

	/**
	 * Initializing object fields
	 * 
	 * @param view game view
	 * @param snakeGame game model
	 */
	public SwingGameMap(SwingGui view, SnakeGame snakeGame) {
		super(true);
		this.view = view;
		this.snakeGame = snakeGame;
		this.map = snakeGame.getMap();
		this.addMouseListener(new MouseController(snakeGame));
	}

	/**
	 * Initialize game component. Setting sizes, images, painter and adding
	 * observer to the model.
	 */
	public void init() {
		painter.setGraphics((Graphics2D) this.getGraphics());
		snakeGame.addObserver(this);
		fullImage = new BufferedImage(map.getM() * SwingGui.SCALE, map.getN()
				* SwingGui.SCALE, BufferedImage.TYPE_INT_RGB);

		width = Math.min(fullImage.getWidth(), COMPONENT_WIDTH);
		height = Math.min(fullImage.getHeight(), COMPONENT_HEIGHT);

		needScroll = fullImage.getWidth() > COMPONENT_WIDTH
				|| fullImage.getHeight() > COMPONENT_HEIGHT;

		visibleImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(visibleImage, 0, 0, null);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		Dimension result = new Dimension(width, height);
		return result;
	}

	/**
	 * If the game is running, repaint the visible content. If the moving animal
	 * is a snake and the game field is bigger than COMPONENT_HEIGHT x
	 * COMPONENT_WIDTH, then compute scrolling offset and get new image to
	 * paint.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void refreshCell(Cell cell) {
		view.refreshLabel();
		Snake snake = snakeGame.getSnake();
		if (snakeGame.isStopped() == false) {

			scrollAndCrop(cell, snake);

			Graphics2D g2d = (Graphics2D) fullImage.getGraphics();
			painter.setGraphics(g2d);
			painter.drawCell(cell);
			repaint();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refreshGUIElements() {
		view.refreshButtons();
		view.refreshLabel();
	}

	/*
	 * The method decides if the game view should be scrolled and crops
	 * invisible canvas
	 */
	private void scrollAndCrop(Cell cell, Snake snake) {
		if ((cell.getAnimalContent() instanceof Snake) && needScroll) {
			scrollOffset = getScrollOffset(snake);
		}
		visibleImage = fullImage.getSubimage(scrollOffset.x, scrollOffset.y,
				width, height);
	}

	/*
	 * Get the point when the scrolled game view starts.
	 */
	private Point getScrollOffset(Snake snake) {
		int x = snake.getHead().x * SwingGui.SCALE - COMPONENT_WIDTH / 2;
		int y = snake.getHead().y * SwingGui.SCALE - COMPONENT_HEIGHT / 2;

		if (x + COMPONENT_WIDTH > fullImage.getWidth()) {
			x = fullImage.getWidth() - COMPONENT_WIDTH - 1;
		}
		if (y + COMPONENT_HEIGHT > fullImage.getHeight()) {
			y = fullImage.getHeight() - COMPONENT_HEIGHT - 1;
		}

		x = (x < 0) ? 0 : x;
		y = (y < 0) ? 0 : y;

		return new Point(x, y);
	}

}
