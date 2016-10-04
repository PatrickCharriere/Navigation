import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PaintCanvas extends JPanel {

    private int oldX;
    private int oldY;

    public PaintCanvas() {
        draw();
    }

    private void draw() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                getGraphics().drawLine(oldX, oldY, e.getX(), e.getY());
                oldX = e.getX();
                oldY = e.getY();

            }
        });
    }
}