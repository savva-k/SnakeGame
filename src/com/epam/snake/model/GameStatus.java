/**
 * 
 */
package com.epam.snake.model;

import com.epam.snake.internationalization.ResourceManager;

/**
 * Enumeration of game statuses.
 * 
 * @author Savva_Kodeikin
 *
 */
public enum GameStatus {
	/**
	 * Game is not started status
	 */
	GAME_NOT_STARTED {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getStatus() {
			return ResourceManager.getString("press_start_msg");
		}
	},

	/**
	 * Game in progress status
	 */
	GAME_IN_PROGRESS {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getStatus() {
			return ResourceManager.getString("score_msg");
		}
	},

	/**
	 * Game is paused status
	 */
	GAME_PAUSED {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getStatus() {
			return ResourceManager.getString("game_paused_msg");
		}
	},

	/**
	 * Game is over status
	 */
	GAME_OVER {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getStatus() {
			return ResourceManager.getString("game_over_msg");
		}
	},

	/**
	 * Game is initializing
	 */
	GAME_INIT {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getStatus() {
			return ResourceManager.getString("game_init_msg");
		}
	},

	/**
	 * Game is stopping
	 */
	GAME_STOPPING {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getStatus() {
			return ResourceManager.getString("game_stopping_msg");
		}

	};

	/**
	 * Get the game status.
	 * 
	 * @return String status of the game
	 */
	public abstract String getStatus();
}
