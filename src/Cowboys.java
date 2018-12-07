// Red Dead Redemption 3
// Cowboys.java
// Names: Sophie Smith and William Wolf
// CSCI0201 Final Project
// Dec 7, 2018

import java.awt.*;



public class Cowboys {
    int x;     // position
    int y;
    Image cowboyChoice;
    
    // creates cowboy with random position 
    // x coordinates between 50 and 950
    // y coordinates between 250 and 500
    public Cowboys(){ 
        x = (int)(Math.random()*900) + 50;
        y = (int)(Math.random()*250) + 250;
        cowboyChoice = imageChoice();
        }
    
    // draws cowboy at x and y position. Size of cowboy depends on 
    // y coordinate to create depth in game board
    public void drawCowboy(Graphics g) {
    	
    	long size =  (long)y / (long)5;
    	int roundedSize = (int)size;
        g.drawImage(cowboyChoice, x, y, roundedSize, roundedSize*2, null);
    }
    
    // Randomly chooses an image to be added to the vector
    public Image imageChoice() {

    	double numChoice = (Math.random()*10);
    	if (numChoice <= 2) {
    		return RedTest.dutchImg;
    	}
    	if (numChoice <= 4) {
    		return RedTest.allendeImg;
    	}
    	if (numChoice <= 6) {
    		return RedTest.javierImg;
    	}
    	if (numChoice <= 8) {
    		return RedTest.nigelImg;
    	}
    	else{
    		return RedTest.haroldImg;
    	}
    	 
    }
}
