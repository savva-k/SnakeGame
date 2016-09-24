package com.epam.snake.view;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.epam.snake.controller.ButtonController;
import com.epam.snake.controller.WindowMoveController;
import com.epam.snake.internationalization.ResourceManager;
import com.epam.snake.model.SnakeGame;

/**
 * The main view class, initializing GUI, creating SwingGameMap and putting it
 * into the JFrame.
 * 
 * @author Savva_Kodeikin
 *
 */
public class SwingGui {

	/**
	 * Start game action command for the button
	 */
	public static final String START_GAME_COMMAND = "start";

	/**
	 * Pause game action command for the button
	 */
	public static final String PAUSE_GAME_COMMAND = "pause";

	/**
	 * Stop game action command for the button
	 */
	public static final String STOP_GAME_COMMAND = "stop";

	/**
	 * Width and height of a cell in pixels
	 */
	public static final int SCALE = 60;

	private static final String GAME_NAME = ResourceManager
			.getString("game_name");
	private static final String START_GAME_LABEL = ResourceManager
			.getString("start_button");
	private static final String PAUSE_GAME_LABEL = ResourceManager
			.getString("pause_button");
	private static final String STOP_GAME_LABEL = ResourceManager
			.getString("stop_button");
	private static final String ICON_PATH = "resources/icon.png";

	private SnakeGame snakeGame;
	private JButton startBtn;
	private JButton pauseBtn;
	private JButton stopBtn;
	private JLabel resultLabel;
	private JFrame frame;

	/**
	 * The GUI constructor. Here the main window is initializing.
	 */
	public SwingGui() {
		frame = new JFrame(GAME_NAME);
		frame.addComponentListener(new WindowMoveController(frame));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon icon = new ImageIcon(ICON_PATH);
		frame.setIconImage(icon.getImage());

		startBtn = new JButton(START_GAME_LABEL);
		pauseBtn = new JButton(PAUSE_GAME_LABEL);
		stopBtn = new JButton(STOP_GAME_LABEL);

		resultLabel = new JLabel();

		frame.setResizable(false);
		frame.setVisible(true);
	}

	/**
	 * Initializing the game view
	 * 
	 * @param snakeGame - game model
	 */
	public void start(SnakeGame snakeGame) {
		this.snakeGame = snakeGame;

		JPanel content = prepareMainPanel();

		frame.setContentPane(content);
		frame.pack();

		refreshButtons();
		refreshLabel();
	}

	/**
	 * Refresh GUI buttons state
	 */
	public void refreshButtons() {
		switch (snakeGame.getStatus()) {
		case GAME_IN_PROGRESS:
			setButtonsCondition(false, true, true);
			break;
		case GAME_NOT_STARTED:
			setButtonsCondition(true, false, false);
			break;
		case GAME_OVER:
			setButtonsCondition(true, false, false);
			break;
		case GAME_PAUSED:
			setButtonsCondition(true, false, true);
			break;
		case GAME_INIT:
			setButtonsCondition(false, false, false);
			break;
		case GAME_STOPPING:
			setButtonsCondition(false, false, false);
			break;
		default:
			break;
		}
	}

	/**
	 * Refresh label text considering the game status
	 */
	public void refreshLabel() {
		resultLabel.setText(String.format(snakeGame.getStatus().getStatus(),
				snakeGame.getScore()));
	}

	/**
	 * Set buttons state
	 * 
	 * @param start
	 * @param pause
	 * @param stop
	 */
	private void setButtonsCondition(boolean start, boolean pause, boolean stop) {
		startBtn.setEnabled(start);
		pauseBtn.setEnabled(pause);
		stopBtn.setEnabled(stop);
	}

	/**
	 * Get JPanel with placed buttons
	 * 
	 * @return JPanel with buttons
	 */
	private JPanel prepareButtons() {
		startBtn.setActionCommand(START_GAME_COMMAND);
		pauseBtn.setActionCommand(PAUSE_GAME_COMMAND);
		stopBtn.setActionCommand(STOP_GAME_COMMAND);

		ButtonController btnController = new ButtonController(snakeGame);
		startBtn.addActionListener(btnController);
		pauseBtn.addActionListener(btnController);
		stopBtn.addActionListener(btnController);

		JPanel buttons = new JPanel();
		buttons.add(startBtn);
		buttons.add(pauseBtn);
		buttons.add(stopBtn);

		return buttons;
	}

	/**
	 * Get JPanel with the label
	 * 
	 * @return JPanel with the label
	 */
	private JPanel prepareLabel() {
		JPanel labelPane = new JPanel();
		labelPane.add(resultLabel);
		return labelPane;
	}

	/**
	 * Get main content pane
	 * 
	 * @return JPanel with buttons, game field and label
	 */
	private JPanel prepareMainPanel() {
		SwingGameMap swingGameMap = new SwingGameMap(this, snakeGame);
		swingGameMap.init();

		JPanel content = new JPanel(null);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.add(prepareButtons());
		content.add(swingGameMap);
		content.add(prepareLabel());

		return content;
	}

}
