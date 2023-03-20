import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
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
	
	ArrayList<String> allUserInput = new ArrayList<String>();
	
	String[] paramCommands = new String[]{"forward", "backward", "turnleft", "turnright"};
	String[] noParamCommands = new String[] {"about", "penup", "pendown", "blue", "green", "red", "white",
			"reset", "clear", "saveimage", "loadimage", "savecommands", "loadcommands"};
	
	public void processCommand(String command)       
	{
		boolean savedCmd = false, savedImg = false;
		
		allUserInput.add(command.toLowerCase());
		
		String[] userInput = command.toLowerCase().split(" ");
		String cmd = userInput[0];
		
		List<String> paramArray = new ArrayList<>(Arrays.asList(paramCommands));
		List<String> noParamArray = new ArrayList<>(Arrays.asList(noParamCommands));
		
		if(paramArray.contains(cmd) && userInput.length > 1 || noParamArray.contains(cmd))
		{
			if(userInput.length > 1) 
			{
				cmdParam(cmd, paramCommands, userInput);
			}
			else if(userInput.length == 1) 
			{
				cmdNoParam(cmd, noParamCommands, allUserInput, savedImg, savedCmd);
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
	
	public void cmdParam(String cmd, String[] ParamCommands, String[] userInput) 
	{
		try 
		{
			int num_amount = Integer.parseInt(userInput[1]);
			
			Runnable[] paramArray = new Runnable[paramCommands.length];
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
					break;
				}
				else continue;
			}
		}
		catch(Exception e) 
		{
			displayMessage("Parameter requires Integer.");
		}
	}
	
	public void cmdNoParam(String cmd, String[] noParamCommands, ArrayList<String> allUserInput, boolean savedImg, boolean savedCmd)
	{
		Color red = new Color(255, 0, 0), blue  = new Color(0, 0, 255), green = new Color(0, 255, 0), white = new Color(255, 255, 255);;
		
		Runnable[] noParamArray = new Runnable[noParamCommands.length];
		noParamArray[0] = () -> about();
		noParamArray[1] = () -> penUp();
		noParamArray[2] = () -> penDown();
		noParamArray[3] = () -> setPenColour(blue);
		noParamArray[4] = () -> setPenColour(green);
		noParamArray[5] = () -> setPenColour(red);
		noParamArray[6] = () -> setPenColour(white);
		noParamArray[7] = () -> {
			reset();
			penDown();
			setPenColour(red);
		};
		noParamArray[8] = () -> clear();
		noParamArray[9] = () -> saveImg(savedImg);
		noParamArray[10] = () -> loadImg(savedImg);
		noParamArray[11] = () -> saveCmd(allUserInput, savedCmd, cmd);
		noParamArray[12] = () -> loadCmd(savedCmd);
		
		for(int i = 0; i < noParamCommands.length; i++) 
		{
			if(cmd.equals(noParamCommands[i])) 
			{
				noParamArray[i].run();
				break;
			}
			else continue;
		}	
	}
	
	public void saveImg(boolean savedImg)
	{
		try 
		{
		    BufferedImage bufImg = getBufferedImage();  
		    File outputFile = new File("savedImg.png");
		    ImageIO.write(bufImg, "png", outputFile);
		    savedImg = true;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void loadImg(boolean savedImg)
	{
		try 
		{
			File inputFile = new File("savedImg.png");
			
			BufferedImage bufImg = ImageIO.read(inputFile);
			setBufferedImage(bufImg);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveCmd(ArrayList<String> allUserInput, boolean savedCmd, String cmd)  
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter("savedCmd.txt"));
			 for(int i = 0; i < allUserInput.size(); i++)
				 if(!allUserInput.get(i).equals("savecommands") && !allUserInput.get(i).equals("loadcommands"))
					{
				    	bw.write(allUserInput.get(i) + "\n");
					}else continue;
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadCmd(boolean savedCmd)
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader("savedCmd.txt"));
			String line = br.readLine();
			while (line != null) 
			{
				processCommand(line);
				line = br.readLine();
			}
			br.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
}