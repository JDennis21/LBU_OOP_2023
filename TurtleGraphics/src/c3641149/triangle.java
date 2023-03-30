package c3641149;

import static java.lang.Math.PI;
import static java.lang.Math.acos;

public class triangle
{
	public int[] triangleDegrees(int sideA, int sideB, int sideC)
	{
		float a = sideA;
		float b = sideB;
		float c = sideC;
		
		float a2 = sideA * sideA;
		float b2 = sideB * sideB;
		float c2 = sideC * sideC;

		float angleA = (float) (acos((b2 + c2 - a2) / (2 * b * c)) * 180 / PI);
		float angleB = (float) (acos((a2 + c2 - b2) / (2 * a * c)) * 180 / PI);
		float angleC = (float) (acos((a2 + b2 - c2) / (2 * a * b)) * 180 / PI);

		if(angleA + angleB + angleC != 180 && angleA + angleB + angleC < 180);
		{
			float avgMissing = (180 - (angleA + angleB + angleC)) / 3;
			angleA = angleA + avgMissing;
			angleB = angleB + avgMissing;
			angleC = angleC + avgMissing;
		}
		
		int[] angles = new int[] {(int) angleA, (int) angleB, (int) angleC};
		
		return angles;
	}
}
