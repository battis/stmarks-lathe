
package lathe;

public class LatheOperator
{
	private Lathe lathe;
	
	/*
	 * one possibility: gives the lathe the curve to work with.
	 * find the equation of a curve (perhaps little pieces of linear segments, y=mx+b)
	 * graph the curve on the visualizer.
	 * begin to cut out little pieces according the the graph.
	 *
	 *another possibility: find the volume of the obj. and the outlining curve.
	 *cuting segments pieces by pieces according to the volume and shape.
	 *(disc method in calculus)
	 * 
	 * 1. scan shape from largest to smallest radius, cutting large radii
	 * first, and finishing with the smallest, finishing with the faceplate
	 * turn
	 * 
	 * 2. mount calibration dumbbell, calibrate lathe; examine shape and
	 * generate interference warnings about cuts not possible with this
	 * tool configuration
	 * 
	 * 3. run dummy pass without workpiece loaded, allow for user abort in
	 * event of potential problems (e.g. interference with the dead center,
	 * etc.)
	 * 
	 * 4.mount workpiece, run findMaterialDimensions(), tell user to rotate
	 * by hand to ensure centeredness, remind user to tuck in tie and
	 * shirt, wear eye protection and not to keep their mouth open like
	 * barn door
	 * 
	 * 5. Go go go!
	 * 
	 * faceplate turn - set X, cut Y; can only cut safely on distal side of
	 * work; can only cut with decreasing radius (no scraping as the tool
	 * pulls back) cylinder turn - set Y, cut X; generally cut away from
	 * the chuck (in a distal direction); return - lift tool from work and
	 * pull back
	 * 
	 * A. rough pass - some increment too large to get material out of the
	 * way; determine which are cylinder faces and which are faceplates,
	 * approach cylinders in increments of
	 * 1/100"; faceplates approach at 5/1000" or so (less than cylinders)
	 * repetitive process -- don't cut to depth in a single pass, be aware
	 * of diameters on either side of the cut rasterize curves on this pass
	 * 
	 * B. polish pass - exactly to spec; extremely slow feed rate to get a
	 * fine, satin surface on it -- not incremental movements, but one
	 * smooth move think about interpolating between raster points to get
	 * smooth curves on this pass (can we express bezier curves as well as
	 * just linear interpolations?)
	 * 
	 * DO NOT run the tool chuck into the work! Need a red zone that
	 * describes the shape of the tool chuck and that can NEVER intersect
	 * the work!
	 */
}