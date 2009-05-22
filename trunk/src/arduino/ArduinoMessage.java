
package arduino;

/**
 * This is quickly and badly written. It needs accessor and modifier
 * methods, as well as some translation so that the user doesn't have to
 * know what the specific numbers in msg really mean
 * 
 * @author Seth Battis
 * 
 */
import java.util.*;

public class ArduinoMessage
{
	public int msg;
	public ArrayList<Integer> params;

	public ArduinoMessage (int msg)
	{
		this.msg = msg;
		params = new ArrayList<Integer> ();
	}

	public void addParam (int n)
	{
		params.add (n);
	}
	
	public String toString()
	{
		return new String ("msg = " + Integer.toString (msg) + "params = " + params);
	}
}
