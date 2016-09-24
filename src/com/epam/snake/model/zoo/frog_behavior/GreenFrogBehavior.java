/**
 * 
 */
package com.epam.snake.model.zoo.frog_behavior;

import com.epam.snake.model.zoo.Frog;
import com.epam.snake.model.zoo.Snake;

/**
 * The green frog behavior class.
 * 
 * @author Savva_Kodeikin
 *
 */
public class GreenFrogBehavior implements IFrogBehavior {

	private static final String NAME = "Green Frog";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void frogEatenAction(Frog frog, Snake snake) {
		frog.kill();
		snake.getModel().increaseScore();
		snake.increaseLength();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return NAME;
	}

}
