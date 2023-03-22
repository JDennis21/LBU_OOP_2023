package c3641149;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape implements Shapes 
{
	protected Color colour; //shape's colour
    protected int x, y; //not I could use c# properties for this
    
    public Shape()
    {
        colour = Color.RED;
        x = y = 100;
    }
	public Shape(Color colour, int x, int y)
    {

        this.colour = colour; //shape's colour
        this.x = x; //its x pos
        this.y = y; //its y pos
        //can't provide anything else as "shape" is too general
    }
	
	public void set(Color c, int... list) 
	{
		 this.colour = c;
         this.x = list[0];
         this.y = list[1];
	}

	public String ToString()
    {
        return super.toString()+"    "+this.x+","+this.y+" : "; //note c# base instead of super and ToString()
    }
	public abstract void draw(Graphics g); 
	

	@Override
	public abstract double calcArea(); 

	@Override
	public abstract double calcPerimeter(); 

}
