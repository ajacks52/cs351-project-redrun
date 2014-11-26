package redrun.model.toolkit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.Sys;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

/**
 * This class contains a variety of tools that assist other classes in
 * constructing an OpenGL scene.
 * 
 * @author Troy Squillaci, J. Jake Nichol
 * @version 1.0
 * @since 2014-11-03
 */
public class Tools
{
  /** Time at last frame */
  public static long lastFrame;

  /** Frames per second */
  public static int fps;

  /** Last FPS time */
  public static long lastFPS;

  /**
   * Loads a texture from a file for OpenGL.
   * 
   * @param filename the name of the file located in the res (resources)
   *          directory
   * @return the loaded texture
   */
  public static Texture loadTexture(String filename, String filetype)
  {
    try
    {
      return TextureLoader.getTexture(filetype,
          ResourceLoader.getResourceAsStream("res/textures/" + filename + "." + filetype), GL_LINEAR);
    }
    catch (IOException ex)
    {
      Logger.getLogger(Tools.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

  /**
   * Get the accurate system time for LWJGL
   * 
   * @return The system time in milliseconds
   */
  public static long getTime()
  {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }
}
