// Red Dead Redemption 3
// Names: Sophie Smith and William Wolf
// CSCI0201 Final Project
// Dec 7, 2018

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
// import java.util.concurrent.TimeUnit;
// import javax.swing.*;
// ^ delete these??

@SuppressWarnings({ "serial", "deprecation"})
// and delete "deprecation"

public class RedTest extends Applet implements ActionListener{
	
	// images for the game
	static Image mainCharImg;
	static Image cowboyImg;
	static Image backgroundImg;
	
    protected RedDeadCanvas c;
    protected Button restartButton;
    protected Label titleLabel, scoreLabel;
    protected int currentScore;
    
    
    static final Color dgreen = new Color(0, 120, 90);
    
    public void init () {
        setLayout(new BorderLayout());
        add("North", makeTopControlPanel());
        c = new RedDeadCanvas(this);
        c.addMouseListener(c);
        c.addMouseMotionListener(c);
        add("Center", c);
        
        backgroundImg = getImage(getDocumentBase(), "images/background1.jpg");
        mainCharImg = getImage(getDocumentBase(), "images/Marston.png");
        //Found at http://convergence-series.wikia.com/wiki/John_Marston
        cowboyImg = getImage(getDocumentBase(), "images/CSImage3.png");
        
        currentScore = 0;    
    }
    
    public void start() {
        c.start();
    }

    public void stop() {
        c.stop();
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
           restart();
        }
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
    	titleLabel = new Label("Red Dead Redemption 3");
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
    int x; // position of target
    int y;
    boolean blackOrColor = true; // color of circle (true = black, false = colored)
    boolean goodHit = false; // whether the click was a hit 
    int speed;
    private Thread t = null;
    long starttime;

    
    public RedDeadCanvas(RedTest s) {
    	parent = s;
    	newGame();
    }
    
    public void newGame() {
    	speed = 3000;
    	cowboys = new Vector<Cowboys>();
    	cowboys.add(new Cowboys());
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
        //sets size of game area
        Dimension d = getSize();
        d.height = 600;
        d.width = 1000;
        
        g.drawImage(RedTest.backgroundImg, 0, 0, null);
        //puts image of John Marston in the corner
        g.drawImage(RedTest.mainCharImg, 0, 0+(d.height-168), null);
        
        Graphics2D g2 = (Graphics2D) g;
        //Found this at https://stackoverflow.com/questions/16995308/can-you-increase-
        //line-thickness-when-using-java-graphics-for-an-applet-i-dont?lq=1
       
        //draws target around mouse location
        //run(g);
        for (int i=0; i<cowboys.size(); i++) {
    		Cowboys c = cowboys.get(i);
    		c.drawCowboy(getGraphics(), RedTest.cowboyImg);
    	}
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x-20, y-20, 40, 40);
		g2.drawLine(x-25, y, x-15, y);
		g2.drawLine(x+25, y, x+15, y);
		g2.drawLine(x, y-25, x, y-15);
		g2.drawLine(x, y+25, x, y+15);
		g.fillOval(x-4, y-4, 8, 8);
    }
    
    
    public void start() {
    	if (t == null) {
    		t = new Thread(this);
    		t.start();
    	}
    }

	@SuppressWarnings("deprecation")
	public void stop() {
    	t.stop();

    }

    public void run(){
    	Thread currentThread = Thread.currentThread();
        while (currentThread == t) {
        	while(cowboys.size() < 15) {
        		if (System.currentTimeMillis()%speed <= 1) {
        			cowboys.add(new Cowboys());
        			if(speed > 500) {
        				speed-=20;
        			}
        		}
        		repaint();
        	}
    		parent.scoreLabel.setText("Game Over! Score: " + parent.currentScore);
        }
        
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

        for (int i=0; i<cowboys.size(); i++) {
			Cowboys c = cowboys.get(i);
			if(p.x <= c.x+57 && p.x >= c.x+20 && p.y <= c.y+95 && p.y >= c.y+10 && cowboys.size() < 25) {
				cowboys.remove(i--);
				goodHit = true;
				parent.currentScore += 1;
				parent.scoreLabel.setText("Score: " + parent.currentScore);
			}
			repaint();
		}
        toggleColor();
        repaint(); // redraws canvas (yellow) and draws circle
    }
    
    // when mouse is released, target turns back to black
    public void mouseReleased(MouseEvent event) {
    	goodHit = false;
    	toggleColor();
    }
    
    public void mouseClicked(MouseEvent event) { }
    
    public void mouseEntered(MouseEvent event) { }
    
    public void mouseExited(MouseEvent event) { }

    public void mouseDragged(MouseEvent event)  { }
    

    public void mouseMoved(MouseEvent event) {
    	// target follows cursor around
    	Point p = event.getPoint();
        x = p.x;
        y = p.y;
        repaint();
    }
	
	public static void clearVector() {
		cowboys.clear();
	}
	
	
}
	
