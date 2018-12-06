
import java.awt.*;



public class Cowboys {
    int x;     // position
    int y;

    public Cowboys(){ // creates cowboy with random position
        x = (int)(Math.random()*900) + 50;
        y = (int)(Math.random()*250) + 250;
        }

    public void drawCowboy(Graphics g, Image img) {
        // draw the cowboy
    	// if (y >)

    	long size =  (long)y / (long)5;
    	int roundedSize = (int)size;
        g.drawImage(img, x, y, roundedSize, roundedSize*2, null);
    }
}
