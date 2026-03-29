package wad;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Entry point for the WAD (Word-A-Day) application.
 * 
 * Initializes and displays the main application window, centered on the screen.
 * Handles command-line arguments to accept a custom configuration file path.
 * If no configuration file is specified, uses the default path.
 * 
 * Supported usage:
 * <ul>
 *   <li>{@code java -jar wad.jar} - Uses default configuration</li>
 *   <li>{@code java -jar wad.jar path/to/config.properties} - Uses custom configuration</li>
 * </ul>
 * 
 * @since 1.0
 */
public class Wad {
	/**
	 * Constructs the WAD application with the given configuration path.
	 * 
	 * Creates the main application window and centers it on the screen,
	 * adjusting for screen boundaries if necessary. The window will not
	 * close on the default close button click (requires graceful shutdown).
	 * 
	 * @param configPath the path to the wad.properties configuration file
	 * @throws RuntimeException if the configuration file cannot be loaded
	 */
	public Wad(String configPath) {
		JFrame frame = new FrameMain(configPath);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Main entry point for the WAD application.
	 * 
	 * Accepts optional command-line argument for configuration file path:
	 * <ul>
	 *   <li>No arguments: Uses default configuration at "C:\\Data\\Doc\\Dict\\wad.properties"</li>
	 *   <li>One argument: Uses specified configuration file path</li>
	 * </ul>
	 * 
	 * Handles any exceptions during initialization by printing error message
	 * and exiting with code 1.
	 * 
	 * @param args optional command-line arguments (max 1: config file path)
	 */
	public static void main(String[] args) {
		String configPath = "C:\\Data\\Doc\\Dict\\wad.properties";
		if (args.length > 0) {
			configPath = args[0];
		}
		
		try {
			new Wad(configPath);
		} catch (Exception e) {
			System.err.println("Fatal error starting WAD: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
