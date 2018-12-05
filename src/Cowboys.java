
import java.awt.*;



public class Cowboys {
    int x;     // position
    int y;
    int size; //scaled size of height of image



    public Cowboys(){ // creates cowboy with random position
        x = (int)(Math.random()*900) + 50;
        y = (int)(Math.random()*300) + 200;
        }

    public void drawCowboy(Graphics g, Image img) {
        // draw the cowboy
    	//size =  100 * ((y / 600) + (1 / 6));
    	//Image scaledImg = img.getScaledInstance(size, -1, Image.SCALE_SMOOTH);
        g.drawImage(img, x, y, null);
    }
}
