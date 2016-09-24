/**
 * 
 */
package com.epam.snake.model.zoo;

import com.epam.snake.model.SnakeGame;
import com.epam.snake.model.zoo.frog_behavior.RedFrogBehavior;

/**
 * The red frog class.
 * 
 * @author Savva_Kodeikin
 *
 */
public class RedFrog extends Frog {

	/**
	 * The red frog constructor.
	 * 
	 * @param snakeGame model
	 */
	public RedFrog(SnakeGame snakeGame) {
		super(snakeGame, new RedFrogBehavior());
	}

}
