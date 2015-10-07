package WumpusWorld;

import javax.swing.SwingUtilities;

import WumpusWorld.gui.MainWindow;

public class Main {
	public static void main(String[] args) {
//		for(int i = 0 ; i < 10 ; i++) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					MainWindow s = new MainWindow();
					s.setVisible(true);
				}
			});
//		}
	}
}
