/**
 * 
 */
package com.epam.snake.model.zoo;

import com.epam.snake.model.SnakeGame;
import com.epam.snake.model.zoo.frog_behavior.GreenFrogBehavior;

/**
 * The green frog class.
 * 
 * @author Savva_Kodeikin
 *
 */
public class GreenFrog extends Frog {

	/**
	 * The green frog constructor.
	 * 
	 * @param snakeGame model
	 */
	public GreenFrog(SnakeGame snakeGame) {
		super(snakeGame, new GreenFrogBehavior());
	}

}
