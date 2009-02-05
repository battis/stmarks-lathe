
package lathe;

import arduino.*;

public class MotorController
{
	private Arduino usb;

	public MotorController (Arduino usb)
	{
		this.usb = usb;
	}

	/**
	 * FIXME what are the units for power?
	 * 
	 * @param power
	 *            the desired power level
	 * @return true if successfully set, false otherwise
	 */
	public boolean setPower (double power)
	{
		/* TODO method stub */
		return false;
	}

	/**
	 * FIXME what are the units for power?
	 * 
	 * @return the current power level
	 */
	public double getPower ()
	{
		/* TODO method stub */
		return 0;
	}
}
