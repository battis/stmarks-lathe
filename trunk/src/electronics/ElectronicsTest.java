package electronics;

import arduino.*;

@SuppressWarnings("serial")
public class ElectronicsTest extends arduino.Arduino
{	
	public void setup()
	{
		super.setup();
		System.out.println ("Starting Electronics Testing sequence");
	}
	
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
		
		/* disabled until wiring is connected
		System.out.println ("Testing RCCW");
		port.write('B');
		
		t.reset();
		while (t.isRunning()) {}*/

	}
}
