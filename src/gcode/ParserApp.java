package gcode;
import java.io.*;


public class ParserApp
{
	public static void main (String[] args) throws IOException
	{
		Parser p = new Parser ("Sample G-Codes/gcode.DXF");
		System.out.println (p);
	}

}
