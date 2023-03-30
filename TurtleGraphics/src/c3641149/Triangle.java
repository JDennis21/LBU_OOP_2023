package c3641149;

import static java.lang.Math.PI;
import static java.lang.Math.acos;

public class Triangle
{
	public int[] triangleDegrees(int sideA, int sideB, int sideC)
	{
		float a = sideA;
		float b = sideB;
		float c = sideC;
		
		float a2 = sideA * sideA;
		float b2 = sideB * sideB;
		float c2 = sideC * sideC;

		int angleA = Math.round((float) (acos((b2 + c2 - a2) / (2 * b * c)) * 180 / PI));
		int angleB = Math.round((float) (acos((a2 + c2 - b2) / (2 * a * c)) * 180 / PI));
		int angleC = Math.round((float) (acos((a2 + b2 - c2) / (2 * a * b)) * 180 / PI));
		
		if(angleA + angleB + angleC != 180)
		{
			angleA = angleA + 1;
		}
		
		int[] angles = new int[] {angleA, angleB, angleC};
			
		return angles;
	}
}
