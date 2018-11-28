//Title and shit

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings({ "serial", "deprecation" })

public class RedTest extends Applet implements ActionListener {

    CircleCanvas c;
    Button restartButton;
    Label titleLabel, scoreLabel;
    
    static final Color dgreen = new Color(0, 120, 90);
    
    public void init () {
        setLayout(new BorderLayout());
        add("North", makeTopControlPanel());
        c = new CircleCanvas();
        c.setBackground(Color.yellow);
        c.addMouseListener(c);
        c.addMouseMotionListener(c);
        add("Center", c);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton)
            c.center();
    }
    
    public Panel makeTopControlPanel() {
    	Panel topControlPanel = new Panel();
    	topControlPanel.setLayout(new GridLayout(1, 3));
    	restartButton = new Button("Restart");
        restartButton.setBackground(dgreen);
        restartButton.addActionListener(this);
        titleLabel = new Label("Red Dead Redeption 3");
        titleLabel.setAlignment(Label.CENTER);
        titleLabel.setBackground(dgreen);
        titleLabel.setForeground(Color.white);
        scoreLabel = new Label("Score: ");
        scoreLabel.setAlignment(Label.CENTER);
        scoreLabel.setBackground(dgreen);;
        scoreLabel.setForeground(Color.white);
        topControlPanel.setBackground(dgreen);
        topControlPanel.add(scoreLabel);
        topControlPanel.add(titleLabel);
        topControlPanel.add(restartButton);
        return topControlPanel;
    	
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
