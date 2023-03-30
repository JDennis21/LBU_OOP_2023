
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

	static final String[] PARAM_COMMANDS = new String[] {"saveimage", "loadimage", "savecommands", "loadcommands", "forward", "backward", "turnleft", "turnright",
			"square", "pencolour", "penwidth", "triangle"};

	static final String[] NO_PARAM_COMMANDS = new String[] {"about", "penup", "pendown", "black", "green", "red", "white", "reset", "clear"};

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

		List<String> paramArray = new ArrayList<>(Arrays.asList(PARAM_COMMANDS));
		List<String> noParamArray = new ArrayList<>(Arrays.asList(NO_PARAM_COMMANDS));

		if (paramArray.contains(cmd) && userInput.length > 1 || noParamArray.contains(cmd))
		{
			if (userInput.length > 1)
			{
				cmdParam(PARAM_COMMANDS, allUserInput);
				posCheck(turtlePos);
					
			} 
			
			else if (userInput.length == 1)
			{
				cmdNoParam(cmd, NO_PARAM_COMMANDS, allUserInput);
				posCheck(turtlePos);
			}
		} 
		
		else if (paramArray.contains(cmd))
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

		Runnable[] paramArray = new Runnable[PARAM_COMMANDS.length];
		paramArray[0] = () -> handleImg("save", getBufferedImage(), userInput[1]);
		paramArray[1] = () -> handleImg("load", getBufferedImage(), userInput[1]);
		paramArray[2] = () -> handleCmd("save", allUserInput, userInput[1]);
		paramArray[3] = () -> handleCmd("load", allUserInput, userInput[1]);

		try
		{
			if (cmd.equals("triangle"))
			{
				triangle(Integer.parseInt(userInput[1]), Integer.parseInt(userInput[2]),
						Integer.parseInt(userInput[3]));
			}

			else
			{
				int R = Integer.parseInt(userInput[1]);
				int G = Integer.parseInt(userInput[2]);
				int B = Integer.parseInt(userInput[3]);

				paramArray[9] = () -> penColour(R, G, B);

				if ((0 <= R && R <= 255) && (0 <= G && G <= 255) && (0 <= B && B <= 255))
				{
					paramArray[9].run();
				} else displayMessage("RGB values must be between 0 and 255.");
			}
		} 
		
		catch (ArrayIndexOutOfBoundsException e)
		{
			int numAmount = Integer.parseInt(userInput[1]);

			paramArray[4] = () -> forward(numAmount);
			paramArray[5] = () -> forward(-numAmount);
			paramArray[6] = () -> turnLeft(numAmount);
			paramArray[7] = () -> turnRight(numAmount);
			paramArray[8] = () -> square(numAmount);
			paramArray[9] = () ->{};
			paramArray[10] = () -> penWidth(numAmount);
			paramArray[11] = () -> triangle(numAmount);

			if ((cmd.equals("forward") || cmd.equals("backward")) && (0 >= numAmount || numAmount > 100))
			{
				displayMessage("Paramater must be an Integer between 1 and 100");
				allUserInput.remove(allUserInput.size() - 1);
			}
			
			else if ((cmd.equals("turnright") || cmd.equals("turnleft")) && (0 >= numAmount || numAmount > 360))
			{
				displayMessage("Paramater must be an Integer between 1 and 360");
				allUserInput.remove(allUserInput.size() - 1);
			}
			
			else
			{
				for (int i = 0; i < ParamCommands.length; i++)
				{
					if (cmd.equals(ParamCommands[i]))
					{
						paramArray[i].run();
						savedCmd = false;
						savedImg = false;
						break;
					} else continue;
				}
			}
		} 
		
		catch (Exception e)
		{
			if (cmd.substring(0, 4).equals("save") || cmd.substring(0, 4).equals("load"))
			{
				for (int i = 0; i < 4; i++)
				{
					if (cmd.equals(ParamCommands[i]))
					{
						paramArray[i].run();
						break;
					} else continue;
				}
			} 
			
			else 
			{
				displayMessage("Parameter requires Integer.");
				allUserInput.remove(allUserInput.size() - 1);
			}
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
		noParamArray[7] = () ->
		{
			reset();
			penDown();
			setPenColour(Color.red);
			penWidth(1);
		};
		noParamArray[8] = () -> clear();

		for (int i = 0; i < noParamCommands.length; i++)
		{
			if (cmd.equals(noParamCommands[i]))
			{
				noParamArray[i].run();
				if (!cmd.equals("clear"))
				{
					savedCmd = false;
					savedImg = false;
				} 
				
				else
				{
					savedCmd = true;
					savedImg = true;
				}
			} else continue;
		}
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
			if (operation.substring(0, 4).equals("save"))
			{
				store.saveImg(buffImg, FileName);
				savedImg = true;
			} 
			
			else if (operation.substring(0, 4).equals("load"))
			{
				if (store.checkSave(2, savedImg))
				{
					setBufferedImage(store.loadImg(FileName));
				}
				savedImg = true;
			}
		}

		catch (Exception e)
		{
			displayMessage("File not found.");
		}
	}

	private void handleCmd(String operation, ArrayList<String> allCmdArray, String FileName)
	{
		try
		{
			ArrayList<String> cmdToSave = new ArrayList<>();

			if (operation.substring(0, 4).equals("save"))
			{
				for (int i = 0; i < allCmdArray.size(); i++)
					if (!allCmdArray.get(i).split(" ")[0].equals("savecommands") && !allCmdArray.get(i).split(" ")[0].equals("loadcommands"))
					{
						cmdToSave.add(allCmdArray.get(i));
					} else continue;

				if (cmdToSave.size() != 0)
				{
					store.saveString(cmdToSave, FileName);
					savedCmd = true;
				} else displayMessage("Nothing to save.");
			} 
			
			else if (operation.substring(0, 4).equals("load"))
			{
				if (store.checkSave(1, savedCmd))
				{
					ArrayList<String> commands = store.loadString(FileName);

					for (String command : commands)
					{
						processCommand(command);
					}
					savedCmd = true;
				}
			}
		}

		catch (Exception e)
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