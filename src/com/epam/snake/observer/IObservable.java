/**
 * 
 */
package com.epam.snake.observer;

import com.epam.snake.model.Cell;

/**
 * Just a marker interface because all methods in this case are static.
 * 
 * @author Savva_Kodeikin
 *
 */
public interface IObservable {

	/**
	 * Add the specified observer to the observers list.
	 * 
	 * @param observer
	 */
	void addObserver(IObserver observer);

	/**
	 * Remove the specified observer from the observers list.
	 * 
	 * @param observer
	 */
	void removeObserver(IObserver observer);

	/**
	 * Notify observers they should repaint the specified cell.
	 * 
	 * @param cell
	 */
	void repaintCell(Cell cell);

	/**
	 * Notify observers they should refresh gui.
	 */
	void refreshGUIElements();

}
