package org.newdawn.tilemap;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.newdawn.tilemap.AStar.Cell;

/**
 * A very simple example to illustrate how simple tile maps can be
 * used for basic collision. This particular technique only works
 * in certain circumstances and for small time updates. However, this 
 * fits many maze based games perfectly. 
 * 
 * @author Kevin Glass
 */
public class Game extends Canvas implements KeyListener {
	/** The sprite we're going to use for our player */
	private Image sprite;
	/** The buffered strategy used for accelerated rendering */
	private BufferStrategy strategy;

	/** Current value of the screen size X */
	private int screenSizeX = 400;
	/** Current value of the screen size Y */
	private int screenSizeY = 420;
	
	/** True if the left key is currently pressed */
	private boolean left;
	/** True if the right key is currently pressed */
	private boolean right;
	/** True if the up key is currently pressed */
	private boolean up;
	/** True if the down key is currently pressed */
	private boolean down;
	private boolean begin;
	private boolean end;
	private boolean clear;

	/** The map our player will wander round */
	private Map map;
	/** The player entity that will be controlled with cursors */
	private Entity player;
	
	/**
	 * Create the simple game - this also starts the game loop
	 */
	public Game() {
		// right, I'm going to explain this in detail since it always seems to 
		// confuse. 
		//
		// Here we'e're doing this by using 
		// the class loader. The reason we do this is so that when developing you
		// can use local files, but when distributing to your players you can 
		// package everything up into jar files which can be handled by webstart

		// However, this means that when you run the program the directory above
		// "res" must be in your classpath so the resources can be found.
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("res/sprite.gif");
			if (url == null) {
				System.err.println("Unable to find sprite: res/sprite.gif");
				System.exit(0);
			}
			sprite = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Unable to load sprite: res/sprite.gif");
			System.exit(0);
		}
		
		// create the AWT frame. Its going to not being  resizable
		// this just gives us less to account for
		Frame frame = new Frame("Robot Navigation Tests");
		frame.setLayout(null);
		setBounds(0, 0, screenSizeX, screenSizeY);
		frame.add(this);
		frame.setSize(screenSizeX, screenSizeY);
		frame.setResizable(false);
		
		// add a listener to respond to the window closing so we can
		// exit the game
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// add a key listener that allows us to respond to player
		// key presses. We're actually just going to set some flags
		// to indicate the current player direction
		frame.addKeyListener(this);
		addKeyListener(this);
		
		// show the frame before creating the buffer strategy!
		frame.setVisible(true);
		
		// create the strategy used for accelerated rendering. More details
		// in the space invaders 2D tutorial
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		// create our game objects, a map for the player to wander around
		// and an entity to represent out player
		map = new Map();
		player = new Entity(sprite, map, 1.5f, 1.5f);
		
		// start the game loop
		gameLoop();
	}
	
	/**
	 * The game loop handles the basic rendering and tracking of time. Each
	 * loop it calls off to the game logic to perform the movement and 
	 * collision checking.
	 */
	public void gameLoop() {
		boolean gameRunning = true;
		long last = System.nanoTime();
		
		// keep looking while the game is running
		while (gameRunning) {
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			// clear the screen
			g.setColor(Color.black);
			g.fillRect(0, 0, screenSizeX, screenSizeY);
			
			// render our game objects
			g.translate(0,20);
			map.paint(g);
			player.paint(g);
			
			// flip the buffer so we can see the rendering
			g.dispose();
			strategy.show();
			
			// pause a bit so that we don't choke the system
			try { Thread.sleep(4); } catch (Exception e) {};
			
			// calculate how long its been since we last ran the
			// game logic
			long delta = (System.nanoTime() - last) / 1000000;
			last = System.nanoTime();
		
			// now this needs a bit of explaining. The amount of time
			// passed between rendering can vary quite a lot. If we were
			// to move our player based on the normal delta it would
			// at times jump a long distance (if the delta value got really
			// high). So we divide the amount of time passed into segments
			// of 5 milliseconds and update based on that
			//for (int i=0;i<delta / 5;i++) {
				try {
					logic(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}
			try { Thread.sleep(50); } catch (Exception e) {};
			// after we've run through the segments if there is anything
			// left over we update for that
			/*if ((delta % 5) != 0) {
				try {
					logic(delta % 5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
	}
	
	/**
	 * Our game logic method - for this example purpose this is very 
	 * simple. Check the keyboard, and attempt to move the player
	 * 
	 * @param delta The amount of time to update for (in milliseconds)
	 * @throws InterruptedException 
	 */
	public void logic(long delta) throws InterruptedException {
		int i = 0;
		// check the keyboard and record which way the player
		// is trying to move this loop
		if (left) {
			player.moveAbsolute(player.getX() - 1 /** delta * 0.005f*/, player.getY());
			left = false;
		}
		if (right) {
			player.moveAbsolute(player.getX() + 1 /** delta * 0.005f*/, player.getY());
			right = false;
		}
		if (up) {
			player.moveAbsolute(player.getX(), player.getY() - 1 /** delta * 0.005f*/);
			up = false;
		}
		if (down) {
			player.moveAbsolute(player.getX(), player.getY() + 1 /** delta * 0.005f*/);
			down = false;
		}
		if (begin) {
			player.moveAbsolute(1.5f, 1.5f);
			begin = false;
		}
		if (end) {
			AStar astar = new AStar();
			Cell [] path = new Cell[100];
			path = astar.test(map.getWidth(), map.getHeight(), 5, 14, player.getyCell(), player.getxCell(), map.getData());
			
			if(null == path[1]) {
				player.moveAbsolute(path[0].j + 0.5f, path[0].i + 0.5f);
				map.setCellVisited(path[0].j, path[0].i);
				end = false;
			} else {
				player.moveAbsolute(path[1].j + 0.5f, path[1].i + 0.5f);
				map.setCellVisited(path[1].j, path[1].i);
			}
			
		}
		if (clear) {
			map.clearMap();
			clear = false;
		}
		map.setCellVisited(player.getxCell(), player.getyCell());
	}
	
	
	
	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		// check the keyboard and record which keys are pressed
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			end = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			begin = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_C) {
			clear = true;
		}
	}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		// check the keyboard and record which keys are released
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			//end = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			begin = false;
		}
	}
	
	/**
	 * The entry point to our example code
	 * 
	 * @param argv The arguments passed into the program
	 */
	public static void main(String[] argv) {
		new Game();
	}
}