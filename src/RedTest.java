//Title and shit

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings({ "serial", "deprecation" })

public class RedTest extends Applet implements ActionListener {

    protected CircleCanvas c;
    protected Button restartButton;
    protected Label titleLabel, scoreLabel;
    protected int currentScore;
    
    static final Color dgreen = new Color(0, 120, 90);
    
    public void init () {
    	currentScore = 0;
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
           restart();
    }
    
    public Panel makeTopControlPanel() {
    	Panel topControlPanel = new Panel();
    	topControlPanel.setLayout(new GridLayout(1, 3));
    	
    	restartButton = makeRestartButton();
        titleLabel = makeTitleLabel();
        scoreLabel = makeScoreLabel();
        scoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        
        topControlPanel.setBackground(dgreen);
        topControlPanel.add(scoreLabel);
        topControlPanel.add(titleLabel);
        topControlPanel.add(restartButton);
        
        return topControlPanel;
    }
    
    public Label makeTitleLabel() {
    	titleLabel = new Label("Red Dead Redeption 3");
    	titleLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        titleLabel.setAlignment(Label.CENTER);
        titleLabel.setForeground(Color.red);
    	return titleLabel;
    }
    
    public Label makeScoreLabel() {
    	scoreLabel = new Label("Score: " + Integer.toString(currentScore));
    	scoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        scoreLabel.setAlignment(Label.CENTER);
        scoreLabel.setForeground(Color.white);
        return scoreLabel;
    }
    
    public Button makeRestartButton() {
    	restartButton = new Button("Restart");
    	restartButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
        restartButton.addActionListener(this);
        restartButton.setForeground(dgreen);
        return restartButton;
    }
    
    // centers circle on canvas and repaints
    public void restart() {
    	/*
        Dimension d = getSize();        // size of canvas
        x =  d.width/2;
        y =  d.height/2;
        repaint();
        */
    	currentScore = 0;
    }

}


@SuppressWarnings("serial")

// a canvas that displays a circle
class CircleCanvas extends Canvas implements MouseListener,  MouseMotionListener   {
        
    int x = 50; // position of circle
    int y = 20;
    boolean blackOrColor = true; // color of circle (true = black, false = colored)
    boolean goodHit = false; // whether the click was a hit 

    // draw the circle at x, y
    public void paint(Graphics g) {
        if (blackOrColor)
            g.setColor(Color.black);
        else {
        	// dot appears red if hits target
        	if (goodHit) {
        		g.setColor(Color.red);
        	}
        	// dot appears gray if does not hit target
        	else {
        		g.setColor(Color.lightGray);
        	} 
        }
        //draws the target
        Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x-20, y-20, 40, 40);
		g2.drawLine(x-25, y, x-15, y);
		g2.drawLine(x+25, y, x+15, y);
		g2.drawLine(x, y-25, x, y-15);
		g2.drawLine(x, y+25, x, y+15);
		g.fillOval(x-4, y-4, 8, 8);
    }


    // toggles color of circle and repaints
    public void toggleColor() {
        blackOrColor = ! blackOrColor;
        repaint();
    }

    // draws color circle where mouse is pressed
    public void mousePressed(MouseEvent event) {
        Point p = event.getPoint();
        x = p.x;
        y = p.y;
        toggleColor();
        repaint(); // redraws canvas (yellow) and draws circle
    }

    public void mouseReleased(MouseEvent event) {
    	toggleColor();
    }
    
    // need these also because we implement a MouseListener
    public void mouseClicked(MouseEvent event) { }
    
    public void mouseEntered(MouseEvent event) { }
    
    public void mouseExited(MouseEvent event) { }

    public void mouseDragged(MouseEvent event)  { }
    
    // need this also because we implement a MouseMotionListener
    public void mouseMoved(MouseEvent event) {
    	// now the circle follows the cursor around
    	Point p = event.getPoint();
        x = p.x;
        y = p.y;
        repaint();
    }

	
	}
	
