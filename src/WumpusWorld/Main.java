package WumpusWorld;

import WumpusWorld.gui.MainWindow;

import javax.swing.SwingUtilities;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class Main 
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				MainWindow s = new MainWindow();
				s.setVisible(true);
			}
		});
	}
}
