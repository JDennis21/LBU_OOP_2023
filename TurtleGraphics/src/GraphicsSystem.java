
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JFrame;

import c3641149.Store;
import c3641149.Triangle;
import uk.ac.leedsbeckett.oop.LBUGraphics;

public class GraphicsSystem extends LBUGraphics
{
	private static final long serialVersionUID = 1L;

	Store store = new Store();
	
	ArrayList<String> allUserInput = new ArrayList<>();
	
	Map<String, Consumer<String[]>> methodMap = new HashMap<>();
	
	boolean savedCmd = true, savedImg = true;
	
	public static void main(String[] args)
	{
		new GraphicsSystem();
	}

	public GraphicsSystem()
	{
		setMethodMap(allUserInput, methodMap);
		
		JFrame MainFrame = new JFrame();
		setPreferredSize(800, 400);
		MainFrame.setLayout(new FlowLayout());
		MainFrame.add(this);
		MainFrame.pack();
		MainFrame.setVisible(true);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		about();
		clear();
		penDown();
	}

	@Override
	public void processCommand(String command)
	{
		allUserInput.add(command.toLowerCase());
	
		String[] userInput = command.toLowerCase().split(" ");

		if(methodMap.containsKey(userInput[0]))
		{
			if(paramCheck(userInput))
			{
				handleImg("panel", getBufferedImage(), "revertpanel");
				int [] turtlePos = new int[] {getxPos(), getyPos()};
				
					runMethod(userInput);
					posCheck(turtlePos);
			}else allUserInput.remove(userInput[0]);
		} 
		
		else
		{
			displayMessage("Command is not recongnised.");
			allUserInput.remove(userInput[0]);
		}
	}
	
	public void runMethod(String[] userInput)
	{
		String cmd = userInput[0];
		String[] parameters = Arrays.copyOfRange(userInput, 1, userInput.length);
		
		if(cmd.equals("triangle"))
		{
			if(userInput.length == 4)
			{
				triangle(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]));
			}else triangle(Integer.parseInt(parameters[0]));
		}
		
		else
		{
			methodMap.get(cmd).accept(parameters);
		}
		
		if(cmd.equals("clear"))
		{
			savedCmd = true;
			savedImg = true;
		} 
		
		else
		{
			savedCmd = false;
			savedImg = false;
		}
	}
	
	public void setMethodMap(ArrayList<String> allUserInput, Map<String, Consumer<String[]>> methodMap)
	{	
		methodMap.put("penup",   (sArray) -> penUp());
		methodMap.put("pendown", (sArray) -> penDown());
		methodMap.put("black",   (sArray) -> setPenColour(Color.black));
		methodMap.put("green",   (sArray) -> setPenColour(Color.green));
		methodMap.put("red",     (sArray) -> setPenColour(Color.red));
		methodMap.put("white",   (sArray) -> setPenColour(Color.white));
		methodMap.put("clear",   (sArray) -> clear());
		methodMap.put("reset",   (sArray) -> reset());
		methodMap.put("about",   (sArray) -> about());
		
		methodMap.put("saveimage",    (sArray) -> handleImg("save", getBufferedImage(), sArray[0]));
		methodMap.put("loadimage",    (sArray) -> handleImg("load", getBufferedImage(), sArray[0]));
		methodMap.put("savecommands", (sArray) -> handleCmd("save", allUserInput, sArray[0]));
		methodMap.put("loadcommands", (sArray) -> handleCmd("load", allUserInput, sArray[0]));
		
		methodMap.put("forward",   (sArray) -> forward(Integer.parseInt(sArray[0])));
		methodMap.put("backward",  (sArray) -> forward(-Integer.parseInt(sArray[0])));
		methodMap.put("turnleft",  (sArray) -> turnLeft(Integer.parseInt(sArray[0])));
		methodMap.put("turnright", (sArray) -> turnRight(Integer.parseInt(sArray[0])));
		methodMap.put("square",    (sArray) -> square(Integer.parseInt(sArray[0])));
		methodMap.put("penwidth",  (sArray) -> penWidth(Integer.parseInt(sArray[0])));
		methodMap.put("triangle",  (sArray) -> triangle(Integer.parseInt(sArray[0])));
		
		methodMap.put("triangle",  (sArray) -> triangle(Integer.parseInt(sArray[0]), Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
		methodMap.put("pencolour", (sArray) -> penColour(Integer.parseInt(sArray[0]), Integer.parseInt(sArray[1]), Integer.parseInt(sArray[2])));
	}
	
	public boolean paramCheck(String[] userInput)
	{
		String cmd = userInput[0];
		
		String[] oneParam = {"saveimage", "loadimage", "savecommands", "loadcommands", "forward", "backward", "turnleft", "turnright", "square", "penwidth", "triangle"};
		String[] threeParam = {"triangle", "pencolour"};
		
		if(userInput.length == 1 && !(Arrays.asList(oneParam).contains(cmd)) && !(Arrays.asList(threeParam).contains(cmd))) 
		{
			return true;
		} 
		
		else if(Arrays.asList(oneParam).contains(cmd) && userInput.length == 2) 
		{
			return boundsCheck(cmd, userInput);
		} 
		 
		else if(Arrays.asList(threeParam).contains(cmd) && userInput.length == 4) 
		{
			return boundsCheck(cmd, userInput);
		}
		else if(cmd.equals("triangle") && userInput.length != 2 && userInput.length != 4)
		{
			displayMessage("Triangle requires one or three parametes.");
			return false;
		}
		
		else if(Arrays.asList(oneParam).contains(cmd) && userInput.length != 2) 
		{
			displayMessage("Command requires a parameter");
			return false;
		} 
		
		else if(Arrays.asList(threeParam).contains(cmd) && userInput.length != 4) 
		{
			displayMessage("Command requires three parameters");
			return false;
		}	
		
		else if(userInput.length != 1) 
		{
			displayMessage("Command requires no parameters.");
			return false;
		}else return false;
	}
	
	public boolean boundsCheck(String cmd, String[] userInput)
	{
		try
		{
			int numAmount = Integer.parseInt(userInput[1]);
			
			if((cmd.equals("forward") || cmd.equals("backward")) && (0 >= numAmount || numAmount > 100))
			{
				displayMessage("Parameter must be an Integer between 1 and 100");
				return false;
			}
			
			else if((cmd.equals("turnright") || cmd.equals("turnleft")) && (0 >= numAmount || numAmount > 360))
			{
				displayMessage("Parameter must be an Integer between 1 and 360");
				return false;
			}
			
			else if(cmd.equals("pencolour"))
			{
				int num2 = Integer.parseInt(userInput[2]);
				int num3 = Integer.parseInt(userInput[3]);
				
				if(0 >= numAmount || numAmount > 255 || 0 >= num2 || num2 > 255 || 0 >= num3 || num3 > 255)
				{
					displayMessage("RGB values must be between 1 and 255");
					return false;
				}
			}else return true;
		}
		
		catch(NumberFormatException e)
		{
			if(cmd.contains("save") || cmd.contains("load"))
			{
				return true;
			}
			
			else
			{
			displayMessage("Command requires Integer.");
			return false;
			}
		}
		return false;
	}
	
	public void posCheck(int[] turtlePos)
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
	
	public void handleImg(String operation, BufferedImage buffImg, String FileName)
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
			
			else if(operation.contains("panel"));
			{
				store.saveImg(buffImg, FileName);
			}
		}

		catch(Exception e)
		{
			displayMessage("File not found.");
		}
	}

	public void handleCmd(String operation, ArrayList<String> allCmdArray, String FileName)
	{
		try
		{
			allCmdArray.removeIf(cmd -> cmd.contains("save") || cmd.contains("load"));

			if(operation.substring(0, 4).equals("save"))
			{
				if (allCmdArray.size() != 0)
				{
					store.saveString(allCmdArray, FileName);
					savedCmd = true;
				}else displayMessage("Nothing to save.");
			} 
			
			else if(operation.substring(0, 4).equals("load"))
			{
				if(store.checkSave(1, savedCmd))
				{
					for(String command : store.loadString(FileName))
					{
						processCommand(command);
						System.out.println(command);
					}
					savedCmd = true;
				}
			}
		}

		catch(IOException e)
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

	public void penWidth(int penWidth)
	{
		setStroke(penWidth);
	}
	
	public void square(int sideLength)
	{
		for(int i = 0; i < 4; i++)
		{
			forward(sideLength);
			turnRight(90);
		}
	}

	public void triangle(int sideLength)
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

	public void triangle(int sideA, int sideB, int sideC)
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
	
	@Override
	public void reset()
	{
		super.reset();
		penDown();
		setPenColour(Color.red);
		penWidth(1);
	}
	
	@Override
	public void about()
	{
		super.about();
		penWidth(1);
	}
}