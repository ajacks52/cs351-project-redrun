package redrun.model.toolkit;

import static org.lwjgl.opengl.GL20.glDeleteShader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import redrun.model.graphics.Shader;

public class ShaderTools
{
  public static final String SHADERS_DIRECTORY = "/res/shaders/";

  public static String findFileOrThrow(String filename)
  {
    InputStream fileStream = ClassLoader.class.getResourceAsStream(SHADERS_DIRECTORY + filename);
    if (fileStream != null) { return SHADERS_DIRECTORY + filename; }

    throw new RuntimeException("Could not find the file " + filename);
  }

  public static int loadShader(int shaderType, String shaderFilename)
  {
    String filepath = findFileOrThrow(shaderFilename);
    String shaderCode = loadShaderFile(filepath);

    return Shader.compileShader(shaderType, shaderCode);
  }

  public static int createProgram(ArrayList<Integer> shaders)
  {
    try
    {
      int prog = Shader.linkProgram(shaders);
      return prog;
    }
    finally
    {
      for (Integer shader : shaders)
      {
        glDeleteShader(shader);
      }
    }
  }

  private static String loadShaderFile(String shaderFilepath)
  {
    StringBuilder text = new StringBuilder();

    try
    {
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          ClassLoader.class.getResourceAsStream(shaderFilepath)));

      String line;

      while ((line = reader.readLine()) != null)
      {
        text.append(line).append("\n");
      }

      reader.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    return text.toString();
  }
}
