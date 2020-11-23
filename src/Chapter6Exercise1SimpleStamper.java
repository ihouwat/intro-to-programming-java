import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A simple demonstration of MouseEvents.  Shapes are drawn
 * on a white background when the user clicks the panel.  If
 * the user Shift-clicks, the panel is cleared.  If the user
 * right-clicks the panel, a blue oval is drawn.  Otherwise,
 * when the user clicks, a red rectangle is drawn. if the user
 * drags the mouse, the program continues to draw figures .
 * The contents of the panel are not persistent.
 * For example, they might disappear if the panel is resized.
 * This class has a main() routine to allow it to be run as an application.
 */
public class Chapter6Exercise1SimpleStamper extends JPanel implements MouseListener, MouseMotionListener {

    public static void main(String[] args) {
        JFrame window = new JFrame("Simple Stamper");
        Chapter6Exercise1SimpleStamper content = new Chapter6Exercise1SimpleStamper();
        window.setContentPane(content);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(120,70);
        window.setSize(400,300);
        window.setVisible(true);
    }

    // ----------------------------------------------------------------------

    // Some private variables used among the event handling methods
    private boolean dragging; // Detects if a user is dragging the mouse
    private Graphics g; // Graphics context
    private int x, y;  // x-coordinate and y-coordinate where user clicked.

    /**
     * This constructor simply sets the background color of the panel to be white
     * and sets the panel to listen for mouse events on itself.
     */
    public Chapter6Exercise1SimpleStamper() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }


    // paintComponent to draw initial panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    // Method to draw a blue oval
    // Takes in the graphics context, and x and y locations
    public void drawBlueOval(Graphics g, int x, int y) {
        g = getGraphics();
        g.setColor(Color.BLUE);  // Blue interior.
        g.fillOval( x - 30, y - 15, 60, 30 );
        g.setColor(Color.BLACK); // Black outline.
        g.drawOval( x - 30, y - 15, 60, 30 );
        g.dispose();  // We are finished with the graphics context, so dispose of it.

    }

    // Method to draw a red rectangle
    // Takes in the graphics context, and x and y locations
    public void drawRectangle(Graphics g, int x, int y){
        g = getGraphics();
        g.setColor(Color.RED);   // Red interior.
        g.fillRect( x - 30, y - 15, 60, 30 );
        g.setColor(Color.BLACK); // Black outline.
        g.drawRect( x - 30, y - 15, 60, 30 );
        g.dispose();  // We are finished with the graphics context, so dispose of it.

    }


    /**
     *  Since this panel has been set to listen for mouse events on itself, 
     *  this method will be called when the user clicks the mouse on the panel.
     *  This method is part of the MouseListener interface.
     */
    public void mousePressed(MouseEvent evt) {

        if ( evt.isShiftDown() ) {
                // The user was holding down the Shift key.  Just repaint the panel.
                // Since this class does not define a paintComponent() method, the 
                // method from the superclass, JPanel, is called.  That method simply
                // fills the panel with its background color, which is black.  The 
                // effect is to clear the panel.
            repaint();
            dragging = false;
            return;
        }

        dragging = true;
        x = evt.getX();
        y = evt.getY();

        if ( evt.isMetaDown() || evt.isAltDown()) {
            // User right-clicked at the point (x,y). Draw a blue oval centered
            // at the point (x,y). (A black outline around the oval will make it
            // more distinct when shapes overlap.)
            drawBlueOval(g, x, y);
        }
        else {
            // User left-clicked (or middle-clicked) at (x,y).
            // Draw a red rectangle centered at (x,y).
            drawRectangle(g, x, y);
        }

    } // end mousePressed();


    /**
     *  This method is part of the MouseMotionListener interface.
     */
    public void mouseDragged(MouseEvent evt) {

        if (dragging == false) return;  // Nothing to do because the user isn't drawing.

        x = evt.getX();   // x-coordinate of mouse.
        y = evt.getY();   // y-coordinate of mouse.

        if ( evt.isMetaDown() || evt.isAltDown()) {
            drawBlueOval(g, x, y);
        }
        else {
            drawRectangle(g, x,y);
        }
    } // end mouseDragged();

    // The next four empty routines are required by the MouseListener and MouseMotionListener interfaces.
    // They don't do anything in this class, so their definitions are empty.

    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) { }
    public void mouseMoved(MouseEvent evt) { }


} // end class SimpleStamper