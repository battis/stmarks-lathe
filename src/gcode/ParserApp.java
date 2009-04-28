package gcode;
import java.io.*;

import simplerjogl.Frame;


public class ParserApp
{
	public static void main (String[] args) throws IOException
	{
		Parser p = new Parser ("Sample G-Codes/gcode.DXF");
		System.out.println (p);
		//ParserRenderer renderer = new ParserRenderer (p);
		//Frame frame = Frame.createFrame ("Sample SimplerJOGL App", true);
		//frame.addGLEventListener (renderer);
		//frame.start();
	}

}
