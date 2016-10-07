import java.util.*;

/**
 * 
 */
public class Robot extends Object {

    /**
     * Default constructor
     * Creates a robot of 10px by 10px with (0, 0) as origin point
     */
    public Robot() {
    	this.origin = new Point(0, 0);
    	
    	// Creating the physical structure of the robot
    	List<Shape> struct = new ArrayList<Shape>();
    	// Creates a new 10x10px square not crossable
    	struct.add(new Square(10, 10, false));
    	this.structure = struct;
    }

    /**
     * 
     */
    private int orientation;


    /**
     * @param Point Dest 
     * @return
     */
    public boolean moveTo(Point Dest) {
        // TODO implement here
        return false;
    }
    
    /**
     * This method checks (if itself has not walkable areas only)
     * if the point p is in a walkable area or not
     */
    public boolean isWalkable(Point p) {
    	
    	for(Shape item : this.structure){
    		System.out.println("Item : " + item);
    	}
    	return false;
    }

}