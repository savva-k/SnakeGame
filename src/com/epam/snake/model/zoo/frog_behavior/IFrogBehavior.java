/**
 * 
 */
package com.epam.snake.model.zoo.frog_behavior;

import com.epam.snake.model.zoo.Frog;
import com.epam.snake.model.zoo.Snake;

/**
 * Implement this interface and delegate an instance to a frog, to give it the
 * right behavior.
 * 
 * @author Savva_Kodeikin
 *
 */
public interface IFrogBehavior {
	/**
	 * The snake invokes this method when it eats a frog.
	 * 
	 * @param frog eaten frog
	 * @param snake snake instance
	 */
	void frogEatenAction(Frog frog, Snake snake);
}
