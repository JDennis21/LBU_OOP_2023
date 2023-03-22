package c3641149;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Ellipse
{
	 public void set( Color colour, int... list) //this is a variable parameter list
     {
         super.set(colour, list[0], list[1],list[2],list[2]);
     }

	
	public void draw(Graphics g) 
	{
		super.draw(g);
		
	}

	
	public double calcArea() 
	{
		return super.calcArea();
	}

	
	public double calcPerimeter() 
	{
		return super.calcPerimeter();
	}

	  public String ToString() //all classes inherit from object and ToString() is abstract in object (see Object in Java documentation)
	     {
	         return super.ToString()+ "circle  "+this.radiusx;
	     }
}