package main;

import javax.swing.JFrame; // Importing the JFrame class from the javax.swing package

public class Main {

	public static void main(String[] args) { // Main method, the entry point of the program
		JFrame window = new JFrame(); // Creating a new JFrame object named "window"
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setting the default close operation when the window is
																// closed
		window.setResizable(false); // Making the window not resizable
		window.setTitle("Five Minutes of Hell"); // Setting the title of the window to "Five Minutes of Hell"

		GamePanel gamePanel = new GamePanel(); // Creating a new GamePanel object named "gamePanel"
		window.add(gamePanel); // Adding the gamePanel to the content pane of the window

		window.pack(); // Packing the window, which sizes it based on the preferred sizes of its
						// subcomponents

		window.setLocationRelativeTo(null); // Centering the window on the screen
		window.setVisible(true); // Making the window visible

		gamePanel.setupGame(); // Calling the setupGame method on the gamePanel, presumably to initialize the
								// game
		gamePanel.startGameThread(); // Calling the startGameThread method on the gamePanel, presumably to start a
										// game-related thread

	}
}
