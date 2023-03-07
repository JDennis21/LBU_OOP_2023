
import java.awt.FlowLayout;

import javax.swing.JFrame;

import uk.ac.leedsbeckett.oop.LBUGraphics;

public class Main extends LBUGraphics
{
        public static void main(String[] args)
        {
                new Main(); //create instance of class that extends LBUGraphics (could be separate class without main), gets out of static context
        }

        public Main()
        {
                JFrame MainFrame = new JFrame();                //create a frame to display the turtle panel on
                MainFrame.setLayout(new FlowLayout());  //not strictly necessary
                MainFrame.add(this);                                    //"this" is this object that extends turtle graphics so we are adding a turtle graphics panel to the frame
                MainFrame.pack();                                               //set the frame to a size we can see
                MainFrame.setVisible(true);                             //now display it
                //about();                                                                //call the LBUGraphics about method to display version information.
                penDown();
                forward(100);
                turnRight();
                forward(100);
        }

        
        public void processCommand(String command)      //this method must be provided because LBUGraphics will call it when it's JTextField is used
        {
                //String parameter is the text typed into the LBUGraphics JTextfield
                //lands here if return was pressed or "ok" JButton clicked
                //TO DO 
        	String cmd, num;
        	System.out.println("command typed" + command);
        	String[] set = command.split(" ");
        	cmd = set[0];
        	num = set[1];
        	if(cmd.equals("turnleft") == true);
        	{
        		turnLeft();
        	}
        	if(cmd.equals("forward") == true);
        	{
        		forward(num);
        	}
        	
        }
}
