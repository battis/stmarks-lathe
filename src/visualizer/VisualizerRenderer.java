
package visualizer;

import simplerjogl.*;

public class VisualizerRenderer extends Renderer
{
	private VisualWorkPiece work;
	private Light light;

	public void init ()
	{
		work = new VisualWorkPiece (gl, 1, 5, 100, 100);

		light = new Light (gl);
		light.setPosition (1, 1, 1, 0);
		light.setAmbient (.4f, .4f, .4f, 1);
		light.enable ();
		
		gl.glClearColor (1, 1, 1, 1);

		enableAxes ();
		setAxisLength (work.getLength() * 1.2);
	}

	public void display ()
	{
		glu.gluLookAt (work.getLength () / 2, work.getMaxRadius(), 0, 0, 0, 0, 1, 0, 0);

		work.draw ();
	}
}
