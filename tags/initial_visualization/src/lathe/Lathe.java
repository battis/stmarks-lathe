
package lathe;

import java.awt.*;

import arduino.*;

/**
 * Lathe
 * 
 * An object to represent the physical lathe device and its operations, as
 * handled through the Arduino Diecimila USB board and Processing
 * interface. High level operations of the lathe ("smart" maneuvers like
 * not cutting through huge swaths of material quickly) are handled by the
 * LatheOperator class. This class is low-level mechanical operation of the
 * lathe itself.
 * 
 * Motor reduction is 187:1, saddle controls are 1 rotation per 1mm on
 * transverse drive and 1 rotation per 15mm on the parallel drive, so if we
 * can get 1/4 turn resolution on the encoders, we should be able to work
 * in 1/1000 of an inch.
 * 
 * @author Seth Battis
 * @version 2008-11-07
 */
public class Lathe
{
	private Arduino usb;
	private WorkPiece work;
	private Tool tool;

	public Lathe ()
	{
		usb = new Arduino ();
	}

	/**
	 * Finds the physical origin of the material on the lathe. This must be
	 * done first, as all subsequent operations require a valid origin from
	 * which to calculate their own movements.
	 */
	public void calibrate ()
	{
	/*
	 * TODO Use a calibration dumbbell, with five points of contact:
	 * 
	 * 1. touch cutting edge of tool to faceplate A (Y)
	 * 
	 * 2. touch cutting edge of tool to faceplate B (Y distance)
	 * 
	 * 3. touch non-cutting edge of tool to back of faceplate B (tool Y)
	 * 
	 * 4. touch cutting edge of tool to radius of faceplate B (X)
	 * 
	 * 5. touch cutting edge of tool to radius of spindle (X distance)
	 * 
	 * Mark a line on saddle beyond which tool holder may not be mounted,
	 * ask user in which track tool holder is mounted, calculate circle of
	 * "potential tool holder interference" to exclude from work.
	 * 
	 * Ask the user what the orientation of the tool is. can we tweak the
	 * calibration so that we can remount an existing work piece and find a
	 * reference surface on the work to reorient ourselves to the current
	 * shape?
	 */
	}

	/**
	 * Finds the surface of the material mounted in the lathe. This will
	 * assume that the material is a regular centered cylinder for future
	 * calculations.
	 * 
	 * @see #calibrate()
	 */
	public void findMaterialDimensions ()
	{
	/*
	 * TODO method stub touch tool to radius of workpiece
	 */
	}

	/**
	 * Returns the speed of the lathe's rotation.
	 * 
	 * FIXME what are the units for the speed of rotation?
	 * 
	 * @return speed in ____ units (always non-negative)
	 */
	public double getRotationSpeed ()
	{
		/* TODO method stub */
		return 0;
	}

	/**
	 * Resets the index counter for the lathe rotation.
	 * 
	 * @see #getIndexCount()
	 */
	public void resetIndexCount ()
	{
	/* TODO method stub */
	}

	/**
	 * Returns the current number of stops since the last zeroing of the
	 * index counter for the lathe rotation. There are 60 indexed stops per
	 * one rotation of the lathe. If the index counter overflows, it will
	 * be reset modulo 60, such that the count % 60 will always be relative
	 * to the last zeroing of the index counter.
	 * 
	 * @see #resetIndexCount()
	 */
	public int getIndexCount ()
	{
		/* TODO method stub */
		return 0;
	}

	/**
	 * Returns the position of the tool head relative to the origin. FIXME
	 * what are the units for this position?
	 * 
	 * @return a point in 2-dimensional space in ____ units
	 * @see #calibrate()
	 */
	public Point getToolHeadPosition ()
	{
		/* TODO method stub */
		return new Point ();
	}

	/**
	 * Returns the speed of the the tool head's movement lateral to the
	 * axis of rotation (perpendicular). Positive speeds indicate movement
	 * towards the material; negative speeds indicate movement away from
	 * the material.
	 * 
	 * FIXME what are the units for this speed?
	 * 
	 * @return speed in _____ units
	 * @see #getParallelSpeed()
	 */
	public double getTransverseSpeed ()
	{
		/* TODO method stub */
		return 0;
	}

	/**
	 * Returns the speed of the tool head's movement parallel to the axis
	 * of rotation. Positive speeds indicate movement away from the origin;
	 * negative speeds indicate movement towards the origin.
	 * 
	 * FIXME what are the units for this speed?
	 * 
	 * @return speed in ____ units
	 * @see #calibrate()
	 * @see #getTransverseSpeed()
	 */
	public double getParallelSpeed ()
	{
		/* TODO method stub */
		return 0;
	}

	/**
	 * Returns the calculated (not necessarily real) distance between the
	 * tool head and the material. This distance is calculated based on
	 * both the origin of the lathe and the assumption that the original
	 * material was a uniform cylinder.
	 * 
	 * FIXME what are the units for this distance?
	 * 
	 * @return distance in ____ units
	 * @see #calibrate()
	 */
	public double getDistanceToMaterial ()
	{
		/* TODO method stub */
		return 0;
	}

	/**
	 * Move the tool head laterally in relation to the material.
	 * 
	 * This method does NOT attempt any sort of "smart" behavior, simply
	 * attempting to move the tool head to the desired location. Small
	 * increments are recommended!
	 * 
	 * Positive distances indicate movement towards the material; negative
	 * distances indicate movement away from the material. Returns 0 if the
	 * tool head is successfully moved to the desired location, otherwise
	 * the distance overshot (positive values) or undershot (negative
	 * values) is returned.
	 * 
	 * FIXME what are the units for this distance?
	 * 
	 * @param distance
	 *            to move in ____ units
	 * @param speed
	 *            at which to move the tool head in ____ units
	 * @return difference between desired and actual movement in ____ units
	 * @see #moveParallel(double,double)
	 */
	public double moveTransverse (double distance, double speed)
	{
		/* TODO method stub */
		return 0;
	}

	/**
	 * Move the tool head parallel to the material.
	 * 
	 * This method does NOT attempt any sort of "smart" behavior, simply
	 * attempting to move the tool head to the desired location. Small
	 * increments are recommended!
	 * 
	 * Positive distances indicate movement away from the origin; negative
	 * distances indicate movement toward the origin. Returns 0 if the tool
	 * head is successfully moved to the desired location, otherwise the
	 * distance overshot (positive values) or undershot (negative values)
	 * is returned.
	 * 
	 * FIXME what are the units for this distance?
	 * 
	 * @param distance
	 *            to move in ____ units
	 * @param speed
	 *            at which to move in ____ units
	 * @return difference between desired and actual movement in ____ units
	 * @see #calibrate()
	 * @see #moveTransverse(double,double)
	 */
	public double moveParallel (double distance, double speed)
	{
		/* TODO method stub */
		return 0;
	}
}