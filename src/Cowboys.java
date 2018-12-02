
import java.awt.*;

public class Cowboys {

	int x;     // position
    int y;
    int size = 5;

    public Cowboys(int screenX, int screenY){ // creates ball with random attributes
        x = (int)(Math.random()*screenX)+0;
        y = (int)(Math.random()*screenY)+0;
        // size = (int)(Math.random()*8)+8;
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillOval(x - size, y - size, 2*size, 2*size);
        g.setColor(Color.black);
        g.drawOval(x - size, y - size, 2*size, 2*size);
    }

}
