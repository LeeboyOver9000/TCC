package WumpusWorld;

import javax.swing.SwingUtilities;

import WumpusWorld.gui.MainWindow;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow s = new MainWindow();
				s.setVisible(true);
			}
		});
	}
}
