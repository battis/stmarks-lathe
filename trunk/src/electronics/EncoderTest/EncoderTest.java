package electronics.EncoderTest;

import arduino.*;

public class EncoderTest extends Arduino
{
	private static final long serialVersionUID = 1L;

	public void setup()
	{
		super.setup();		
		System.out.println ("Start Encoder Test");
	}
	
	public void draw()
	{
		while (port.available() > 0)
		{
			System.out.print (port.readString ());
		}
	}
}
