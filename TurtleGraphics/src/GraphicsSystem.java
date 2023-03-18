import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
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
	
	public void processCommand(String command)       
	{
		String[] userInput = command.toLowerCase().split(" ");
		String cmd = userInput[0];
				
		String[] paramCommands = new String[]{"forward", "backward", "turnleft", "turnright"};
		String[] noParamCommands = new String[] {"about", "penup", "pendown", "black", "green", "red", "white", "reset", "clear", "saveimage", "loadimage"};
		
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
				cmdNoParam(cmd, noParamCommands);
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
			
			Runnable[] paramArray = new Runnable[4];
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
	
	public void cmdNoParam(String cmd, String[] noParamCommands)
	{
		Color black = new Color(0, 0, 0);
		Color green = new Color(0, 255, 0);
		Color red = new Color(255, 0, 0);
		Color white = new Color(255, 255, 255);
		
		Runnable[] noParamArray = new Runnable[11];
		noParamArray[0] = () -> about();
		noParamArray[1] = () -> penUp();
		noParamArray[2] = () -> penDown();
		noParamArray[3] = () -> setPenColour(black);
		noParamArray[4] = () -> setPenColour(green);
		noParamArray[5] = () -> setPenColour(red);
		noParamArray[6] = () -> setPenColour(white);
		noParamArray[7] = () -> reset();
		noParamArray[8] = () -> clear();
		noParamArray[9] = () -> saveImg();
		noParamArray[10] = () -> loadImg();
		
		
		for(int i = 0; i < noParamCommands.length; i++) 
		{
			if(cmd.equals("reset")) {
				reset();
				penDown();
			}
			else if(cmd.equals(noParamCommands[i])) 
			{
				noParamArray[i].run();
				break;
			}
			else continue;
		}	
	}
	public void saveImg()
	{
		try 
		{
		    BufferedImage bufImg = getBufferedImage();  
		    File outputFile = new File("saved.png");
		    ImageIO.write(bufImg, "png", outputFile);
		} 
		catch (IOException e) 
		{
		    displayMessage("error occured");
		}
	}
	
	public void loadImg() 
	{
		File inputFile = new File("saved.png");
		try
		{
			BufferedImage bufImg = ImageIO.read(inputFile);
			setBufferedImage(bufImg);
		} 
		catch (IOException e)
		{
			displayMessage("error occured");
		}
		
	}
}