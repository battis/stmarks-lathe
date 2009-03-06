package gcode;

import java.io.*;

import simplerjogl.*;
import simplerjogl.shell.*;
import visualizer.*;

public class gcodeParserApp
{
	public static void main (String[] args) throws IOException
	{
		gcodeParser gcp = new gcodeParser ("Sample G-Codes/gcode.DXF");
		Renderer renderer = new ParserRenderer (gcp);
		ShellFrame frame = ShellFrame.createFrame ("G-Code Parser Visualization", false);
		frame.addGLEventListener (renderer);
		frame.addKeyListener (renderer);
		frame.addShellListener (renderer);
		frame.start ();
	}

}
