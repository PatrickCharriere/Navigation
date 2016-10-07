
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;


/**
 * 
 */
@SuppressWarnings("serial")
public class Program extends Canvas {
	
    private static final String WINDOW_TITLE = "Robot Navigation";

	/**
     * 
     */
    private Map map;

    /**
     * 
     */
    private Robot bot;

    /**
     * 
     */
    private Algorithm algo;

    /**
     * 
     */
    private KeyListener keyboard;

    /** 
     * The buffered strategy used for accelerated rendering
     * */
	private BufferStrategy strategy;

	/**
	 * Screen dimensions in pixels
	 */
	private int screenHeight;
	private int screenWidth;
    
	/**
     * Default constructor
     * @return
     */
    public Program() {
    }
    	
    	
    /**
     * 
     * @return
     */
    public Program(int width, int height) {
    	this.screenHeight = height;
    	this.screenWidth = width;
    	
    	// create the AWT frame. Its going to not being  resizable
		// this just gives us less to account for
		Frame frame = new Frame(WINDOW_TITLE);
		frame.setLayout(null);
		setBounds(0, 0, width, height);
		frame.add(this);
		frame.setSize(width, height);
		frame.setResizable(false);
		
		// add a listener so we can exit the program
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		// add a key listener that allows us to respond to player
		// key presses. We're actually just going to set some flags
		// to indicate the current player direction
		//frame.addKeyListener(this);
		//addKeyListener(this);
		

		// show the frame before creating the buffer strategy!
		frame.setVisible(true);
		 
		// create the strategy used for accelerated rendering
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		// create our game objects, a map for the player to wander around
		// and an entity to represent out player
		map = new Map(300, 300);
		bot = new Robot();
		//algo = new Algorithm();
		
		setObjectOnMap(bot, map);
    }

    /**
     * Main program loop. Cette boucle permet de contrôler de manière régulière
     * les touches pressées (vidage du buffer) et d'effectuer les actions en
     * fonction. Lors d'un déplacement elle va utiliser les objets pour vérifier
     * les mouvements et les éventuelles collisions.
     * @return
     */
    public void mainLoop() {
    	boolean programRunning = true;
		
		// keep looking while the program is running
		while (programRunning) {
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			// clear the screen
			g.setColor(Color.black);
			g.fillRect(0, 0, screenWidth, screenHeight);
			
			// render the main map
			g.translate(50, 70);
			map.paint(g);
			
			// flip the buffer so we can see the rendering
			g.dispose();
			strategy.show();
			
			// pause a bit so that we don't choke the system
			try { Thread.sleep(5); } catch (Exception e) {};
			
		
			/*try {
				logic(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			
			// Sleep 50 microsec to make movements visible
			try { Thread.sleep(50); } catch (Exception e) {};
		}
    }

    /**
     * @param Dest 
     * @param Obj 
     * @param Map 
     * @param Algo 
     * @return
     */
    public void moveObjectTo(Point Dest, Object Obj, Map Map, Algorithm Algo) {
        // TODO implement here
    }

    /**
     * @param Object Obj 
     * @param Map Map 
     * @return
     */
    public void setObjectOnMap(Object Obj, Map Map) {
        Map.addObjectToList(Obj);
    }
    
    /**
	 * The entry point to our code
	 * 
	 * @param argv The arguments passed into the program
	 */
	public static void main(String[] argv) {
		Program p = new Program(400, 420);
		p.mainLoop();
	}
    
}