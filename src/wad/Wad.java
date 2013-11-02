package wad;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Wad {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		String configPath = "C:\\Data\\Doc\\Dict\\wad.properties";
		if (args.length > 0) {
			configPath = args[0];
		}
		new Wad(configPath);
	}
}
