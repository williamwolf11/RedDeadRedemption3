// Red Dead Redemption 3
// Names: Sophie Smith and William Wolf
// CSCI0201 Final Project
// Dec 7, 2018

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


@SuppressWarnings({ "serial", "deprecation"})
// and delete "deprecation"

public class RedTest extends Applet implements ActionListener{
	
	// images for the game
	static Image mainCharImg;
	static Image dutchImg;
	static Image javierImg;
	static Image haroldImg;
	static Image allendeImg;
	static Image nigelImg;
	static Image backgroundImg;

    protected RedDeadCanvas c;
    protected Button restartButton;
    protected Label titleLabel, scoreLabel;
    protected int currentScore;
    
    static final Color dgreen = new Color(0, 120, 90);
    
    // initializes game set up and assigns image instance variables
    public void init () {
        setLayout(new BorderLayout());
        add("North", makeTopControlPanel());
        c = new RedDeadCanvas(this);
        c.addMouseListener(c);
        c.addMouseMotionListener(c);
        add("Center", c);
        
        backgroundImg = getImage(getDocumentBase(), "images/background1.jpg");
        // 
        mainCharImg = getImage(getDocumentBase(), "images/Marston.png");
        //Found at http://convergence-series.wikia.com/wiki/John_Marston
        dutchImg = getImage(getDocumentBase(), "images/CSImage3.png");
        allendeImg = getImage(getDocumentBase(), "images/Coronel_allende.png");
        haroldImg = getImage(getDocumentBase(), "images/Professor.png");
        javierImg = getImage(getDocumentBase(), "images/javierescuella.png");
        nigelImg = getImage(getDocumentBase(), "images/NigelWestDickens.png");
        // Previous 4 images found at http://reddead.wikia.com
        
        currentScore = 0;    
    }
    // starts canvas
    public void start() {
        c.start();
    }

    // stops canvas
    public void stop() {
        c.stop();
    }
    
    // handles response to button
    public void actionPerformed(ActionEvent e) {
    	// restarts game if restartButton is pressed
        if (e.getSource() == restartButton) {
           restart();
        }
    }
    // makes the top panel of the game with score label,
    // title label, and restart button
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
    
    // creates title label with the name of game in bold red
    // lettering
    public Label makeTitleLabel() {
    	titleLabel = new Label("Red Dead Redemption 3");
    	titleLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        titleLabel.setAlignment(Label.CENTER);
        titleLabel.setForeground(Color.red);
    	return titleLabel;
    }
    
    // makes the score label in white plain font
    public Label makeScoreLabel() {
    	scoreLabel = new Label("Score: " + Integer.toString(currentScore));
    	scoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        scoreLabel.setAlignment(Label.CENTER);
        scoreLabel.setForeground(Color.white);
        return scoreLabel;
    }
    
    // creates the restart button in dark green font
    public Button makeRestartButton() {
    	restartButton = new Button("Restart");
    	restartButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
        restartButton.addActionListener(this);
        restartButton.setForeground(dgreen);
        return restartButton;
    }
    
    // centers circle on canvas and repaints
    public void restart() {
    	c.newGame();
    	currentScore = 0;
    	scoreLabel.setText("Score: " + currentScore);
    }

}



@SuppressWarnings("serial")

// a canvas that displays a circle
class RedDeadCanvas extends Canvas implements Runnable, MouseListener,  MouseMotionListener  {
		
	RedTest parent; //instance variable to be able to access applet components
	static Vector<Cowboys> cowboys;
	private Thread t = null;
    int x; // position of target
    int y;
    int speed; // speed that cowboys appear
    
    boolean blackOrColor = true; // color of circle (true = black, false = colored)
    boolean goodHit = false; // whether the click was a hit 
    
    int mainCharPos = 432;
    
    // size of target
    int targetRadius = 20;
    int centerDotRad = 4;
    int targStartLine = 25;
    int targEndLine = 15;

    public RedDeadCanvas(RedTest s) {
    	parent = s;
    	newGame();
    }
    
    // resets components of the game
    public void newGame() {
    	speed = 3000;
    	cowboys = new Vector<Cowboys>();
    }

	//draws the canvas
    public void paint(Graphics g) {
        if (blackOrColor) {
            g.setColor(Color.black);
        } else {
        	// dot appears red if hits target
        	if (goodHit) {
        		g.setColor(Color.red);
        	} else {
        	// dot appears gray if does not hit target
        		g.setColor(Color.lightGray);
        	} 
        }
        // background image
        g.drawImage(RedTest.backgroundImg, 0, 0, null);
        //puts image of John Marston in the corner
        g.drawImage(RedTest.mainCharImg, 0, mainCharPos, null);
        
        Graphics2D g2 = (Graphics2D) g;
        //Found this at https://stackoverflow.com/questions/16995308/can-you-increase-
        //line-thickness-when-using-java-graphics-for-an-applet-i-dont?lq=1
       
        // draws cowboys
        for (int i=0; i<cowboys.size(); i++) {
    		Cowboys c = cowboys.get(i);
    		c.drawCowboy(getGraphics());
    	}
        // draws the target following cursor
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x-targetRadius, y-targetRadius, 2*targetRadius, 2*targetRadius);
		g2.drawLine(x-targStartLine, y, x-targEndLine, y);
		g2.drawLine(x+targStartLine, y, x+targEndLine, y);
		g2.drawLine(x, y-targStartLine, x, y-targEndLine);
		g2.drawLine(x, y+targStartLine, x, y+targEndLine);
		g.fillOval(x-centerDotRad, y-centerDotRad, 2*centerDotRad, 2*centerDotRad);
    }
   
    // starts the thread
    public void start() {
    	if (t == null) {
    		t = new Thread(this);
    		t.start();
    	}
    }
    // stops the thread
	@SuppressWarnings("deprecation")
	public void stop() {
    	t.stop();

    }

    public void run(){
    	Thread currentThread = Thread.currentThread();
    	// while running
        while (currentThread == t) {
        	// game stops if there are 15 cowboys
        	while(cowboys.size() < 15) {
        		
        		if (System.currentTimeMillis()%speed == 0) {
        			cowboys.add(new Cowboys());
        			repaint();
        			// makes cowboys appear faster as game goes on
        			if(speed > 100) {
        				speed-=20;
        			
        			}
        		}
        		
        	}
        	// when 15 cowboys appear, shows Game Over message
    		parent.scoreLabel.setText("Game Over! Score: " + parent.currentScore);
        }
        
    }
    
    // toggles color of target and repaints
    public void toggleColor() {
        blackOrColor = ! blackOrColor;
        repaint();
    }

    // shoots and determines if shot was a hit
    public void mousePressed(MouseEvent event) {
        Point p = event.getPoint();
        x = p.x;
        y = p.y;

        for (int i=0; i<cowboys.size(); i++) {
			Cowboys c = cowboys.get(i);
			// if the position where player clicks is on head/shoulders of cowboy
			if(p.x <= c.x+57 && p.x >= c.x+20 && p.y <= c.y+95 && p.y >= c.y+10 && cowboys.size() < 15) {
				cowboys.remove(i--);
				// changes color of target to red
				goodHit = true;
				// add one to score and update label
				parent.currentScore += 1;
				parent.scoreLabel.setText("Score: " + parent.currentScore);
			}
			repaint();
		}
        // when mouse is pressed, target is either gray or red
        toggleColor();
        repaint(); // redraws canvas (yellow) and draws circle
    }
    
    // when mouse is released, target turns back to black
    public void mouseReleased(MouseEvent event) {
    	// resets goodHit boolean 
    	goodHit = false;
    	// target turns black again
    	toggleColor();
    }
    
    public void mouseClicked(MouseEvent event) { }
    
    public void mouseEntered(MouseEvent event) { }
    
    public void mouseExited(MouseEvent event) { }
    
    public void mouseDragged(MouseEvent event)  { }
    
    // target follows cursor around
    public void mouseMoved(MouseEvent event) {
    	Point p = event.getPoint();
        x = p.x;
        y = p.y;
        repaint();
    }
	
}
	
