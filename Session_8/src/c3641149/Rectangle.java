package c3641149;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape
{
	 protected int width, height;

	     public Rectangle() 
	     {
	    	 super();
	    	 width = 100;
	         height = 100;
	    	
	     }
	     public Rectangle(Color colour, int x, int y, int width, int height) 
	     {
	    	 super(colour, x, y);
	    	 this.width = width; //the only thing that is different from shape
	            this.height = height;
	     }

	   
	     public void set( Color colour, int... list)
	     {
	         //list[0] is x, list[1] is y, list[2] is radius
	         super.set(colour, list[0], list[1]);
	         this.width = list[2];
	         this.height = list[3];
	         

	     }

	 

	     public void draw(Graphics g)
	     {

	       
	         g.setColor(colour);
	         g.fillRect(x, y, width, height);
	         g.setColor(Color.BLACK);
	         g.drawRect(x, y, width, height);
	         
	     }

	     public double calcArea()
	     {
	    	 return width * height;
	     }

	     public double calcPerimeter()
	     {
	    	 return 2 * width + 2 * height;
	     }

	     public String ToString() //all classes inherit from object and ToString() is abstract in object
	     {
	         return super.ToString()+ "rectangle  "+this.width+","+this.width;
	     }
}
