/**
 * 
 */
package com.epam.snake.model.zoo.frog_behavior;

import com.epam.snake.model.zoo.Frog;
import com.epam.snake.model.zoo.Snake;

/**
 * The blue frog behavior class.
 * 
 * @author Savva_Kodeikin
 *
 */
public class BlueFrogBehavior implements IFrogBehavior {

	private static final String NAME = "Blue frog";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void frogEatenAction(Frog frog, Snake snake) {
		snake.kill();
		frog.kill();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return NAME;
	}

}
