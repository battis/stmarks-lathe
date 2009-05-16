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
		super();
		Parser p = null;
		JFileChooser fc = new JFileChooser ();
		if (fc.showOpenDialog (this.getParent ()) == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile ();
			try
			{
				p = new Parser (file.getAbsolutePath ());	
			}
			catch (Exception e)
			{
				e.printStackTrace ();
				System.exit (0);
			}
		}
		else
		{
			System.exit (0);
		}
		System.out.println (p);
		
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
