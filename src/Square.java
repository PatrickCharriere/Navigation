
/**
 * 
 */
public class Square extends Polygon {

    /**
     * Default constructor
     */
    public Square() {
    }
    
    /**
     * 
     * @param width of the square to create
     * @param height of the square to create
     * @param walkable if the area can be crossed
     */
    public Square(int width, int height, boolean walkable) {
    	this.ul = new Point(0, 0);
    	this.dr = new Point(width, height);
    	this.walkable = walkable;
    }

    /**
     * upper left corner of the square
     */
    private Point ul;

    /**
     * downer right corner of the square
     */
    private Point dr;


    /**
     * Other constructor by the extremities
     * @param ul upper left corner point
     * @param dr downer right corner point
     * @param walkable: true if the area is crossable
     * @return
     */
    public Square(Point ul, Point dr, boolean walkable) {
        // TODO implement here
    }

    /**
     * This function checks (if the shape is not walkable)
     * if the point is in it
     */
    public boolean isWalkable(Point p) {
    	if(true == this.walkable) {
    		return true;
    	}
    	
    	if(true == this.isIn(p)) {
    		return false;
    	} else {
    		return true;
    	}
    	
    }
    
    /**
     * This method inform if the point p is in the current
     * shape or not.
     */
    public boolean isIn(Point p) {
    	
    	return true;
    }
    
}