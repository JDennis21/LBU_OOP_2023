public class Date 
{
	private int day, month, year;
	
	public Date()
	{
		day = 1;
		month = 1;
		year = 1;
	}
	
	public void setDay(int dayIn)
	{
		if(dayIn >= 1 && dayIn <= 30)
			day = dayIn;
	}
	
	public void setMonth(int monthIn)
	{
		if(monthIn >= 1 && monthIn <= 12)
			month = monthIn;
	}
	
	public void setYear(int yearIn)
	{
		if(yearIn >= 1 && yearIn <= 9999)
			year = yearIn;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getYear()
	{
		return year;
	}
}