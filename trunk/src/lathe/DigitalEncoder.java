
package lathe;

import arduino.*;

/**
 * A class to model the behavior of a generic digital encoder.
 * 
 * @author Seth Battis
 * @version 2008-11-08
 */
public abstract class DigitalEncoder
{
	protected Arduino usb;

	public DigitalEncoder (Arduino usb)
	{
		this.usb = usb;
	}

	/**
	 * @return true if the encoder is reading high
	 */
	public abstract boolean isHigh ();

	/**
	 * @return true if the encoder is reading low
	 */
	public abstract boolean isLow ();
}
