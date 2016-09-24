/**
 * 
 */
package com.epam.snake.model;

import java.awt.Point;
import java.util.Observable;

/**
 * A map of a game, M x N cells.
 * 
 * @author Savva_Kodeikin
 *
 */
public class GameMap extends Observable {

	private Cell[][] map;
	private SnakeGame snakeGame;
	private int m;
	private int n;

	/**
	 * Create a new map with M x N cells size.
	 * 
	 * @param snakeGame game model
	 * @param m width
	 * @param n height
	 */
	public GameMap(SnakeGame snakeGame, int m, int n) {
		map = new Cell[n][m];
		this.snakeGame = snakeGame;
		this.setM(m);
		this.setN(n);

		resetMap();
	}

	/**
	 * @return the m
	 */
	public int getM() {
		return m;
	}

	/**
	 * @param m the m to set
	 */
	public void setM(int m) {
		this.m = m;
	}

	/**
	 * @return the n
	 */
	public int getN() {
		return n;
	}

	/**
	 * @param n the n to set
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * Get the cell at [i,j]
	 * 
	 * @param m - "x" cell position
	 * @param n - "y" cell position
	 * @return cell at [m, n]
	 */
	public Cell getCell(int m, int n) {
		Cell result = null;

		if (map.length > 0) {
			if (n < map.length && m < map[0].length) {
				result = map[n][m];
			}
		}

		return result;
	}

	/**
	 * Get the cell at Point point
	 * 
	 * @param point Point of the cell
	 * @return cell at specified point
	 */
	public Cell getCell(Point point) {
		return getCell(point.x, point.y);
	}

	/**
	 * Reset all cells in the map to new Cell();
	 */
	public void resetMap() {

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				Cell cell = new Cell(snakeGame, j, i);
				map[i][j] = cell;

			}
		}
	}

	/**
	 * Update all cells in the map (and so repaint all the map)
	 */
	public void update() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				map[i][j].update();
			}
		}
	}
}
