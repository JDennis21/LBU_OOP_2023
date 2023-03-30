
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
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
		//800,400
	     JFrame MainFrame = new JFrame();  
	     MainFrame.setLayout(new FlowLayout()); 
	     MainFrame.add(this);                    
	     MainFrame.pack();                       
	     MainFrame.setVisible(true);  
	     penDown();
	}

	ArrayList<String> allUserInput = new ArrayList<String>();
	
	String[] paramCommands = new String[]{"saveimage", "loadimage", "savecommands", "loadcommands", "forward", "backward", "turnleft", "turnright", "square",
			"pencolour", "penwidth", "triangle"};
	
	String[] noParamCommands = new String[] {"about", "penup", "pendown", "black", "green", "red", "white",
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
	
	private void cmdParam(String[] ParamCommands, ArrayList<String> allUserInput) 
	{
		String lastUserInput = allUserInput.get(allUserInput.size() - 1);
		String[] userInput = lastUserInput.toLowerCase().split(" ");
		String cmd = userInput[0];
		
		Runnable[] paramArray = new Runnable[paramCommands.length];
		paramArray[0] = () -> handleImg(1, getBufferedImage(), userInput[1]);
		paramArray[1] = () -> handleImg(2, getBufferedImage(), userInput[1]);
		paramArray[2] = () -> handleCmd(1, allUserInput, userInput[1]);
		paramArray[3] = () -> handleCmd(2, allUserInput, userInput[1]);
		
		try 
		{
			if(cmd.equals("triangle")) 
			{
				triangle(Integer.parseInt(userInput[1]), Integer.parseInt(userInput[2]), Integer.parseInt(userInput[3]));
			}
			
			else 
			{
				int R = Integer.parseInt(userInput[1]);
				int G = Integer.parseInt(userInput[2]);
				int B = Integer.parseInt(userInput[3]);
				
				paramArray[9] = () -> penColour(R, G, B);
				
				if((0 <= R && R <= 255) && (0 <= G && G <= 255) && (0 <= B && B <= 255))
				{
					paramArray[9].run();
				}else displayMessage("RGB values must be between 0 and 255.");
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			int numAmount = Integer.parseInt(userInput[1]);
			
			paramArray[4] = () -> forward(numAmount);
			paramArray[5] = () -> forward(-numAmount);
			paramArray[6] = () -> turnLeft(numAmount);
			paramArray[7] = () -> turnRight(numAmount);
			paramArray[8] = () -> square(numAmount);
			paramArray[9] = () -> {};
			paramArray[10] = () -> penWidth(numAmount);
			paramArray[11] = () -> triangle(numAmount);
			
			
			if((cmd.equals("forward") || cmd.equals("backward")) && (0 >= numAmount || numAmount > 100))
				displayMessage("Paramater must be an Integer between 1 and 100");
			else if((cmd.equals("turnright") || cmd.equals("turnleft")) && (0 >= numAmount || numAmount > 360))
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
				for(int i = 0; i < 4; i++) 
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
	
	private void cmdNoParam(String cmd, String[] noParamCommands, ArrayList<String> allUserInput)
	{
		Runnable[] noParamArray = new Runnable[noParamCommands.length];
		noParamArray[0] = () -> about();
		noParamArray[1] = () -> penUp();
		noParamArray[2] = () -> penDown();
		noParamArray[3] = () -> setPenColour(Color.black);
		noParamArray[4] = () -> setPenColour(Color.green);
		noParamArray[5] = () -> setPenColour(Color.red);
		noParamArray[6] = () -> setPenColour(Color.white);
		noParamArray[7] = () -> {
			reset();
			penDown();
			setPenColour(Color.red);
			penWidth(1);
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
	
	private void handleImg(int operation, BufferedImage buffImg, String FileName)
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
	
	private void handleCmd(int operation, ArrayList<String> allCmdArray, String FileName)
	{
		try 
		{
			ArrayList<String> cmdToSave = new ArrayList<String>();
			
			if(operation == 1)
			{
				for(int i = 0; i < allCmdArray.size(); i++)
					 if(!allCmdArray.get(i).split(" ")[0].equals("savecommands") && !allCmdArray.get(i).split(" ")[0].equals("loadcommands"))
						{
						 cmdToSave.add(allCmdArray.get(i));
						}else continue;
				
				if(cmdToSave.size() != 0)
				{
						store.saveString(cmdToSave, FileName);
						savedCmd = true;
				}else displayMessage("Nothing to save.");
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
	
	private void square(int sideLength)
	{
		forward(sideLength);
		turnRight(90);
		forward(sideLength);
		turnRight(90);
		forward(sideLength);
		turnRight(90);
		forward(sideLength);
		turnRight(90);
	}
	
	private void penColour(int R, int G, int B)
	{
		Color col = new Color(R, G, B);
		setPenColour(col);
	}
	
	private void penWidth(int penWidth)
	{
		setStroke(penWidth);
	}
	
	private void triangle(int sideLength)
	{
		turnRight(90);
		forward(sideLength / 2);
		turnLeft(120);
		System.out.println(getxPos() + ", " + getyPos());
		forward(sideLength);
		turnLeft(120);
		System.out.println(getxPos() + ", " + getyPos());
		forward(sideLength);
		turnLeft(120);
		System.out.println(getxPos() + ", " + getyPos());
		forward(sideLength / 2);
		turnLeft(90);
	}
	
	public void triangle(int sideA, int sideB, int sideC)
	{
	    double aAngle =  Math.acos(-(Math.pow(sideA, 2) - Math.pow(sideB, 2) - Math.pow(sideC, 2)) / (2 * sideB * sideC));
	    
	    System.out.println(aAngle);

        Point[] p = new Point[3];

        p[0] = new Point(0, 0);
        p[1] = new Point((int) sideB, 0);
        p[2] = new Point((int) (Math.cos(aAngle) * sideC), (int) (Math.sin(aAngle) * sideC));

        Point center = new Point((p[0].x + p[1].x + p[2].x) / 3, 
                                 (p[0].y + p[1].y + p[2].y) / 3);

        for (Point a : p)
        {
        	a.translate(-center.x, -center.y);
        }
	}
}