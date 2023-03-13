import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import uk.ac.leedsbeckett.oop.LBUGraphics;

public class GraphicsSystem extends LBUGraphics
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		new GraphicsSystem();
	}
	
	public GraphicsSystem()
	{
	     JFrame MainFrame = new JFrame();        
	     MainFrame.setLayout(new FlowLayout()); 
	     MainFrame.add(this);                    
	     MainFrame.pack();                       
	     MainFrame.setVisible(true);  
	     penDown();
	}
	
	public void processCommand(String command)       
	{
		String[] cmd_list = command.toLowerCase().split(" ");
		String cmd = cmd_list[0];
		
		System.out.println("command typed " + command);
				
		if(cmdCheck(cmd))
		{
			paramCheck(cmd, cmd_list, cmd_list.length);
		}
		else 
		{
			System.out.println("Command is not recongnised.");
		}
	}
		
	public Boolean cmdCheck(String cmd) 
	{
		String[] commands = new String[]{"forward", "backward", "turnleft", "turnright", "abou"
				+ "t", "penup", "pendown", "black", "green", "red", "white", "reset", "clear"};
		List<String> cmdList = new ArrayList<>(Arrays.asList(commands));
		
		return cmdList.contains(cmd);
	}
	
	public void paramCheck(String cmd, String[] cmd_list, int ListLength) 
	{
		try 
		{
			Color black = new Color(0, 0, 0);
			Color green = new Color(0, 255, 0);
			Color red = new Color(255, 0, 0);
			Color white = new Color(255, 255, 255);
			
			if(ListLength > 1) 
			{
				int num_amount = Integer.parseInt(cmd_list[1]);
				if(num_amount < 0 || 360 < num_amount)
					System.out.println("Integer must be greater than zero.");
				else if(cmd.equals("forward") && 0 < num_amount && num_amount <= 100)
					forward(num_amount);
				else if(cmd.equals("backward") && 0 < num_amount && num_amount <= 100)
					forward(-num_amount);
				else if(cmd.equals("turnleft") && 0 < num_amount && num_amount <= 360)
					turnLeft(num_amount);
				else if(cmd.equals("turnright") && 0 < num_amount && num_amount <= 360)
					turnRight(num_amount);
			}
			else if(ListLength == 1) 
			{
				if(cmd.equals("about"))
					about();
				else if(cmd.equals("penup"))
					penUp();
				else if(cmd.equals("pendown"))
					penDown();
				else if(cmd.equals("black"))
					setPenColour(black);
				else if(cmd.equals("green")) 
					setPenColour(green);
				else if(cmd.equals("red"))
					setPenColour(red);
				else if(cmd.equals("white"))
					setPenColour(white);
				else if(cmd.equals("reset"))
					reset();
				else if(cmd.equals("clear"))
					clear();
				else 
					System.out.println("Parameter is missing.");	
			}
		}
		catch(Exception e) 
		{
			System.out.println("Parameter requires Integer.");
		}
	}
}