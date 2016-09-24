/**
 * 
 */
package com.epam.snake.model.zoo.frog_behavior;

import com.epam.snake.model.zoo.Frog;
import com.epam.snake.model.zoo.Snake;

/**
 * The red frog behavior class.
 * 
 * @author Savva_Kodeikin
 *
 */
public class RedFrogBehavior implements IFrogBehavior {

	private static final String NAME = "Red frog";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void frogEatenAction(Frog frog, Snake snake) {
		frog.kill();
		snake.decreaseLength();
		snake.getModel().increaseScore(2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return NAME;
	}

}
