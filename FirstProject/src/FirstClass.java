import java.util.Scanner;

public class FirstClass
{

	public static void main(String[] args)
	{
		FirstClass Obj = new FirstClass();
		Obj.go();
	}
	
	public void go()
	{
		try (Scanner scan = new Scanner(System.in))
		{
			System.out.print("Enter your age ");
			int age = scan.nextInt();
			
			if (age > 20)
			{
				System.out.println("Your are " + age + " years old");

			}
			else 
			{
				System.out.println("Your are " + age + " years young");

			}
		}
	}
}