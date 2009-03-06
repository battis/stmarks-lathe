package electronics;

import java.awt.*;

import arduino.*;

public class ElectronicsTest extends arduino.Arduino
{	
	private static final long serialVersionUID = 1L;
	private int testCount;
	private Timer timer = new Timer (3000);
	private Toolkit tool = Toolkit.getDefaultToolkit ();

	public void setup()
	{
		System.out.println ("Starting Electronics Testing sequence");
		super.setup();
		testCount = 0;
	}

	private void portWrite (char c)
	{
		port.write(c);
		tool.beep();
		timer.reset();
		while (timer.isRunning()) {}
	}
	
	public void draw ()
	{

		// sending instructions about how to control the lathe
		// collecting information about the encoders
		// communicating with the rest of the lathe software on the computer

		if (testCount < 2)
		{
			testCount++;
			
			System.out.println ("Testing RCW");
			portWrite('A');

			System.out.println ("Testing RCW");
			portWrite('A');

			System.out.println ("Testing RCCW");
			portWrite('B');

			System.out.println ("Testing Zero Power");
			portWrite ('C');
		}
		else if (testCount == 2)
		{
			System.out.println ("Ending Electronics Testing sequence");
			tool.beep();
			testCount++;
			System.exit (0);
		}
	}
}
