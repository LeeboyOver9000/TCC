package WumpusWorld.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import WumpusWorld.Coordinate;
import WumpusWorld.Maze;
import WumpusWorld.WumpusLabyrinth;
import WumpusWorld.agents.Hunter;
import WumpusWorld.sys.Settings;

public class Game extends JPanel implements Runnable
{
	private WumpusLabyrinth environment;

	private int x = Settings.SIZE_X;

	private int y = Settings.SIZE_Y;
	
	private boolean isVisibleCells = false;
	
	private boolean isRunning;
	
	public Game() {
		environment = new WumpusLabyrinth("The-Wumpus-World", "localhost", "2424", this);
		start();
	}

	private void doDrawing(Graphics g) {
		// Define the line's border
		g.setColor(Color.BLACK);

		drawGrid(g);
		drawGold(g);
		drawPit(g);
		drawWumpus(g);
		drawStenchAndBreeze(g);
		drawAgent(g);
		
		if( !isVisibleCells ) {
			drawRectangle(g);
		}
	}
	
	private void drawRectangle(Graphics g) {
		Dimension size = getSize();
		
		int hImage = size.height / y;
		int wImage = size.width / x;
		
		Maze maze = environment.getMaze();
		Hunter agent = (Hunter) environment.getAgent("Hunter");
		
		if( agent != null ) {
			for (int i = 0; i < maze.getMazeSize() ; i++)
				for (int j = 0; j < maze.getMazeSize() ; j++)
					if ( !agent.getKnowledgeBase().isVisited(i, j))
						g.fillRect(i*wImage+3, j*hImage+3, wImage-6, hImage-6);					
		}		
	}
	
	private void drawImage(Graphics g, File file, Coordinate location) {
		drawImage( g,file, location.getX(),location.getY() );
	}

	private void drawImage(Graphics g, File file, int posX, int posY) {
		Dimension size = getSize();

		if (posX == -1 || posY == -1)
			return;

		try {
			BufferedImage image = ImageIO.read(file);

			int hImage = size.height / y;
			int wImage = size.width / x;
			int x0 = posX * wImage;
			int y0 = posY * hImage;

			g.drawImage(image, x0, y0, wImage, hImage, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void drawText(Graphics g, String text, int posX, int posY, int offsetY) {
		Dimension size = getSize();

		if (posX == -1 || posY == -1)
			return;

		int hImage = size.height / y;
		int wImage = size.width / x;
		int x0 = posX * wImage;
		int y0 = posY * hImage;

		g.drawString(text, x0 + 20, y0 + offsetY);	
	}
	
	private void drawGold(Graphics g) {
		Maze maze = environment.getMaze();
		
		for( int i = 0 ; i < maze.getMazeSize() ; i++ )
			for( int j = 0 ; j < maze.getMazeSize() ; j++)
				if ( maze.getRoom(i, j).isGold() )
					drawImage(g, new File("res/gold.gif"), maze.getRoom(i, j).getCoordinate() );
	}

	private void drawAgent(Graphics g) {
		Hunter agent = (Hunter) environment.getAgent("Hunter");
		
		if( agent != null) {	
			drawImage(g, new File("res/agent.png"), agent.getKnowledgeBase().getCurrentRoom().getCoordinate());
		}
	}
	
	private void drawWumpus(Graphics g) {
		Maze maze = environment.getMaze();
		
		for( int i = 0 ; i < maze.getMazeSize() ; i++ )
			for( int j = 0 ; j < maze.getMazeSize() ; j++)
				if ( maze.getRoom(i, j).isWumpus() )
					drawImage(g, new File("res/wumpus.gif"), maze.getRoom(i, j).getCoordinate() );			
	}
	
	private void drawPit(Graphics g) {
		Maze maze = environment.getMaze();
		
		for( int i = 0 ; i < maze.getMazeSize() ; i++ )
			for( int j = 0 ; j < maze.getMazeSize() ; j++)
				if ( maze.getRoom(i, j).isPit() )
					drawImage(g, new File("res/pit.gif"), maze.getRoom(i, j).getCoordinate());
	}
	
	private void drawStenchAndBreeze(Graphics g) {
		Maze maze = environment.getMaze();
		
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if ( maze.getRoom(i, j).isStench() ) {
					g.setFont( new Font("Stench", Font.BOLD, 16));
					drawText(g, "Stench", i, j, 20);
				}
				if ( maze.getRoom(i, j).isBreeze() ) {
					g.setFont( new Font("Breeze", Font.BOLD, 16));
					drawText(g, "Breeze", i, j, 40);
				}
			}
		}
	}
	
	private void drawGrid(Graphics g) {
		Dimension size = getSize();

		// Draw horizontal lines
		int horizontal = size.height / y;

		for (int i = 1; i < Settings.SIZE_Y; i++) {
			g.drawLine(0, i * horizontal, size.width, i * horizontal);
		}

		// Draw vertical lines
		int vertical = size.width / x;

		for (int i = 1; i < x; i++) {
			g.drawLine(i * vertical, 0, i * vertical, size.height);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	@Override
	public void run() {
		while(isRunning){
			try {
				Thread.sleep(3 * 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			step();
		}
	}

	public void step() {
		repaint();	
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public void start() {
		environment.start();
		isRunning = true;
		repaint();
	}
	
	public void setVisibleCells(boolean isVisible){
		isVisibleCells = isVisible;
		repaint();
	}
	public boolean isVisibleCells(){
		return isVisibleCells;
	}
}
