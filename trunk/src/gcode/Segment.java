package gcode;


public class Segment
{
	private Character letter;
	private Double number;
	
	public Segment()
	{
		letter = null;
		number = null;
	}
	
	public void setLetter (char c)
	{
		letter = c;
	}
	
	public char getLetter ()
	{
		return letter;
	}
	
	public void setNumber (double n)
	{
		number = n;
	}
	
	public double getNumber ()
	{
		return number;
	}
	
	public String toString()
	{
		return new String (letter + " = " + number);
	}
}
