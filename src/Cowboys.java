//Comment title

import java.awt.*;

public class Cowboys {
    int x;     // position
    int y;
    int height; //size
    int width;

    public Cowboys(){ // creates cowboy with random attributes
        x = (int)(Math.random()*1000);
        y = (int)(Math.random()*800);
        height = (int)(Math.random()*100);
        width = (int)(Math.random()*100);
    }

    /*public boolean move(int width, int height, int pmin, int pmax) {
        // moves the ball within the box defined by width and height
        // returns true if ball gets lost
        // if it hits the top and sides, bounce it back
        // if it hits the bottom, bounce it only if it is between
        // pmin and pmax; return true otherwise

        x += dx;
        if (x-radius <0 || x+radius >= width) {
            dx = -dx;
            x += dx;
        }

        y += dy;
        if (y+radius >= height && (x+radius < pmin || x-radius > pmax))
            return true;
        if (y-radius < 0 || y+radius >= height) {
            dy = -dy;
            y += dy;
        }
        return false;
    }*/

    public void drawCowboy(Graphics g) {
        // draw the cowboy
        g.setColor(Color.red);
        g.drawRect(x, y, width, height);
    }
}