package gcode;

import lathe.*;
import simplerjogl.*;
import visualizer.*;

public class ParserRenderer extends Renderer
{
	private VisualWorkPiece target;
	private WorkPiece w;
	private Light light;
	
	public ParserRenderer (Parser p)
	{
		super();
		w = p.getVertexShape();
	}
	
	public void init()
	{
		Material.enableBlending (gl);
		Material translucentPlastic = new Material (gl);
		translucentPlastic.setDiffuse (1, 1, 1, 0.75);
		translucentPlastic.setSpecular (.9, .9, 1, 0.85);
		translucentPlastic.setShininess (35);
		
		target = new VisualWorkPiece(gl, w, 100, translucentPlastic);
		
		light = new Light (gl);
		light.setPosition (1, 1, 1, 0);
		light.setAmbient (.4f, .4f, .4f, 1);
		light.enable ();

		gl.glClearColor (0.5f, 0.5f, 0.5f, 1);
	}
	
	public void display()
	{
		double centerX = target.work.length() / 2.0;
		double scale = 1.0 / 10.0;
		glu.gluLookAt (centerX * scale , 0, centerX * scale * 4, 
			centerX * scale, 0, 0, 
			0, 1, 0);
		gl.glScaled (0.1, 0.1, 0.1);
		target.draw ();
	}
}
