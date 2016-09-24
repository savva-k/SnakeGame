/**
 * 
 */
package com.epam.snake.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.epam.snake.model.SnakeGame;

/**
 * This class handles user's mouse clicking
 * 
 * @author Savva_Kodeikin
 *
 */
public class MouseController extends MouseAdapter {

	private SnakeGame snakeGame;

	/**
	 * Mouse controller parameter
	 * 
	 * @param snakeGame model instance
	 */
	public MouseController(SnakeGame snakeGame) {
		this.snakeGame = snakeGame;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (!snakeGame.isPaused() && !snakeGame.isStopped()) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				snakeGame.getSnake().turnLeft();
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				snakeGame.getSnake().turnRight();
			}
		}
	}
}
