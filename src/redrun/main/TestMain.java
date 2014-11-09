package redrun.main;


import redrun.graphics.camera.Camera;
import redrun.model.gameobject.player.Runner;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class TestMain
{


  public static void main(String[] args)
  {
    TestMain tm = new TestMain();
    tm.initDisplay();
    Camera cam = new Camera(100, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0, 0, 0);
    tm.mainloop();
    tm.exit();
  }
  
  public void mainloop()
  {
    Runner player = new  Runner(3f,3f,3f);
    
    while(!Display.isCloseRequested())
    {
      
      Display.update();
    }
  }
  
  public void initDisplay()
  {
    try
    {
      Display.setDisplayMode(new DisplayMode(800, 600));
      Display.create();
    }
    catch (LWJGLException ex)
    {
      ex.printStackTrace();
    }
  }
  
  public void exit()
  {
    Display.destroy();
  }

}
