/**
 * 
 */
package com.epam.snake.model.zoo;

import com.epam.snake.model.SnakeGame;
import com.epam.snake.model.zoo.frog_behavior.BlueFrogBehavior;

/**
 * The blue frog class.
 * 
 * @author Savva_Kodeikin
 *
 */
public class BlueFrog extends Frog {

	/**
	 * The blue frog constructor
	 * 
	 * @param snakeGame model
	 */
	public BlueFrog(SnakeGame snakeGame) {
		super(snakeGame, new BlueFrogBehavior());
	}

}
