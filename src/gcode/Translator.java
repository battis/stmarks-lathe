package gcode;
import java.util.ArrayList;

import arduino.Arduino;

public class Translator extends Arduino
{
	private ArrayList<Block> program;

	public Translator ()
	{
		Parser p = null;
		try {
			p = new Parser("H:/Eclipse_Workspace/Lathe SVN/Sample G-Codes/TEST-ROUGH.NC");
			System.out.println(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		program = p.getProgram();
	}
	
	public void translate ()
	{
		for (Block b : program)
		{
			if(b.=="G1")
			{
				
			}
		/*
		 * if(G-code == 01)
		 * {
		 * for(int i=0;i<length of the object; i++)
		 * (y(xcurrent)-y(xold))==y;
		 * x+=1
		 *  turn xknob 187*x 
		 *  turn yknob 187*y 
		 *  }
		 *  }
		 */
		}
	}
}
