
package visualizer;

import java.awt.event.KeyEvent;

import simplerjogl.*;

public class VisualizerRenderer extends Renderer
{
	private VisualWorkPiece visual;
	private VisualTool tool;
	private Material translucentPlastic;
	private Light light;

	public void init ()
	{
		Material.enableBlending (gl);
		translucentPlastic = new Material (gl);
		translucentPlastic.setDiffuse (1, 1, 1, 0.75);
		translucentPlastic.setSpecular (.9, .9, 1, 0.85);
		translucentPlastic.setShininess (35);
		
		visual = new VisualWorkPiece (gl, 5, 1, 100, translucentPlastic);
		
		tool = new VisualTool (gl);

		light = new Light (gl);
		light.setPosition (1, 1, 1, 0);
		light.setAmbient (.4f, .4f, .4f, 1);
		light.enable ();
		
		gl.glClearColor (0.5f, 0.5f, 0.5f, 1);

		enableAxes ();
		setAxisLength (visual.work.length() * 1.2);
	}

	public void display ()
	{
		glu.gluLookAt (visual.work.length () / 2, 0, visual.work.maxRadius() * 7, visual.work.length () / 2, 0, 0, 0, 1, 0);
		tool.draw();
		visual.draw ();
	
	}
	
	public void keyPressed(KeyEvent e) //Chris Becker
	{
		if(e.getKeyCode()== KeyEvent.VK_UP)
		{
			tool.tool.move(0, .01);
		}
		if(e.getKeyCode()== KeyEvent.VK_DOWN)
		{
			tool.tool.move(0, -.01);
		}
		if(e.getKeyCode()== KeyEvent.VK_RIGHT)
		{
			tool.tool.move(.01, 0);
		}
		if(e.getKeyCode()== KeyEvent.VK_LEFT)
		{
			tool.tool.move(-.01, 0);
		}
	}
}
