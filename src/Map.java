
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

/**
 * 
 */
public class Map {

    /**
     * Default constructor
     */
    public Map() {
    }
    
    /**
     * Default constructor
     */
    public Map(int w, int h) {
    	this.width = w;
    	this.height = h;
    }
    
    /**
     * 
     */
    private int width;

    /**
     * 
     */
    private int height;

    /**
     * 
     */
    private List<Object> objects;

    /**
     * 
     */
    private int step;

    /**
     * @param fileName 
     * @return
     */
    public boolean readFromFile(String fileName) {
        // TODO implement here
        return false;
    }

    /**
     * @param fileName 
     * @return
     */
    public boolean saveInFile(String fileName) {
        // TODO implement here
        return false;
    }
    
    /**
     * loop through all the pixels in the map
     * rendering them based on whether there is
     * an object locking the way or not
     * @return
     */
    public void paint(Graphics2D g) {
    	boolean walkable = true;
    	
		for (int x=0;x<width;x++) {
			for (int y=0;y<height;y++) {
				// so if the pixel is blocked, draw it a different color
				
				// Ask for all objects in map if this pixel is walkable
				if(null != this.objects) {
					for(Object obj : this.objects) {
						for(Shape shape : obj.structure) {
							if((obj.getOrigin().get_x() < x) && (obj.getOrigin().get_y() < y)) {
								if(false == shape.isWalkable(new Point(x - obj.getOrigin().get_x(), y - obj.getOrigin().get_y()))) {
									walkable = false;
								}
							}
						}
					}
				}
				
				g.setColor(Color.gray);
				if (!walkable) {
					g.setColor(Color.darkGray);
				}
				
				// draw the pixel
				g.fillRect(x, y, 1, 1);
			}
		}
    }

    
    /**
     * 
     */
    public boolean addObjectToList(Object o) {
    	this.objects.add(o);
    	return true;
    }
    
    
    
    /**
     * @return
     */
    public void print() {
        // TODO implement here
    }

}