
package visualizer;

import simplerjogl.*;
import simplerjogl.shell.*;

public class VisualizerApp
{
	public static void main (String[] args)
	{
		Renderer renderer = new VisualizerRenderer ();
		ShellFrame frame = ShellFrame.createFrame ("Lathe Visualizer/Simulator", false);
		frame.addGLEventListener (renderer);
		frame.addShellListener (renderer);
		frame.start ();
	}
}