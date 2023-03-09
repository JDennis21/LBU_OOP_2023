import java.awt.FlowLayout;

import javax.swing.JFrame;

import uk.ac.leedsbeckett.oop.LBUGraphics;

public class GraphicsSystem extends LBUGraphics
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static void main(String[] args)
	{
		new GraphicsSystem();
	}
	
	 public GraphicsSystem()
     {
             JFrame MainFrame = new JFrame();                //create a frame to display the turtle panel on
             MainFrame.setLayout(new FlowLayout());  //not strictly necessary
             MainFrame.add(this);                                    //"this" is this object that extends turtle graphics so we are adding a turtle graphics panel to the frame
             MainFrame.pack();                                               //set the frame to a size we can see
             MainFrame.setVisible(true);                             //now display it
             penDown();   
     }
	
	public void processCommand(String command)      //this method must be provided because LBUGraphics will call it when it's JTextField is used
    {
            //String parameter is the text typed into the LBUGraphics JTextfield
            //lands here if return was pressed or "ok" JButton clicked
            //TO DO 
		String cmd = command;
		System.out.println("command typed " + command);
		if(cmd.equals("about"))
			about();
		if(cmd.equals("penup"))
			penUp();
		if(cmd.equals("pendown"))
			penDown();
		if(cmd.equals("turnleft"))
			turnLeft();
		if(cmd.equals("turnright"))
			turnRight();
		if(cmd.equals("black"))
			setPenColour(PenColour);
    }
}

