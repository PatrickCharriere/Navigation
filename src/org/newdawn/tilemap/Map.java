package org.newdawn.tilemap;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The map holds the data about game area. In this case its responsible
 * for both rendering the map and check collision against the grid cells
 * within. 
 * 
 * Our map is a simple WIDTHxHEIGHT grid containing value 0 to indicate
 * a clear cell and 1 to indicate a wall.
 * 
 * @author Kevin Glass
 */
public class Map {
	/** The value indicating a clear cell */
	private static final int CLEAR = 0;

	/** The value indicating a blocked cell */
	private static final int BLOCKED = 1;

	/** The value indicating a visited cell */
	private static final int VISITED = 2;
	
	/** The value indicating a visited cell */
	private static final int BEGIN = 3;
	
	/** The value indicating a visited cell */
	private static final int END = 4;
	
	/** The width in grid cells of our map */
	private static final int WIDTH = 20;
	/** The height in grid cells of our map */
	private static final int HEIGHT = 20;
	
	/** The rendered size of the tile (in pixels) */
	public static final int TILE_SIZE = 20;
	
	/** The actual data for our map */
	private int[][] data = new int[WIDTH][HEIGHT];

	/**
	 * Create a new map with some default contents
	 */
	public Map() {
		// create some default map data - it would be way
		// cooler to load this from a file and maybe provide
		// a map editor of some sort, but here we'll manually
		// fill the data with a simple little map

		for (int y=0;y<HEIGHT;y++) {
			data[0][y] = BLOCKED;
			data[3][y] = BLOCKED;
			data[7][y] = BLOCKED;
			data[11][y] = BLOCKED;
			data[WIDTH-1][y] = BLOCKED;
		}
		for (int x=0;x<WIDTH;x++) {
			if ((x > 0) && (x < WIDTH-1)) {
				data[x][10] = CLEAR;
			}
			
			if (x > 2) {
				data[x][9] = BLOCKED;
			}
			data[x][0] = BLOCKED;
			data[x][HEIGHT-1] = BLOCKED;
		}
		
		data[4][9] = CLEAR;
		data[7][5] = CLEAR;
		data[7][4] = CLEAR;
		data[11][7] = CLEAR;
		data[16][9] = CLEAR;
		
		data[1][1] = BEGIN;
		data[14][5] = END;
	}
	
	/**
	 * Render the map to the graphics context provided. The rendering
	 * is just simple fill rectangles
	 * 
	 * @param g The graphics context on which to draw the map
	 */
	public void paint(Graphics2D g) {
		// loop through all the tiles in the map rendering them
		// based on whether they block or not
		for (int x=0;x<WIDTH;x++) {
			for (int y=0;y<HEIGHT;y++) {
				// so if the cell is blocks, draw a light grey block
				// otherwise use a dark gray

				g.setColor(Color.darkGray);
				if (data[x][y] == BLOCKED) {
					g.setColor(Color.gray);
				} else if (data[x][y] == VISITED) {
					g.setColor(new Color(20,130,160));
				} else if (data[x][y] == BEGIN) {
					g.setColor(new Color(225,75,75));
				} else if (data[x][y] == END) {
					g.setColor(new Color(75,225,100));
				}
				
				// draw the rectangle with a dark outline
				g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
				g.setColor(g.getColor().darker());
				g.drawRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}
	}
	
	/**
	 * 
	 * @param g The graphics context on which to draw
	 * @param x The coordinate (x axis) of the cell to paint
	 * @param y The coordinate (x axis) of the cell to paint
	 * @param c The color to paint the cell
	 */
	public void paintCell(Graphics2D g, int x, int y, Color c) {
		g.setColor(c);
		g.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
		g.drawRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}
	
	public int[][] getData() {
		return data;
	}

	public void setCellVisited(int i, int j) {
		if((BEGIN != data[i][j]) && (END != data[i][j])) {
			data[i][j] = VISITED;
		}
	}

	public void setCellClear(int i, int j) {
		data[i][j] = CLEAR;
	}
	
	public void clearMap() {
		for (int x=0;x<WIDTH;x++) {
			for (int y=0;y<HEIGHT;y++) {
				if(VISITED == data[x][y]) {
					data[x][y] = CLEAR;
				}
			}
		}
		
	}
	
	public void setCellBlocked(int i, int j) {
		data[i][j] = BLOCKED;
	}
	
	/**
	 * Check if a particular location on the map is blocked. Note
	 * that the x and y parameters are floating point numbers meaning
	 * that we can be checking partially across a grid cell.
	 * 
	 * @param x The x position to check for blocking
	 * @param y The y position to check for blocking
	 * @return True if the location is blocked
	 */
	public boolean blocked(float x, float y) {
		// look up the right cell (based on simply rounding the floating
		// values) and check the value
		return data[(int) x][(int) y] == BLOCKED;
	}
}
