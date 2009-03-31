package electronics;

import java.awt.*;

import arduino.*;

public class ElectronicsTest extends arduino.Arduino
{	
	private static final long serialVersionUID = 1L;
	private int testCount;
	private Timer timer = new Timer (5000);
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
			
			/*attemption only R on, then only X on, then both on, then both off
			 * then R on in other direction, same with X, then both, and finish with each off
			 */
			
			System.out.println ("Testing RadiusClockwise");
			portWrite('A');

			System.out.println ("Testing XClockwise");
			portWrite('D');

			System.out.println ("Testing SetBothRunClockwise");
			portWrite('G');
			
			System.out.println ("Testing RadiusCounterClockwise");
			portWrite('B');

			System.out.println ("Testing XCounterClockwise");
			portWrite('E');

			System.out.println ("Testing SetBothRunCounterClockwise");
			portWrite('H');
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
