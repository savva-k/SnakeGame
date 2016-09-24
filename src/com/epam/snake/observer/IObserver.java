/**
 * 
 */
package com.epam.snake.observer;

import com.epam.snake.model.Cell;

/**
 * Implement this interface if you want to be an observer.
 * 
 * @author Savva_Kodeikin
 *
 */
public interface IObserver {
	/**
	 * The action that observer have to do when an observable object has
	 * changed.
	 * 
	 * @param cell
	 */
	void refreshCell(Cell cell);

	/**
	 * Implement refreshing view elements like buttons, labels, etc here
	 */
	void refreshGUIElements();
}
