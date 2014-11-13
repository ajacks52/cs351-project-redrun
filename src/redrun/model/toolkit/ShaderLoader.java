package redrun.model.toolkit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Utility class to load shaders to make new shader make a new object of this
 * class
 * 
 * 
 * @author Adam Mitchell
 *
 *
 */
public class ShaderLoader
{
  private ArrayList<Integer> shaders = new ArrayList<Integer>();
  private int shaderProgram;

  public ShaderLoader()
  {
    this.shaderProgram = glCreateProgram();
  }

  public int loadShader(String name)
  {
    int shader = 0;

    if (name.endsWith(".fs"))
    {
      shader = glCreateShader(GL_FRAGMENT_SHADER);
    }
    else if (name.endsWith(".vs"))
    {
      shader = glCreateShader(GL_VERTEX_SHADER);
    }
    else
    {
      return -1;
    }

    StringBuilder shaderSource = new StringBuilder();
    BufferedReader reader = null;
    try
    {
      reader = new BufferedReader(new FileReader("res/shaders/" + name));
      String line;

      while ((line = reader.readLine()) != null)
      {
        shaderSource.append(line).append('\n');
      }
    }
    catch (IOException e)
    {
      // System.out.println(name);
      System.err.println("Shader wasn't loaded properly.");
      return -1;
    }
    finally
    {
      if (reader != null)
      {
        try
        {
          reader.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }

    glShaderSource(shader, shaderSource);
    glCompileShader(shader);
    if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE)
    {
      System.err.println("Shader wasn't compiled correctly.");
      return -1;
    }

    glAttachShader(this.shaderProgram, shader);
    glLinkProgram(this.shaderProgram);
    glValidateProgram(this.shaderProgram);

    return shader;
  }

  /**
   * Gets the current shader "return this.shaderProgram"
   * 
   * @return
   */
  public int getShaderProgram()
  {
    return this.shaderProgram;
  }

  /**
   * Deletes the this shaderProgram
   */
  public void deleteShaderProgram()
  {
    glDeleteProgram(this.shaderProgram);
  }

  /**
   * Deletes a given shader
   * 
   * @param shader to be deleted int
   */
  public void deleteShader(int s)
  {
    glDeleteShader(s);
  }

  /**
   * Deletes all shaders, should called when programs exits
   */
  public void deleteShaders()
  {
    for (Integer s : shaders)
    {
      glDeleteShader(s);
    }
  }
}