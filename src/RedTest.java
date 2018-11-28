//Title and shit

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")

public class RedTest extends Applet implements ActionListener {

    CircleCanvas c;
    Button centerButton, colorButton;
    
    public void init () {
        setLayout(new BorderLayout());
        centerButton = new Button("Center");
        centerButton.setBackground(Color.white);
        centerButton.addActionListener(this);
        colorButton = new Button("Red/Green");
        colorButton.setBackground(Color.white);
        colorButton.addActionListener(this);
        Panel p = new Panel();
        p.setBackground(Color.black);
        p.add(centerButton);
        p.add(colorButton);
        add("North", p);
        c = new CircleCanvas();
        c.setBackground(Color.yellow);
        c.addMouseListener(c);
        c.addMouseMotionListener(c);
        add("Center", c);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == centerButton)
            c.center();
        else if (e.getSource() == colorButton)
            c.toggleColor();
    }
}


@SuppressWarnings("serial")

// a canvas that displays a circle
class CircleCanvas extends Canvas implements MouseListener,  MouseMotionListener   {
        
    int x = 50; // position of circle
    int y = 20;
    boolean redgreen = true; // color of circle (true = red, false = green)

    // draw the circle at x, y
    public void paint(Graphics g) {
        if (redgreen)
            g.setColor(Color.red);
        else
            g.setColor(Color.green);
        g.fillOval(x-10, y-10, 20, 20);
    }

    // centers circle on canvas and repaints
    public void center() {
        Dimension d = getSize();        // size of canvas
        x =  d.width/2;
        y =  d.height/2;
        repaint();
    }

    // toggles color of circle and repaints
    public void toggleColor() {
        redgreen = ! redgreen;
        repaint();
    }

    // draws color circle where mouse is pressed
    public void mousePressed(MouseEvent event) {
        Point p = event.getPoint();
        x = p.x;
        y = p.y;
        repaint(); // redraws canvas (yellow) and draws circle
    }

    // draws a (nonpermanent) blue circle when the mouse is released at
    // a different position from where it was pressed
    // it will get erased when repaint() is called (or the window is
    // uncovered, resized, etc.)
    public void mouseReleased(MouseEvent event) {
        Point p = event.getPoint();
        if (x != p.x || y != p.y) {
            Graphics g = getGraphics(); // get current graphics context
            g.setColor(Color.blue);
            g.fillOval(p.x-10, p.y-10, 20, 20);
        }
    }
    
    // need these also because we implement a MouseListener
    public void mouseClicked(MouseEvent event) { }
    public void mouseEntered(MouseEvent event) {
        toggleColor();
    }
    public void mouseExited(MouseEvent event) { }


    // this draws (non-permanent) gray circles when the mouse is dragged
    public void mouseDragged(MouseEvent event)  {
        Point p = event.getPoint();
        Graphics g = getGraphics(); // get current graphics context
        g.setColor(Color.gray);
        g.fillOval(p.x-10, p.y-10, 20, 20);
    }
    
    // need this also because we implement a MouseMotionListener
    public void mouseMoved(MouseEvent event) { }

    }
