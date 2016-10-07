
import java.util.*;

/**
 * 
 */
public abstract class Object {

    /**
     * Default constructor
     */
    public Object() {
    }

    /**
     * 
     */
    protected List<Shape> structure;

    /**
     * 
     */
    protected Point origin;

    /**
     * 
     */
    protected int objectType;

    /**
     * @return
     */
    public void printProperties() {
        // TODO implement here
    }

    /**
     * @param p 
     * @return
     */
    public boolean isWalkable(Point p) {
        // TODO implement here
        return false;
    }

    /**
     * @return
     */
    public Point getOrigin() {
        return this.origin;
    }

    /**
     * @param p 
     * @return
     */
    public boolean isIn(Point p) {
        // TODO implement here
        return false;
    }

	public List<Shape> getStructure() {
		return structure;
	}

    
}