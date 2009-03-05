package visualizer;

import java.awt.event.KeyEvent;

import javax.media.opengl.GL;

import simplerjogl.*;

public class LatheLayout extends Model
{
	public LatheLayout (GL gl)
	{
		super (gl);
		l=new Light (gl);
		l.enable ();
		red = new Material (gl);
		red.setDiffuse(1,0,0,1);
		blue = new Material (gl);
		blue.setDiffuse(0,0,1,1);
		green = new Material (gl);
		green.setDiffuse(0,1,0,1);
		white = new Material (gl);
		white.setDiffuse(1,1,1,1);
	}

	private int t=0;
	private Light l;
	private Material red;
	private Material blue;
	private Material green;
	private Material white;


	public void display()
	{
		//glu.gluLookAt(0, 0, 14,    // eye location
			//0, 0, 0,    // focal point
			//0, 1, 0);    // up vector

		gl.glPushMatrix ();
		red.use ();
		gl.glTranslatef(-1.25f, 1, .5f);
		glut.glutSolidCylinder(1, 2, 100, 100);
		gl.glRotatef(t, 0, 0, 1);
		glut.glutWireCylinder(.1f, 2.5f, 10, 10);
		gl.glPopMatrix();

		gl.glPushMatrix ();
		green.use ();
		gl.glTranslatef(1, .25f, .25f);
		gl.glScalef(1.5f, .25f, 5);
		glut.glutSolidCube(1);
		gl.glPopMatrix();


		gl.glPushMatrix ();


		gl.glPushMatrix ();
		blue.use ();
		gl.glTranslatef(.9f, 1.1f, 2f);
		gl.glRotatef(t, 0, 0, 1);
		glut.glutSolidCylinder(.75f, .5f, 100, 100);
		glut.glutWireCylinder(.1f, 1, 10, 10);
		gl.glPopMatrix();


		gl.glPushMatrix ();
		blue.use ();
		gl.glTranslatef(.9f, 1.1f, -2f);
		gl.glRotatef(t, 0, 0, 1);
		glut.glutSolidCylinder(.75f, .5f, 100, 100);
		gl.glPopMatrix();

		gl.glPopMatrix();



		gl.glPushMatrix ();
		white.use ();
		gl.glScalef(5, .25f, 6);
		glut.glutSolidCube(1);
		gl.glPopMatrix();


		/*gl.glPushMatrix ();
		red.use ();

		gl.glTranslatef(1.5f, .4f, 0);
		gl.glPushMatrix ();

		gl.glPushMatrix ();
		gl.glTranslatef(.15f, .8f, 0);
		gl.glScalef(.25f, .15f, .15f);
		glut.glutSolidCube(1);
		gl.glPopMatrix();

		gl.glTranslatef(.375f, .5f, 0);
		gl.glScalef(.25f, 1, .3f);
		glut.glutSolidCube(1);
		gl.glPopMatrix();*/


		gl.glScalef(1, .25f, .5f);
		glut.glutSolidCube(1);
		gl.glPopMatrix();

		t+=1;


	}

}
