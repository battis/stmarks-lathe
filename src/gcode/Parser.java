package gcode;
import java.io.*;
import java.util.*;

import lathe.WorkPiece;

public class Parser
{
	private char file[];
	private Block bBuffer;
	private Segment sBuffer;
	private String nBuffer;
	private ArrayList<Block> program;

	public Parser (String fileName) throws IOException
	{
		Scanner fScanner = new Scanner (new BufferedReader (new FileReader (fileName)));
		program = new ArrayList<Block>();
		String fString = new String();
		while (fScanner.hasNextLine ())
		{
			fString = fString.concat (fScanner.nextLine ().toUpperCase ());
		}
		file = fString.toCharArray ();
		nBuffer = new String();

		for (char c : file)
		{
			if ((c == '%') || (c == ' '))
			{
				// ignore this
			}
			else if (Character.isDigit (c) || (c == '.') || (c == '-') || (c == '+'))
			{
				nBuffer = nBuffer.concat (String.valueOf (c));
			}
			else
			{
				if ((c == 'G') || (c == 'M') /* || (bBuffer.isComplete())*/)
				{
					nextBlock();
				}
				else
				{
					nextSegment();
				}
				sBuffer.setLetter (c);
			}
		}
	}

	private void nextBlock ()
	{
		nextSegment();
		if (bBuffer != null)
		{
			program.add (bBuffer);
		}
		bBuffer = new Block();
	}

	private void nextSegment()
	{
		if (sBuffer != null)
		{
			if (nBuffer.length () > 0)
			{
				sBuffer.setNumber (Double.valueOf (nBuffer));
				nBuffer = new String();
			}
			bBuffer.add (sBuffer);
			if (bBuffer.isComplete())
			{
				program.add (bBuffer);
				bBuffer = new Block();
			}
		}
		sBuffer = new Segment();
	}

	public String toString()
	{
		String s = new String();
		for (Block b : program)
		{
			s = s.concat (b.toString());
		}
		return s;
	}

	public WorkPiece getVertexShape()
	{
		WorkPiece w = new WorkPiece();
		for (Block b : program)
		{
			// figure out what command this block is
			// add those vertices to the workpiece
			// 		w.add (new Vertex (x, y));
		}
		return w;
	}
}