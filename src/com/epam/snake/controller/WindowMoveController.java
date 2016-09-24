/**
 * 
 */
package com.epam.snake.controller;

import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author Savva_Kodeikin
 *
 */
public class WindowMoveController extends ComponentAdapter {

	private Frame frame;

	/**
	 * Window move controller
	 * 
	 * @param frame a frame to repaint when it was moved
	 */
	public WindowMoveController(Frame frame) {
		this.frame = frame;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		frame.repaint();
	}
}
