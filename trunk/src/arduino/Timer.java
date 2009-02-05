
package arduino;

import java.util.*;

public class Timer
{
	private Date start, end;
	private long duration;

	public Timer (long duration)
	{
		this.duration = duration;
		reset ();
	}

	public void reset ()
	{
		start = new Date ();
		end = new Date (start.getTime () + duration);
	}

	public long set (long newDuration)
	{
		long oldDuration = duration;
		duration = newDuration;
		return oldDuration;
	}

	public boolean isExpired ()
	{
		Date now = new Date ();
		return now.after (end);
	}

	public long elapsed ()
	{
		Date now = new Date ();
		return now.getTime () - start.getTime ();
	}

	public boolean isRunning ()
	{
		Date now = new Date ();
		return now.before (end);
	}
}
