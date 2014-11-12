package redrun.model.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * This class contains a variety of tools that assist other classes in constructing
 * an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class Tools
{
  /**
   * Loads a texture from a PNG file for OpenGL.
   * 
   * @param filename the name of the file located in the res (resources) directory
   * @return the loaded tecture
   */
  public static Texture loadPNGTexture(String filename)
  {
    try
    {
      return TextureLoader.getTexture("png", new FileInputStream(new File("res/textures/" + filename + ".png")));
    }
    catch (FileNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return null;
  }
  
  /**
   * Loads a texture from a JPG for OpenGL.
   * 
   * @param filename the name of the file located in the res (resources) directory
   * @return the loaded tecture
   */
  public static Texture loadJPGTexture(String filename)
  {
    try
    {
      return TextureLoader.getTexture("jpg", new FileInputStream(new File("res/textures/" + filename + ".jpg")));
    }
    catch (FileNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return null;
  }
}
