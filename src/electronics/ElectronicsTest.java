package electronics;

import arduino.*;
import processing.core.*;
import processing.serial.*;

@SuppressWarnings ("serial")
public class ElectronicsTest extends arduino.Arduino
{	
	public void draw ()
	{
		Timer t = new Timer (3000);
		
		// sending instructions about how to control the lathe
		// collecting information about the encoders
		// communicating with the rest of the lathe software on the computer
		
		System.out.println ("Testing RCW");
		port.write('A');
	
		t.reset();
		while (t.isRunning()) {}
		
		/*System.out.println ("Testing RCCW");
		port.write('B');
		
		t.reset();
		while (t.isRunning()) {}*/

	}
}
