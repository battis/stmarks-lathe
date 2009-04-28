
package gcode;

import java.util.*;
import java.io.*;

import simplerjogl.*;

import lathe.*;

/*
 * TODO Apparently Gcode2000 flip-flops X and Y coordinates (and flips Y
 * negative-positive) when comparing the diagram to the generated Gcode. Is
 * this how we're using it? Or is it just a misconception on our part?
 */

public class gcodeParser
{
	private Scanner file;
	private WorkPiece target;
	private String token;

	public gcodeParser (String filename) throws IOException
	{
		file = new Scanner (new BufferedReader (new FileReader (filename)));
		target = new WorkPiece();

		while (file.hasNext ())
		{
			nextToken();
			if (token.equals ("G45"))
			{

				do {
					nextToken();
					double x, y;
					if (!isGCode())
					{
						x = Double.valueOf (token.substring (1));
						nextToken();
						if (!isGCode())
						{
							y = Double.valueOf (token.substring (1));
							target.add (new Vertex (-y, x)); 
						}
					}
				

				} while (!isGCode());
			}
		}
	}
//<<<<<<< .mine
	
	private double x, y;
/* take the y coordinate of gcode and make it x, take x of gcode and make it -y*/
//f
	public gcodeParser (double x,double y)
	{
		this.x=-y;
		this.y=x;
	}
//=======
	
	protected void nextToken()
	{
		token = file.next ();
	}

//>>>>>>> .r59
	protected boolean isGCode()
	{
		return (token.charAt (0) == 'G');
	}
	
	public WorkPiece getTarget()
	{
		return target;
	}
}
