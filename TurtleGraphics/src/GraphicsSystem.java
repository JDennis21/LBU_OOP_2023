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
				
		if(cmdCheck(cmd))
		{
			if(cmd_list.length > 1) 
			{
				cmdParam(cmd, cmd_list);
			}
			else if(cmd_list.length == 1) 
			{
				cmdNoParam(cmd);
			}
		}
		else 
		{
			displayMessage("Command is not recongnised.");
		}
	}	
	public Boolean cmdCheck(String cmd) 
	{
		String[] commands = new String[]{"forward", "backward", "turnleft", "turnright", "abou"
				+ "t", "penup", "pendown", "black", "green", "red", "white", "reset", "clear"};
		List<String> cmdList = new ArrayList<>(Arrays.asList(commands));
		
		return cmdList.contains(cmd);
	}
	public void cmdParam(String cmd, String[] cmd_list) 
	{
		try 
		{
			int num_amount = Integer.parseInt(cmd_list[1]);
			if((cmd.equals("forward") || cmd.equals("backward")) && (0 >= num_amount || num_amount > 100))
				displayMessage("Paramater must be an Integer between 1 and 100");
			else if((cmd.equals("turnright") || cmd.equals("turnleft")) && (0 >= num_amount || num_amount > 360))
				displayMessage("Paramater must be an Integer between 1 and 360");
			else if(cmd.equals("forward") && 0 < num_amount && num_amount <= 100)
				forward(num_amount);
			else if(cmd.equals("backward") && 0 < num_amount && num_amount <= 100)
				forward(-num_amount);
			else if(cmd.equals("turnleft") && 0 < num_amount && num_amount < 360)
				turnLeft(num_amount);
			else if(cmd.equals("turnright") && 0 < num_amount && num_amount < 360)
				turnRight(num_amount);	
		}
		catch(Exception e) 
		{
			displayMessage("Parameter requires Integer.");
		}
	}
	public void cmdNoParam(String cmd)
	{
		Color black = new Color(0, 0, 0);
		Color green = new Color(0, 255, 0);
		Color red = new Color(255, 0, 0);
		Color white = new Color(255, 255, 255);
		
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
		else if(cmd.equals("reset")) {
			reset();
			penDown();}
		else if(cmd.equals("clear"))
			clear();
		else 
			displayMessage("Parameter is missing.");	
	}
}