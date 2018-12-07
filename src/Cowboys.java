
import java.awt.*;



public class Cowboys {
    int x;     // position
    int y;
    
    // creates cowboy with random position 
    // x coordinates between 50 and 950
    // y coordinates between 250 and 500
    public Cowboys(){ 
        x = (int)(Math.random()*900) + 50;
        y = (int)(Math.random()*250) + 250;
        }
    
    // draws cowboy at x and y position. Size of cowboy depends on 
    // y coordinate to create depth in game board
    public void drawCowboy(Graphics g, Image img) {
    	long size =  (long)y / (long)5;
    	int roundedSize = (int)size;
        g.drawImage(img, x, y, roundedSize, roundedSize*2, null);
    }
}
