package c3641149;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 
 * @author Duncan Mullier
 * Shapes interface
 */
public interface Shapes 
{
	/**
	 * set setter for an instance, sets the colour of a shape and its properties 
	 * @param c colour of object
	 * @param list, can vary depending on object but take the form x,y, size, size
	 * 
	 */
	void set(Color c, int... list); //note params int[] list in c#
	
	/**
	 * draw renders the object on the graphics context passed
	 * @param g graphics context ion which to draw the object
	 */
    void draw(Graphics g);
    
    /**
     * Calculates the area of the shape
     * @return area
     */
    double calcArea();
    
    /**
     * calculates the perimeter of the shape
     * @return perimeter
     */
    double calcPerimeter();

}
