package arduino;

import java.util.*;

public class ArduinoMessage
{
	private int msg;
	private ArrayList<Integer> params;
	
	public ArduinoMessage (int msg)
	{
		this.msg = msg;
		params = new ArrayList<Integer>();
	}
	
	public void addParam (int n)
	{
		params.add (n);
	}
}
