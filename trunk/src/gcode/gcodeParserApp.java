package gcode;

import java.io.*;

public class gcodeParserApp
{
	public static void main (String[] args) throws IOException
	{
		gcodeParser gcp = new gcodeParser ("Sample G-Codes/gcode.DXF");
	}

}
