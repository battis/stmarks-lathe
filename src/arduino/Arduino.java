// developed primarily by P. Lim

package arduino;

import java.util.ArrayList;

import processing.core.*;
import processing.serial.*;

@SuppressWarnings ("serial")
public class Arduino extends PApplet
{
	public static final String ARDUINO_HANDSHAKE = "@";
	protected Serial port;

	public void setup ()
	{
		/* get a list of all active ports */
		String ports[] = Serial.list ();
		Timer t = new Timer (1500);

		/* try every port, looking for the handshake from the Arduino board */
		boolean handshake = false;
		for (int i = 0; i < ports.length && !handshake; i++ )
		{
			boolean skip = false;
			System.out.println ("Testing " + ports[i] + "...");

			/* open connection to port */
			try
			{
				port = new Serial (this, ports[i], 9600);
			}
			catch (Exception e)
			{
				skip = true;
			}
			if (!skip)
			{
				/* wait a couple of seconds for a response */
				t.reset ();
				while ( (port.available () < 1) && (t.isRunning ()))
				{}

				/* check any response received against the handshake */
				if (port.available () > 0)
				{
					if (port.readString ().equals (ARDUINO_HANDSHAKE))
					{
						println ("Arduino USB connected to port " + ports[i]);
						handshake = true;
					}
				}
			}
		}
		if (!handshake)
		{
			/* it's possible that we should just quit the program if the Arduino can't be found... */
			System.out.println ("Arduino USB was not found.");
			// System.exit (0);
		}
	}

	public void draw ()
	{
		Timer t = new Timer (3000);

		// sending instructions about how to control the lathe
		// collecting information about the encoders
		// communicating with the rest of the lathe software on the
		// computer

		/*
		 * commented out testing code now that we're working on the actual
		 * communication of G-code to the Arduino -- SDB 5/15
		 */
		/*
		 * System.out.println ("Testing XCW"); port.write(1);
		 * 
		 * t.reset(); while (t.isRunning()) {}
		 * 
		 * System.out.println ("Testing XCCW"); port.write(2);
		 * 
		 * t.reset(); while (t.isRunning()) {}
		 * 
		 * System.out.println ("Testing RCW"); port.write(4);
		 * 
		 * t.reset(); while (t.isRunning()) {}
		 * 
		 * System.out.println ("Testing XCW + RCW"); port.write(5);
		 * 
		 * t.reset (); while (t.isRunning()) {}
		 * 
		 * System.out.println ("Testing XCCW + RCW"); port.write(6);
		 * 
		 * t.reset (); while (t.isRunning()) {}
		 * 
		 * System.out.println ("Testing RCCW"); port.write(8);
		 * 
		 * t.reset(); while (t.isRunning()) {}
		 * 
		 * System.out.println ("Testing XCW + RCCW"); port.write (9);
		 * 
		 * t.reset (); while (t.isRunning()) {}
		 * 
		 * System.out.println ("Testing XCCW + RCCW"); port.write (10);
		 * 
		 * t.reset (); while (t.isRunning()) {}
		 * 
		 * System.out.println ("Testing EM"); port.write (128);
		 * 
		 * t.reset (); while (t.isRunning()) {}
		 */

	}

	/*
	 * These methods look good... but we may run into a timing issue: what
	 * happens if the G-code interpreter calls a bunch of these methods in
	 * quick succession while the first one is still operating? What will
	 * happen to the lathe? We probably want to collect the feedback from
	 * the Arduino and send that back to whoever called these methods -- if
	 * we wait for that feedback, we shouldn't end up with overlapping
	 * commands.
	 */
	public void waityourturn()
	{
		ArrayList <Integer> params = new ArrayList<Integer>(); 
		while (port.available() == 0) {}
		int msg = port.read();
		while (port.available () > 0)
			{ 
			params.add (port.read ()); 
			} 
	}
	public void XCW (int turns, int power)
	{
		port.write (1);
		port.write (turns);
		port.write (power);
		waityourturn();// so methods don't overlap. One method waits for the previous command to finish
	}

	public void XCCW (int turns, int power)
	{
		port.write (2);
		port.write (turns);
		port.write (power);
		waityourturn();
	}

	public void RCW (int turns, int power)
	{
		port.write (4);
		port.write (turns);
		port.write (power);
		waityourturn();
	}

	public void XCWandRCW (int turns, int power)
	{
		port.write (5);
		port.write (turns);
		port.write (power);
		waityourturn();
	}

	public void XCCWandRCW (int turns, int power)
	{
		port.write (6);
		port.write (turns);
		port.write (power);
		waityourturn();
	}

	public void RCCW (int turns, int power)
	{
		port.write (8);
		port.write (turns);
		port.write (power);
		waityourturn();
	}

	public void XCWandRCCW (int turns, int power)
	{
		port.write (49);
		port.write (turns);
		port.write (power);
		waityourturn();
	}

	public void XCCWandRCCW (int turns, int power)
	{
		port.write (10);
		port.write (turns);
		port.write (power);
		waityourturn();
	}
}