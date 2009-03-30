package gcode;


import java.util.*;

@SuppressWarnings("serial")
public class Block extends ArrayList<Segment>
{
	private boolean delete;
	private Integer lineNumber;
	private Character commandSet;
	private Double command;

	public Block()
	{
		super();
		delete = false;
		lineNumber = null;
		commandSet = null;
		command = null;
	}

	public boolean add (Segment s)
	{
		switch (s.getLetter ())
		{
			case 'N':
				lineNumber = (int) s.getNumber ();
				return true;
			case 'G':
			case 'M':
				commandSet = s.getLetter ();
				command = s.getNumber ();
				return true;
			default:
				return super.add (s);
		}
	}

	public void setDelete (boolean b)
	{
		delete = b;
	}

	public boolean getDelete()
	{
		return delete;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	public char getCommandSet ()
	{
		return commandSet;
	}

	public double getCommand ()
	{
		return command;
	}

	public String toString()
	{
		String s = new String();
		if (delete)
		{
			s = s.concat ("/");
		}
		else
		{
			s = s.concat (" ");
		}

		if (lineNumber != null)
		{
			s = s.concat ("N" + lineNumber + " ");
		}

		if (commandSet != null)
		{
			s = s.concat (String.valueOf (commandSet));
		}

		if (command != null)
		{
			if ((double) (command - Math.floor (command)) == 0.0)
			{
				s = s.concat (String.valueOf (Math.round (command)));
			}
			else
			{
				s = s.concat (String.valueOf (command));
			}
		}

		s = s.concat ("\n");
		if (this.size () > 0)
		{
			for (Segment seg : this)
			{
				s = s.concat ("\t" + seg + "\n");
			}
		}

		return s;
	}
}
