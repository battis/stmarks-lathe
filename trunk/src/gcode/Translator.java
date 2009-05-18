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
		for (Block b : program)
		{
			if(b.getCommandSet() == 'G' && b.getCommand() == 1)
			{
				
			}
		/*
		 *
		 * {
		 * for(int i=0;i<length of the object; i++)
		 * (y(xcurrent)-y(xold))==y;
		 * x+=1
		 *  turn xknob 187*x 
		 *  turn yknob 187*y 
		 *  }
		 *  }
		 */
		}
	}
}
