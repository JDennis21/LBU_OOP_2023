package c3641149;

import java.awt.Color;
import java.awt.Graphics;

public class Square extends Rectangle
{
	 public void set( Color colour, int... list)
     {
         //list[0] is x, list[1] is y, list[2] is radius
         super.set(colour, list[0], list[1], list[2], list[2]);
        
     }

	
	public void draw(Graphics g) {
		super.draw(g);
		
	}

	
	public double calcArea() {
		// TODO Auto-generated method stub
		return super.calcArea();
	}

	
	public double calcPerimeter() {
		// TODO Auto-generated method stub
		return super.calcPerimeter();
	}

	  public String ToString() //all classes inherit from object and ToString() is abstract in object
	  {
	         return super.ToString()+ "square  "+this.width+","+this.width;
	  }
}
