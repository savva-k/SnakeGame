package com.epam.snake.model;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.epam.snake.model.zoo.FrogPool;
import com.epam.snake.model.zoo.Snake;
import com.epam.snake.observer.IObservable;
import com.epam.snake.observer.IObserver;

/**
 * The main application class that creates and controls all threads and all game
 * processes.
 * 
 * @author Savva_Kodeikin
 *
 */
public class SnakeGame implements IObservable {

	private static final int DEFAULT_SIZE_M = 10;
	private static final int DEFAULT_SIZE_N = 10;
	private static final int MIN_SIZE_M = 5;
	private static final int MAX_SIZE_M = 250;
	private static final int MIN_SIZE_N = 5;
	private static final int MAX_SIZE_N = 250;
	private static final int DEFAULT_SNAKE_LENGTH = 3;
	private static final int MIN_SNAKE_LENGTH = 2; // max = sizeM
	private static final int DEFAULT_SNAKE_SLEEP_TIME = 150;
	private static final int MIN_SNAKE_SLEEP_TIME = 5;
	private static final int MAX_SNAKE_SLEEP_TIME = 2000;
	private static final int DEFAULT_FROGS_COUNT = DEFAULT_SIZE_M
			* DEFAULT_SIZE_N / 5;
	private static final int MIN_FROGS_COUNT = 1; // max = sizeM x sizeN -
													// snakeLength

	private static final String FROGS_COUNT_PARAM_NAME = "Frogs Count";
	private static final String SNAKE_SLEEP_TIME_PARAM_NAME = "Snake Sleep Time";
	private static final String SNAKE_LENGTH_PARAM_NAME = "Snake Length";
	private static final String SIZE_N_PARAM_NAME = "Size N";
	private static final String SIZE_M_PARAM_NAME = "Size M";

	private static final Logger logger = Logger.getLogger(SnakeGame.class);

	private int sizeM = DEFAULT_SIZE_M;
	private int sizeN = DEFAULT_SIZE_N;
	private int snakeLength = DEFAULT_SNAKE_LENGTH;
	private int snakeSleepTime = DEFAULT_SNAKE_SLEEP_TIME;
	private int frogsCount = DEFAULT_FROGS_COUNT;

	private Thread snakeThread;
	private FrogPool frogPool;

	private ArrayList<IObserver> observers = new ArrayList<IObserver>();
	private Snake snake;
	private GameMap map;
	private GameStatus status = GameStatus.GAME_NOT_STARTED;
	private AtomicInteger score = new AtomicInteger(0);
	private volatile boolean paused = false;
	private volatile boolean stopped = true;

	/**
	 * Snake game constructor
	 */
	public SnakeGame() {
		setStatus(GameStatus.GAME_NOT_STARTED);
	}

	/**
	 * This method initializes the game instance.
	 * 
	 * @param sizeM game map width
	 * @param sizeN game map height
	 * @param length snake length
	 * @param sleepTime snake sleep time
	 * @param frogsCount count of frogs
	 */
	public void init(String sizeM, String sizeN, String length,
			String sleepTime, String frogsCount) {
		this.sizeM = handleArgument(sizeM, SIZE_M_PARAM_NAME, DEFAULT_SIZE_M,
				MIN_SIZE_M, MAX_SIZE_M);
		this.sizeN = handleArgument(sizeN, SIZE_N_PARAM_NAME, DEFAULT_SIZE_N,
				MIN_SIZE_N, MAX_SIZE_N);
		this.snakeLength = handleArgument(length, SNAKE_LENGTH_PARAM_NAME,
				DEFAULT_SNAKE_LENGTH, MIN_SNAKE_LENGTH, this.sizeM);
		this.snakeSleepTime = handleArgument(sleepTime,
				SNAKE_SLEEP_TIME_PARAM_NAME, DEFAULT_SNAKE_SLEEP_TIME,
				MIN_SNAKE_SLEEP_TIME, MAX_SNAKE_SLEEP_TIME);
		this.frogsCount = handleArgument(frogsCount, FROGS_COUNT_PARAM_NAME,
				DEFAULT_FROGS_COUNT, MIN_FROGS_COUNT, this.sizeM * this.sizeN
						- snakeLength);
		map = new GameMap(this, this.sizeM, this.sizeN);
		logger.info(String
				.format("The snake game started with parameters: "
						+ "map_width=%s, map_height=%s, snake_length=%s, frogs_count=%s,"
						+ " snake_sleep_time=%s", this.sizeM, this.sizeN,
						this.snakeLength, this.frogsCount, this.snakeSleepTime));
	}

	/**
	 * Returns horizontal size of a map in cells.
	 * 
	 * @return int horizontal size of a map in cells.
	 */
	public int getSizeM() {
		return sizeM;
	}

	/**
	 * Returns vertical size of a map in cells.
	 * 
	 * @return int vertical size of a map in cells.
	 */
	public int getSizeN() {
		return sizeN;
	}

	/**
	 * Returns status of the game.<br />
	 * It could be GAME_NOT_STARTED, GAME_IN_PROGRESS, GAME_PAUSED, GAME_OVER.
	 * 
	 * @return GameStatus status of the game.
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * Sets status of the game.<br />
	 * It could be GAME_NOT_STARTED, GAME_IN_PROGRESS, GAME_PAUSED, GAME_OVER.
	 * 
	 * @param status to set
	 */
	public void setStatus(GameStatus status) {
		this.status = status;
		refreshGUIElements();
	}

	/**
	 * This method starts the game, runs threads, etc.
	 */
	public void start() {
		logger.debug("Entering start() method");
		score.set(0);

		stopped = false;
		paused = false;

		setStatus(GameStatus.GAME_INIT);

		map.resetMap();

		snake = new Snake(this, snakeLength, snakeSleepTime);
		snakeThread = new Thread(snake);

		frogPool = new FrogPool(this, frogsCount);
		frogPool.start();

		map.update();

		snakeThread.start();

		setStatus(GameStatus.GAME_IN_PROGRESS);
		logger.debug("Leaving start() method");
	}

	/**
	 * Stopping the game.
	 */
	public void stop() {
		logger.debug("Entering stop() method");
		setStatus(GameStatus.GAME_STOPPING);
		stopped = true;
		paused = false;

		snake.kill();
		frogPool.killAll();

		setStatus(GameStatus.GAME_OVER);
		logger.debug("Leaving stop() method");
	}

	/**
	 * Pausing the game.
	 */
	public void pause() {
		logger.debug("Entering pause() method");

		if (paused) {
			setStatus(GameStatus.GAME_IN_PROGRESS);
			frogPool.notifyFrogs();
			snake.wakeUp();
		} else {
			setStatus(GameStatus.GAME_PAUSED);
		}

		paused = !paused;
		logger.debug("Leaving pause() method");
	}

	/**
	 * @return int game score
	 */
	public int getScore() {
		return score.get();
	}

	/**
	 * @return FrogPool instance
	 */
	public FrogPool getFrogPool() {
		return frogPool;
	}

	/**
	 * Returns an instance of the GameMap
	 * 
	 * @return GameMap map
	 */
	public GameMap getMap() {
		return map;
	}

	/**
	 * Increasing game score by a concrete number.
	 * 
	 * @param i how much points we got.
	 */
	public void increaseScore(int i) {
		int value = score.get();
		score.set(value + i);
	}

	/**
	 * Increasing game score by 1.
	 */
	public void increaseScore() {
		score.incrementAndGet();
	}

	/**
	 * @return boolean is the game paused?
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * @return boolean is the game stopped?
	 */
	public boolean isStopped() {
		return stopped;
	}

	/**
	 * @return Snake instance.
	 */
	public Snake getSnake() {
		return snake;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void repaintCell(Cell cell) {
		for (IObserver observer : observers) {
			observer.refreshCell(cell);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refreshGUIElements() {
		for (IObserver observer : observers) {
			observer.refreshGUIElements();
		}
	}

	/*
	 * This method parses a string value into an int value, if it can't parse,
	 * it returns the default value. If parsing passed without any errors, we're
	 * checking if the value we got >= minSize and <= maxSize.
	 */
	private int handleArgument(String value, String paramName,
			int defaultValue, int minValue, int maxValue) {
		logger.debug(String.format(
				"Entering handleArgument(%s, %s, %s, %s, %s) method", value,
				paramName, defaultValue, minValue, maxValue));
		int result;

		if (value == null) {
			return defaultValue;
		}

		try {
			int test = Integer.parseInt(value);

			if (test >= minValue && test <= maxValue) {
				result = test;
			} else {
				logger.info(String
						.format("Incorrect \"%s\" value (\"%s\"), the correct range is from %s to %s. Using the default value (%s)",
								paramName, value, minValue, maxValue,
								defaultValue));
				result = defaultValue;
			}
		} catch (NumberFormatException e) {
			logger.info(String
					.format("Illegal argument \"%s\" (\"%s\"), using the default value (%s)",
							paramName, value, defaultValue));
			result = defaultValue;
		}

		logger.debug(String.format(
				"Leaving handleArgument() method, the result is %s", result));
		return result;
	}

}
