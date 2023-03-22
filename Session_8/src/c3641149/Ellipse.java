package c3641149;

import java.awt.Color;
import java.awt.Graphics;

public class Ellipse extends Shape
{
	protected int radiusx, radiusy;

	     public Ellipse() 
	     {
	    	 super();
	     }
	     
	     public Ellipse(Color colour, int x, int y, int radiusx, int radiusy) 
	     {
	    	 super(colour, x, y);
	         this.radiusx = radiusx; //the only thing that is different from shape
	         this.radiusy = radiusy;
	     }

	   
	     public void set( Color colour, int... list)
	     {
	         //list[0] is x, list[1] is y, list[2] is radius
	         super.set(colour, list[0], list[1]);
	         this.radiusx = list[2];
	         this.radiusy = list[3];

	     }

	 

	     public void draw(Graphics g)
	     {
	    	 g.setColor(colour);
	         g.fillOval(x, y, radiusx * 2, radiusy * 2);
	         g.setColor(Color.BLACK);
	         g.drawOval(x, y, radiusx * 2, radiusy * 2);
	         
	     }

	     public double calcArea()
	     {
	         return Math.PI * radiusx * radiusy;
	     }

	     public double calcPerimeter()
	     {
	         return Math.PI*2*(Math.sqrt(radiusx*radiusx+radiusy*radiusy)/2);
	     }

	     public String ToString() //all classes inherit from object and ToString() is abstract in object
	     {
	         return super.ToString()+ "ellipse  "+this.radiusx+" "+this.radiusy;
	     }
}
