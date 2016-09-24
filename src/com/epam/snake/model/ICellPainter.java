/**
 * 
 */
package com.epam.snake.model;

import java.awt.Graphics2D;

/**
 * You can creating your own game painter by implementing this interface.
 * 
 * @author Savva_Kodeikin
 *
 */
public interface ICellPainter {
	/**
	 * Draw a cell on a map.
	 * 
	 * @param cell - the cell which is to be drawn
	 */
	void drawCell(Cell cell);

	/**
	 * Draw an animal that contains a cell or nothing if it contains null.
	 * 
	 * @param cell - the cell that contains the animal
	 */
	void drawAnimal(Cell cell);

	/**
	 * Set the graphics component on which you will draw the content.
	 * 
	 * @param graphics Graphics2D
	 */
	void setGraphics(Graphics2D graphics);
}
