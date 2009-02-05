
package visualizer;

import java.util.*;

import javax.media.opengl.*;

import lathe.*;

import simplerjogl.*;

public class VisualWorkPiece extends Model
{
	public WorkPiece work;
	protected Material material;
	protected double slices;

	public VisualWorkPiece (GL gl, double radius, double length, int slices, Material material)
	{
		super (gl);

		work = new WorkPiece (radius, length);
		this.slices = slices;
		this.material = material;
	}

	public void draw ()
	{
		gl.glPushMatrix ();
		{
			material.use ();
		}
		gl.glPopMatrix();
	}
}