package redrun.model.toolkit;

import java.nio.FloatBuffer;

/**
 * This class contains a list of colors in a format usable by OpenGL.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-07
 */
public class Color
{
  /** The color red. */
  public static final FloatBuffer RED = Tools.asFloatBuffer(new float[] {1.0f, 0.0f, 0.0f, 1.0f});
  
  /** The color green. */
  public static final FloatBuffer GREEN = Tools.asFloatBuffer(new float[] {0.0f, 1.0f, 0.0f, 1.0f});
  
  /** The color blue. */
  public static final FloatBuffer BLUE = Tools.asFloatBuffer(new float[] {0.0f, 0.0f, 1.0f, 1.0f});

  /** The color white. */
  public static final FloatBuffer WHITE = Tools.asFloatBuffer(new float[] {1.0f, 1.0f, 1.0f, 1.0f});
  
  /** The color black. */
  public static final FloatBuffer BLACK = Tools.asFloatBuffer(new float[] {0.0f, 0.0f, 0.0f, 1.0f});
}