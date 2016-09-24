/**
 * 
 */
package com.epam.snake.model.zoo;

import java.util.Deque;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.epam.snake.model.SnakeGame;

/**
 * This is a Frog Pool class, that creates frogs until it doesn't have enough.
 * 
 * @author Savva_Kodeikin
 *
 */
public class FrogPool extends Thread {

	/**
	 * 
	 */
	private static final int KILLING_THREADS_TIMEOUT = 15;
	private static final int BLUE_FROGS_MAX_COUNT = 3;
	private static final int RED_FROGS_MAX_COUNT = 10;
	private static final int FROG_CLASSES_COUNT = 3;
	private static final int BLUE_FROG_CODE = 0;
	private static final int RED_FROG_CODE = 1;

	private static final Logger logger = Logger.getLogger(FrogPool.class);

	private int redFrogCounter = 0;
	private int blueFrogCounter = 0;

	private ExecutorService executor;
	private int frogsCount;
	private Deque<Frog> frogs;
	private SnakeGame snakeGame;
	private boolean alive = true;

	/**
	 * The FrogPool constructor
	 * 
	 * @param snakeGame model
	 * @param frogsCount count of frogs
	 */
	public FrogPool(SnakeGame snakeGame, int frogsCount) {
		executor = Executors.newFixedThreadPool(frogsCount);
		this.snakeGame = snakeGame;
		this.frogsCount = frogsCount;
		frogs = new ConcurrentLinkedDeque<Frog>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		while (alive) {
			if (snakeGame.isStopped()) {
				killAll();
				break;
			}

			if (frogs.size() < frogsCount) {
				Frog frog = getRandomFrog();
				frogs.add(frog);
				executor.execute(frog);
			}
		}
		destroyThreads();
	}

	/**
	 * This method the snake snake will invoke when it eats a frog.
	 * 
	 * @param snake snake that eats
	 * @param frog frog that is eaten
	 */
	public void eatFrog(Snake snake, Frog frog) {
		logger.debug(String.format("Entering eatFrog(snake, frog(%s)) method",
				frog.getClass().getSimpleName()));

		frog.getEatenBehavior().frogEatenAction(frog, snake);
		frogs.remove(frog);

		logger.debug(String
				.format("Leaving eatFrog() method, semaphore released, frogs.size()=%s",
						frogs.size()));
	}

	/**
	 * Kills all frogs.
	 */
	public void killAll() {
		logger.debug("Entering killAll() method");

		alive = false;

		logger.debug("Leaving killAll() method, all frogs should be killed");
	}

	/**
	 * The method returns true if all frogs are dead
	 * 
	 * @return true if all frogs are dead
	 */
	public boolean areAllFrogsDead() {
		return executor.isTerminated();
	}

	/**
	 * Waking up the frogs after the pause.
	 */
	public void notifyFrogs() {
		logger.debug("Entering notifyFrogs() method");

		for (Frog frog : frogs) {
			frog.wakeUp();
		}

		logger.debug("Leaving notifyFrogs() method, all frogs should be notified");
	}

	/*
	 * This method returns random Frog instance, but it also checks if it can
	 * return some types of frogs.
	 */
	private Frog getRandomFrog() {
		Random random = new Random();
		Frog frog = null;

		while (frog == null) {
			int rnd = random.nextInt(FROG_CLASSES_COUNT);
			switch (rnd) {
			case BLUE_FROG_CODE:
				if (blueFrogCounter < BLUE_FROGS_MAX_COUNT) {
					frog = new BlueFrog(snakeGame);
					blueFrogCounter++;
				}
				break;
			case RED_FROG_CODE:
				if (redFrogCounter < RED_FROGS_MAX_COUNT) {
					frog = new RedFrog(snakeGame);
					redFrogCounter++;
				}
				break;
			default:
				frog = new GreenFrog(snakeGame);
				break;
			}
		}
		return frog;
	}

	/*
	 * Killing all threads and frogs...
	 */
	private void destroyThreads() {
		logger.debug("Entering destroyThreads() method, frogs count = "
				+ frogs.size());

		executor.shutdown();

		for (Frog frog : frogs) {
			frog.wakeUp();
			frog.kill();
		}

		try {
			if (executor.awaitTermination(KILLING_THREADS_TIMEOUT,
					TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}

		logger.debug("Leaving destroyThreads() method, frogs count = "
				+ frogs.size());
	}

}
