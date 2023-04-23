
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import c3641149.Store;
import c3641149.Triangle;
import uk.ac.leedsbeckett.oop.LBUGraphics;

public class GraphicsSystem extends LBUGraphics
{
	private static final long serialVersionUID = 1L;

	Store store = new Store();
	
	ArrayList<String> allUserInput = new ArrayList<>();

	private final String[] COMMANDS = new String[] {"about", "penup", "pendown", "black", "green", "red", "white", "reset", "clear", "saveimage", "loadimage",
			"savecommands", "loadcommands", "forward", "backward", "turnleft", "turnright", "square", "penwidth", "triangle", "pencolour"};

	boolean savedCmd = true, savedImg = true;
	
	public static void main(String[] args)
	{
		new GraphicsSystem();
	}

	public GraphicsSystem()
	{
		JFrame MainFrame = new JFrame();
		setPreferredSize(800, 400);
		MainFrame.setLayout(new FlowLayout());
		MainFrame.add(this);
		MainFrame.pack();
		MainFrame.setVisible(true);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		penDown();
	}

	@Override
	public void processCommand(String command)
	{
		int [] turtlePos = new int[] {getxPos(), getyPos()};
		handleImg("save", getBufferedImage(), "revertpanel");
		
		allUserInput.add(command.toLowerCase());

		String[] userInput = command.toLowerCase().split(" ");
		String cmd = userInput[0];

		List<String> commandsArrayList = new ArrayList<>(Arrays.asList(COMMANDS));
		Runnable[] methodArray = new Runnable[COMMANDS.length];

		if(commandsArrayList.contains(cmd))
		{
			if(setMethodArray(allUserInput, methodArray))
			{
				try
				{
					for(int i = 0; i < COMMANDS.length; i++)
					{
						if(cmd.equals(COMMANDS[i]))
						{
							methodArray[i].run();
							displayMessage("LBUgraphics V4.4");
							if(cmd.equals("clear") || cmd.substring(0, 4).equals("save") || cmd.substring(0, 4).equals("load"))
							{
								savedCmd = true;
								savedImg = true;
							} 
							
							else
							{
								savedCmd = false;
								savedImg = false;
							}
						}else continue;
					}
					posCheck(turtlePos);	
				}
				
				catch(java.lang.NullPointerException e)
				{
					displayMessage("Command requires a parameter.");
				}
			}
		} 
		
		else
		{
			displayMessage("Command is not recongnised.");
		}
	}

	private boolean setMethodArray(ArrayList<String> allUserInput, Runnable[] methodArray)
	{
	String[] userInput = allUserInput.get(allUserInput.size() - 1).toLowerCase().split(" ");
	String cmd = userInput[0];
	
	String[] oneParam = {"saveimage", "loadimage", "savecommands", "loadcommands", "forward", "backward", "turnleft", "turnright", "square", "penwidth"};
	String[] threeParam = {"triangle", "pencolour"};
	
		if(userInput.length == 1)
		{
			methodArray[0] = () -> about();
			methodArray[1] = () -> penUp();
			methodArray[2] = () -> penDown();
			methodArray[3] = () -> setPenColour(Color.black);
			methodArray[4] = () -> setPenColour(Color.green);
			methodArray[5] = () -> setPenColour(Color.red);
			methodArray[6] = () -> setPenColour(Color.white);
			methodArray[7] = () ->
			{
				reset();
				penDown();
				setPenColour(Color.red);
				penWidth(1);
			};
			methodArray[8] = () -> clear();
			
			return true;
		}
		
		else if(userInput.length == 2)
		{
			methodArray[9] = () -> handleImg("save", getBufferedImage(), userInput[1]);
			methodArray[10] = () -> handleImg("load", getBufferedImage(), userInput[1]);
			methodArray[11] = () -> handleCmd("save", allUserInput, userInput[1]);
			methodArray[12] = () -> handleCmd("load", allUserInput, userInput[1]);
			
			try
			{
				if(!cmd.equals("savecommands") && !cmd.equals("loadcommands") && !cmd.equals("saveimage") && !cmd.equals("loadimage"))
				{
					int numAmount = Integer.parseInt(userInput[1]);
					
					methodArray[13] = () -> forward(numAmount);
					methodArray[14] = () -> forward(-numAmount);
					methodArray[15] = () -> turnLeft(numAmount);
					methodArray[16] = () -> turnRight(numAmount);
					methodArray[17] = () -> square(numAmount);
					methodArray[18] = () -> penWidth(numAmount);
					methodArray[19] = () -> triangle(numAmount);	
				
					if(!Arrays.asList(oneParam).contains(cmd))
					{
						displayMessage("Command requires no parameters.");
						return false;
					}
					
					else if(userInput.length > 2 && cmd.equals("triangle"))
					{
						displayMessage("Triangle command requires 1 or 3 paramaters.");
						return false;
					}
					
					else if((cmd.equals("forward") || cmd.equals("backward")) && (0 >= numAmount || numAmount > 100))
					{
						displayMessage("Command must be an Integer between 1 and 100");
						allUserInput.remove(allUserInput.size() - 1);
						return false;
					}
					
					else if((cmd.equals("turnright") || cmd.equals("turnleft")) && (0 >= numAmount || numAmount > 360))
					{
						displayMessage("Command must be an Integer between 1 and 360");
						allUserInput.remove(allUserInput.size() - 1);
						return false;
					}else return true;
				}
			}
			
			catch(NumberFormatException e)
			{
				displayMessage("Command requires Integer.");
				allUserInput.remove(allUserInput.size() - 1);	
				return false;
			}
		}
		
		else if(userInput.length > 2)
		{
			try
			{
				if(!Arrays.asList(threeParam).contains(cmd))
				{
					displayMessage("Command does not require multiple parameters");
					return false;
				}
				else if(Arrays.asList(threeParam).contains(cmd) && userInput.length > 4)
				{
					displayMessage("Command requires 3 parameters.");
					return false;
				}
				
				int num1 = Integer.parseInt(userInput[1]);
				int num2 = Integer.parseInt(userInput[2]);
				int num3 = Integer.parseInt(userInput[3]);
				
				methodArray[19] = () -> triangle(num1, num2, num3); 
				methodArray[20] = () -> penColour(num1, num2, num3); 
				
				return true;
			}
			
			catch(NumberFormatException e)
			{
				displayMessage("Command requires Integer.");
				allUserInput.remove(allUserInput.size() - 1);	
				return false;
			}
		}
		return false;
	}
	
	private void posCheck(int[] turtlePos)
	{	
		if((getxPos() > 800 || getxPos() < 0) || (getyPos() > 400 || getyPos() < 0))
		{
			displayMessage("Turtle out of bounds.");
			
			savedImg = true;
			handleImg("load", getBufferedImage(), "revertpanel");
			savedImg = false;
			
			setxPos(turtlePos[0]);
			setyPos(turtlePos[1]);
			
			allUserInput.remove(allUserInput.size() - 1);
		}
	}
	
	private void handleImg(String operation, BufferedImage buffImg, String FileName)
	{
		try
		{
			if(operation.substring(0, 4).equals("save"))
			{
				store.saveImg(buffImg, FileName);
				savedImg = true;
			} 
			
			else if(operation.substring(0, 4).equals("load"))
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

	private void handleCmd(String operation, ArrayList<String> allCmdArray, String FileName)
	{
		try
		{
			ArrayList<String> cmdToSave = new ArrayList<>();

			if(operation.substring(0, 4).equals("save"))
			{
				for(int i = 0; i < allCmdArray.size(); i++)
					if(!allCmdArray.get(i).split(" ")[0].equals("savecommands") && !allCmdArray.get(i).split(" ")[0].equals("loadcommands"))
					{
						cmdToSave.add(allCmdArray.get(i));
					}else continue;

				if (cmdToSave.size() != 0)
				{
					store.saveString(cmdToSave, FileName);
					savedCmd = true;
				}else displayMessage("Nothing to save.");
			} 
			
			else if(operation.substring(0, 4).equals("load"))
			{
				if(store.checkSave(1, savedCmd))
				{
					ArrayList<String> commands = store.loadString(FileName);

					for(String command : commands)
					{
						processCommand(command);
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
	
	private void penColour(int R, int G, int B)
	{
		Color col = new Color(R, G, B);
		setPenColour(col);
	}

	private void penWidth(int penWidth)
	{
		setStroke(penWidth);
	}
	
	private void square(int sideLength)
	{
		for(int i = 0; i < 4; i++)
		{
			forward(sideLength);
			turnRight(90);
		}
	}

	private void triangle(int sideLength)
	{
		turnRight(90);
		forward(sideLength / 2);
		turnLeft(120);
		forward(sideLength);
		turnLeft(120);
		forward(sideLength);
		turnLeft(120);
		forward(sideLength / 2);
		turnLeft(90);
	}

	private void triangle(int sideA, int sideB, int sideC)
	{
		Triangle t = new Triangle();
		
		int[] sides = new int[] {sideA, sideB, sideC};
		Arrays.sort(sides);
		
		int[] angles = t.triangleDegrees(sides[1], sides[0], sides[2]);
		
		if(angles[0] != 0 && angles[1] != 0 && angles[2] != 0)
		{
			turnRight(90);
			forward(sides[2] / 2);
			turnLeft(180 - angles[1]);
			forward(sides[1]);
			turnLeft(180 - angles[2]);
			forward(sides[0]);
			turnLeft(180 - angles[0]);
			forward(sides[2] / 2);
			turnLeft(90);
		}
		
		else 
		{
			displayMessage("Unable to create triangle with these sides.");
			allUserInput.remove(allUserInput.size() - 1);
		}
	}
}