import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class store
{
	GraphicsSystem graphsys = new GraphicsSystem();
	
	public void saveImg()
	{
		try {
		    BufferedImage bufImg = graphsys.getBufferedImage();  
		    File outputFile = new File("saved.png");
		    ImageIO.write(bufImg, "png", outputFile);
		} catch (IOException e) {
		    graphsys.displayMessage("error occured");
		}
	}
	
	public void loadImg() 
	{
		File inputFile = new File("saved.png");
		
		try
		{
			BufferedImage bufImg = ImageIO.read(inputFile);
			graphsys.setBufferedImage(bufImg);
		} 
		
		catch (IOException e)
		{
			graphsys.displayMessage("error occured");
		}
		
	}
}
