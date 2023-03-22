
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import c3641149.Store;
import uk.ac.leedsbeckett.oop.LBUGraphics;

public class GraphicsSystem extends LBUGraphics
{
	private static final long serialVersionUID = 1L;
	
	Store store = new Store();

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
	
	ArrayList<String> allUserInput = new ArrayList<String>();
	
	String[] paramCommands = new String[]{"forward", "backward", "turnleft", "turnright", "saveimage", "loadimage", "savecommands", "loadcommands"};
	
	String[] noParamCommands = new String[] {"about", "penup", "pendown", "blue", "green", "red", "white",
			"reset", "clear"};
	
	boolean savedCmd = true, savedImg = true
			;
	public void processCommand(String command)       
	{
		allUserInput.add(command.toLowerCase());
		
		String[] userInput = command.toLowerCase().split(" ");
		String cmd = userInput[0];
		
		List<String> paramArray = new ArrayList<>(Arrays.asList(paramCommands));
		List<String> noParamArray = new ArrayList<>(Arrays.asList(noParamCommands));
		
		if(paramArray.contains(cmd) && userInput.length > 1 || noParamArray.contains(cmd))
		{
			if(userInput.length > 1) 
			{
				cmdParam(paramCommands, allUserInput);
			}
			else if(userInput.length == 1) 
			{
				cmdNoParam(cmd, noParamCommands, allUserInput);
			}
		}
		else if(paramArray.contains(cmd)) 
		{
			displayMessage("Command requires a parameter.");
		}
		else 
		{
			displayMessage("Command is not recongnised.");
		}
	}	
	
	public void cmdParam(String[] ParamCommands, ArrayList<String> allUserInput) 
	{
		String lastUserInput = allUserInput.get(allUserInput.size() - 1);
		String[] userInput = lastUserInput.toLowerCase().split(" ");
		String cmd = userInput[0];
		
		Runnable[] paramArray = new Runnable[paramCommands.length];
		paramArray[0] = () -> forward(0);
		paramArray[1] = () -> forward(0);
		paramArray[2] = () -> turnLeft(0);
		paramArray[3] = () -> turnRight(0);
		paramArray[4] = () -> handleImg(1, getBufferedImage(), userInput[1]);
		paramArray[5] = () -> handleImg(2, getBufferedImage(), userInput[1]);
		paramArray[6] = () -> handleCmd(1, allUserInput, userInput[1]);
		paramArray[7] = () -> handleCmd(2, allUserInput, userInput[1]);
		
		try 
		{
			int num_amount = Integer.parseInt(userInput[1]);
			
			paramArray[0] = () -> forward(num_amount);
			paramArray[1] = () -> forward(-num_amount);
			paramArray[2] = () -> turnLeft(num_amount);
			paramArray[3] = () -> turnRight(num_amount);
			
			if((cmd.equals("forward") || cmd.equals("backward")) && (0 >= num_amount || num_amount > 100))
				displayMessage("Paramater must be an Integer between 1 and 100");
			else if((cmd.equals("turnright") || cmd.equals("turnleft")) && (0 >= num_amount || num_amount > 360))
				displayMessage("Paramater must be an Integer between 1 and 360");
			else for(int i = 0; i < ParamCommands.length; i++) 
			{
				if(cmd.equals(ParamCommands[i])) 
				{
					paramArray[i].run();
					savedCmd = false;
					savedImg = false;
					break;
				}else continue;
			}
		}
		catch(Exception e) 
		{
			if(cmd.substring(0, 4).equals("save") || cmd.substring(0, 4).equals("load"))
			{
				for(int i = 4; i < ParamCommands.length; i++) 
				{
					if(cmd.equals(ParamCommands[i])) 
					{
						paramArray[i].run();
						break;
					}else continue;
				}
			}else displayMessage("Parameter requires Integer.");
		}
	}
	
	public void cmdNoParam(String cmd, String[] noParamCommands, ArrayList<String> allUserInput)
	{
		Runnable[] noParamArray = new Runnable[noParamCommands.length];
		noParamArray[0] = () -> about();
		noParamArray[1] = () -> penUp();
		noParamArray[2] = () -> penDown();
		noParamArray[3] = () -> setPenColour(Color.blue);
		noParamArray[4] = () -> setPenColour(Color.green);
		noParamArray[5] = () -> setPenColour(Color.red);
		noParamArray[6] = () -> setPenColour(Color.white);
		noParamArray[7] = () -> {
			reset();
			penDown();
			setPenColour(Color.red);
		};
		noParamArray[8] = () -> clear();
		
		for(int i = 0; i < noParamCommands.length; i++) 
		{
			if(cmd.equals(noParamCommands[i])) 
			{
				noParamArray[i].run();
				if(!cmd.equals("clear")) 
				{
					savedCmd = false;
					savedImg = false;
				}else 
					savedCmd = true; 
					savedImg = true;
			}else continue;
			break;
		}	
	}
	
	public void handleImg(int operation, BufferedImage buffImg, String FileName)
	{
		try 
		{
			if(operation == 1)
			{
				store.saveImg(buffImg, FileName);
				savedImg = true;
			}
			else if(operation == 2)
			{
				if(store.checkSave(2, savedImg))
				{
					setBufferedImage(store.loadImg(FileName));
				}	
				savedImg = true;
			}
		}
		
		catch(Exception e)
		{
			displayMessage("File not found.");
		}
	}
	
	public void handleCmd(int operation, ArrayList<String> inCmdArray, String FileName)
	{
		try 
		{
			if(operation == 1)
			{
				for(int i = 0; i < inCmdArray.size(); i++)
					 if(!inCmdArray.get(i).equals("savecommands") && !inCmdArray.get(i).equals("loadcommands"))
						{
						 store.saveString(inCmdArray.get(i), FileName);
						}else continue;
				savedCmd = true;
			}
			else if(operation == 2)
			{
				if(store.checkSave(1, savedCmd))
				{
					ArrayList<String> commands = store.loadString(FileName);
					
					for(int i = 0; i < commands.size(); i++)
					{
						processCommand(commands.get(i));
					}
					savedCmd = true;
				}
			}
		}
		
		catch(Exception e)
		{
			displayMessage("File not found.");
			return;
		}
	}
}