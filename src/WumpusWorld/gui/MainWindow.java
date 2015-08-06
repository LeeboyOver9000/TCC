package WumpusWorld.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import WumpusWorld.sys.Settings;

public class MainWindow extends JFrame implements ItemListener 
{
	private Game game;
	
	private JCheckBox chkShow;
	
	public MainWindow() {
		this.game = new Game();
		initUI();
	}

	private void initUI() {
		//Settings
		setTitle("The World of Wumpus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);

		createToolbar();
		
		Thread thread = new Thread(game);
		add(game);
		
		thread.start();
	}
	

	private void createToolbar() {

		chkShow = new JCheckBox("Visible Cells");
		
		chkShow.setFocusable(false);
		chkShow.setSelected(game.isVisibleCells());
		chkShow.addItemListener(this);
						
		JToolBar toolBar = new JToolBar();
		
		toolBar.add(chkShow);

		add(toolBar, BorderLayout.NORTH);
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		Object source = event.getItemSelectable();

		if (source == chkShow) {
			boolean isVisible = event.getStateChange() == 1 ? true : false;
			game.setVisibleCells(isVisible);
		}
	}
}
