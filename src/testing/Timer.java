
package testing;

import java.util.*;

/**
 * Seriously, is there no java utility class to do this?
 * 
 * @author Seth Battis
 */
public class Timer
{
	private Date start, end;
	private long duration;

	/**
	 * Create a new timer, set to start at this moment
	 * @param duration of the timer in milliseconds
	 * @see #reset()
	 */
	public Timer (long duration)
	{
		this.duration = duration;
		reset ();
	}

	/**
	 * Reset (restart) the timer, setting the start at this moment.
	 */
	public void reset ()
	{
		start = new Date ();
		end = new Date (start.getTime () + duration);
	}

	/**
	 * Set a new duration for the timer (without resetting the timer itself)
	 * @param newDuration in milliseconds
	 * @return the previous duration in milliseconds
	 */
	public long set (long newDuration)
	{
		long oldDuration = duration;
		duration = newDuration;
		return oldDuration;
	}

	/**
	 * @return true if the end time for the timer is in the past, false otherwise
	 */
	public boolean isExpired ()
	{
		Date now = new Date ();
		return now.after (end);
	}

	/**
	 * @return number of milliseconds since the start time
	 */
	public long elapsed ()
	{
		Date now = new Date ();
		return now.getTime () - start.getTime ();
	}

	/**
	 * @return true if the current timer has not yet expired, false otherwise
	 */
	public boolean isRunning ()
	{
		Date now = new Date ();
		return now.before (end);
	}
}
