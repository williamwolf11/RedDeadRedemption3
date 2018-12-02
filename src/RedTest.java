//Title and shit

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings({ "serial", "deprecation"})

public class RedTest extends Applet implements ActionListener {
	static Image img;
    protected CircleCanvas c;
    protected Button restartButton;
    protected Label titleLabel, scoreLabel;
    protected int currentScore;
    
    static final Color dgreen = new Color(0, 120, 90);
    
    public void init () {
    	currentScore = 0;
        setLayout(new BorderLayout());
        add("North", makeTopControlPanel());
        c = new CircleCanvas(this);
        c.setBackground(Color.yellow);
        c.addMouseListener(c);
        c.addMouseMotionListener(c);
        add("Center", c);
        img = getImage(getDocumentBase(), "images/Marston.png");
        //Found at http://convergence-series.wikia.com/wiki/John_Marston
    }
    
    /*public void start() {
        c.start();
    }

    public void stop() {
        c.stop();
    }*/
    
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
class CircleCanvas extends Canvas implements Runnable, MouseListener,  MouseMotionListener  {
	
	RedTest parent; //instance variable to be able to access applet components
	Vector<Cowboys> cowboys;
    int x; // position of circle
    int y;
    boolean blackOrColor = true; // color of circle (true = black, false = colored)
    boolean goodHit = false; // whether the click was a hit 
    
    public CircleCanvas(RedTest s) {
    	parent = s;
    	cowboys = new Vector<Cowboys>();
    	cowboys.add(new Cowboys());
    }
    
	//draws the canvas
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
        //sets size of game area
        Dimension d = getSize();
        d.height = 600;
        d.width = 1000;
        //puts image of John Marston in the corner
        g.drawImage(RedTest.img, 0, 0+(d.height-168), null);
        Graphics2D g2 = (Graphics2D) g;
        //Found this at https://stackoverflow.com/questions/16995308/can-you-increase-
        //line-thickness-when-using-java-graphics-for-an-applet-i-dont?lq=1
       
        //draws target around mouse location
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(x-20, y-20, 40, 40);
		g2.drawLine(x-25, y, x-15, y);
		g2.drawLine(x+25, y, x+15, y);
		g2.drawLine(x, y-25, x, y-15);
		g2.drawLine(x, y+25, x, y+15);
		g.fillOval(x-4, y-4, 8, 8);
		//cowboys.add(new Cowboys());
		/*cowboys.add(new Cowboys());
		Cowboys c = cowboys.get(0);
		c.drawCowboy(g2);*/
		run(g);
		
    }
    
    public void run(Graphics g){
    	while(cowboys.size() < 10) {
    		if(System.currentTimeMillis()%1000 == 0) {
    			cowboys.add(new Cowboys());}
    		for (int i=0; i<cowboys.size(); i++) {
    			Cowboys c = cowboys.get(i);
    			c.drawCowboy(g);
    		}
    	}
    }

    
    /*public void start() {
        t = new Thread();
        t.start();
        starttime = System.currentTimeMillis(); // record start time 
        //to reset timer
    }

    public void stop() {
        t = null;
    }
    
    public void run(){
    	Graphics2D g2 = (Graphics2D) g;
    	Thread currentThread = Thread.currentThread();
    	while (currentThread == t);{
    		try {
    			Thread.sleep(1000); //wait one second
    		}catch (InterruptedException e) {
                // do nothing
            }
    		for (int i=0; i<cowboys.size(); i++) {
                Cowboys c = cowboys.get(i);
                c.drawCowboy(g2);
            }
    		repaint();
    	}
    	
    	
    }*/
    	
    


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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	}
	
