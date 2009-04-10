package visualizer;
import javax.media.opengl.*;
import lathe.*;
import simplerjogl.*;

public class VisualTool extends Model
{
	public Tool tool;
	public VisualTool (GL gl)
	{
		super (gl);
		
		tool = new Tool();
	}
	public void draw ()
	{
		red.use();
		gl.glBegin (GL.GL_POLYGON);
		{
			for (Vertex v : tool)
			{
				gl.glVertex3dv (v.getXYZ(), 0);
			}
		}
		gl.glEnd();
	}
}
