package com.epam.snake;

import java.util.Map;

import com.epam.snake.internationalization.ResourceManager;
import com.epam.snake.model.SnakeGame;
import com.epam.snake.view.SwingGui;

/**
 * Here the Snake Game starts.
 * 
 * @author Savva_Kodeikin
 *
 */
public class SnakeGameStart {

	private static final String WIDTH_PARAM = "-m";
	private static final String HEIGHT_PARAM = "-n";
	private static final String LENGTH_PARAM = "-l";
	private static final String DELAY_PARAM = "-d";
	private static final String FROGS_COUNT_PARAM = "-f";
	private static final String HELP_PARAM = "--help";
	private static final String HELP_SHORT_PARAM = "-h";

	private static final String HELP_TEXT = String.format(
			ResourceManager.getString("help_text"), LENGTH_PARAM, WIDTH_PARAM,
			HEIGHT_PARAM, DELAY_PARAM, FROGS_COUNT_PARAM, LENGTH_PARAM,
			WIDTH_PARAM, HEIGHT_PARAM, DELAY_PARAM, FROGS_COUNT_PARAM);

	/**
	 * Just main method. Parsing the arguments with the Argument parser and
	 * starting the game.
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		String[] needArgs = new String[] { LENGTH_PARAM, DELAY_PARAM,
				WIDTH_PARAM, HEIGHT_PARAM, FROGS_COUNT_PARAM, HELP_PARAM,
				HELP_SHORT_PARAM };

		Map<String, String> arguments = ArgumentParser.parseArguments(args,
				needArgs);

		if (arguments.containsKey(HELP_PARAM)
				|| arguments.containsKey(HELP_SHORT_PARAM)) {

			System.out.println(HELP_TEXT);

		} else {

			SnakeGame snakeGame = new SnakeGame();

			snakeGame.init(arguments.get(WIDTH_PARAM),
					arguments.get(HEIGHT_PARAM), arguments.get(LENGTH_PARAM),
					arguments.get(DELAY_PARAM),
					arguments.get(FROGS_COUNT_PARAM));

			new SwingGui().start(snakeGame);

		}

	}
}
