package c3641149;

public class MainClass 
{

	public static void main(String[] args) 
	{
		MainClass myObject;
		myObject = new MainClass();
		myObject.go();
	
	
	}

	public void go()
	{
		System.out.println("hello go");
		Date appointment = new Date();
		Date birthday = new Date();
		appointment.setDay(21);;
		appointment.setMonth(2);;
		appointment.setYear(2023);;
		birthday.setDay(22);
		birthday.setMonth(9);
		birthday.setYear(2023);
		int month;
		month = appointment.getMonth();
		System.out.println(month);
		
	}
	
}
