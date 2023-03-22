package c3641149;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class Store 
{
	public boolean checkSave(int type, boolean saved)
	{
		if(saved == false && type == 1)
		{
			int yesNo = JOptionPane.showConfirmDialog(null, "Current commandlist is not saved. Would you like to overwrite?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(yesNo == JOptionPane.YES_OPTION)
			{
				return true;
			}else return false;
		}
		else if(saved == false && type == 2)
		{
			int yesNo = JOptionPane.showConfirmDialog(null, "Current image is not saved. Would you like to overwrite?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(yesNo == JOptionPane.YES_OPTION) 
			{
				return true;
			}else return false;
		}else return true;
	}
	
	public void saveImg(BufferedImage buffImg, String FileName) throws IOException
	{ 
	    File outputFile = new File(FileName + ".png");
	    ImageIO.write(buffImg, "png", outputFile);
	}
	
	public BufferedImage loadImg(String FileName) throws IOException
	{
		File inputFile = new File(FileName + ".png");
		BufferedImage buffImg = ImageIO.read(inputFile);
		return buffImg;
	}
	
	public void saveString(String inputTxt, String FileName) throws IOException 
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter(FileName + ".txt"));
		bw.write(inputTxt);
		bw.close();
	}
	
	public ArrayList<String> loadString(String FileName) throws IOException
	{

		ArrayList<String> outLineArray = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(FileName + ".txt"));
		
		String line = br.readLine();
		while (line != null) 
		{
			outLineArray.add(line);
			line = br.readLine();
		}
		br.close();
		
		return outLineArray;	
	}
}
