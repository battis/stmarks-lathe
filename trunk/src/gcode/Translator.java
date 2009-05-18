package gcode;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import arduino.*;

public class Translator extends Arduino
{
	private ArrayList<Block> program;

	public Translator ()
	{
		/* do all the things that the superclass(es) do(es) in its constructor */
		super();

		/* create a new Parser object based on whatever the user chooses in the file chooser dialog, parse it and get ready to translate it. */
		Parser p = null;
		JFileChooser fc = new JFileChooser ();
		if (fc.showOpenDialog (this.getParent ()) == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile ();
			try
			{
				p = new Parser (file.getAbsolutePath ());	
			}
			/* the parser might choke trying to open the file, so we'll print an error message and quit */
			catch (Exception e)
			{
				e.printStackTrace ();
				System.exit (0);
			}
		}
		else /* if the user cancels the open dialog, the program quits */
		{
			System.exit (0);
		}

		/* if we get this far, we've successfully parsed something -- print it out! */
		System.out.println (p);

		/* store a reference to the parsed program in this object for future translation */
		program = p.getProgram();
	}

	public void translate ()
	{
		double x = 0, z = 0;
		double newx, newz;
		for (Block b : program)
		{
			if(b.getCommandSet() == 'G' && b.getCommand() == 1)
			{
				for (Segment s : b)
				{
					if (s.getLetter() == 'Z')
					{
						newz = s.getNumber();
						if (newz - z > 0)
						{
							for (double i = z; i < newz; i ++)
							{
								RCW (255,(int) ((newz - z) * 187));
							}
						}
						else if (newz - z < 0)
						{
							for (double i = z; i < newz; i ++)
							{
								RCCW (255,(int) ((newz - z) * 187));
							}
						}
						z = newz;
					}
					if (s.getLetter() == 'X')
					{
						newx = s.getNumber();
						if (newx - x > 0)
						{
							for (double i = x; i < newx; i ++)
							{
								RCW ((int)((newx - x) * 187) , 255);
							}
						}
						else if (newx - x < 0)
						{
							for (double i = x; i < newx; i ++)
							{
								RCCW ((int)((newx - x) * 187) , 255);
							}
						}
						x = newx;
					}
				}

			}
		}
	}
}
