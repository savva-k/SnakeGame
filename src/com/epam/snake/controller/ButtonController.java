/**
 * 
 */
package com.epam.snake.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.epam.snake.model.SnakeGame;
import com.epam.snake.view.SwingGui;

/**
 * This class controls buttons in the GUI.
 * 
 * @author Savva_Kodeikin
 *
 */
public class ButtonController implements ActionListener {

	private SnakeGame snakeGame;

	/**
	 * Button controller parameter
	 * 
	 * @param snakeGame model instance
	 */
	public ButtonController(SnakeGame snakeGame) {
		this.snakeGame = snakeGame;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (SwingGui.START_GAME_COMMAND.equals(e.getActionCommand())) {
			if (snakeGame.isPaused()) {

				snakeGame.pause();

			} else {

				snakeGame.start();

			}

		} else if (SwingGui.PAUSE_GAME_COMMAND.equals(e.getActionCommand())) {

			snakeGame.pause();

		} else if (SwingGui.STOP_GAME_COMMAND.equals(e.getActionCommand())) {

			snakeGame.stop();

		}
	}

}
