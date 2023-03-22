import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;

import c3641149.*;


public class Application extends JFrame
{
	
	/**
	 * example showing a simple shape hiararchy with abstract methods and an interface
	 */
	private static final long serialVersionUID = 1L;
	final int XSIZE = 640, YSIZE = 480;
	int headX = 10, headY = 50, headXsize = 150, headYsize = 130;
	int eyeX = headX+(headXsize/4), eyeY = headY+(headYsize/5), eyeSize = headXsize /10, eyeGap = headXsize/3; 
	int mouthX = eyeX,  mouthY = eyeY + headY, mouthXsize = eyeSize*3, mouthYsize = eyeSize;
	
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public static void main(String[] args) 
	{
		new Application();
	}
	
	public Application()
	{
		
		setSize(XSIZE, YSIZE);
		
		setVisible(true);
		
		Shape head, lefteye, righteye, mouth;
		Color col;
		//head
		head = new Rectangle();
  		col = new Color(255, 0, 0);
        head.set(col, headX, headY, headXsize, headYsize);
        shapes.add(head);
			
        //left eye
        lefteye = new Circle();		
        col = new Color(255, 255, 255);
        lefteye.set(col, eyeX, eyeY, eyeSize);
        shapes.add(lefteye);
			
        //right eye 
        righteye = new Circle();
        col = new Color(255, 255, 255);
        righteye.set(col, eyeX+eyeGap, eyeY, eyeSize);
        shapes.add(righteye);
			
        //mouth
        mouth = new Ellipse();
        col = new Color(0,0,0);
        mouth.set(col, mouthX, mouthY, mouthXsize, mouthYsize);
        shapes.add(mouth);
	}
	
	/**
	 * draw the shapes in the array list by using a polymorphic reference to call the relevant draw method in the sape
	 */
	public void paint(Graphics g)
	{
		//draw the shapes in the arra
		setBackground(Color.white);	//set the background colour of the frame to white
		
		for (int i = 0; i<shapes.size(); i++)
		{
			Shape s;	//polymorphics reference because a shaoe can point diown in the hierarchy
			s = (Shape) shapes.get(i);	//get the current shape (but what is it?)
			if (s != null)
			{
				s.draw(g);		//call the draw method (but which one?)
				System.out.println(s.ToString()); //call the toSring method (but which one?)
			}
			else
				System.out.println("invalid shape in array"); //shouldn't happen as factory does not produce rubbish

         }
	}
}
