package redrun.model.toolkit;

import org.lwjgl.Sys;

public class Timing
{
  private static long lastFrame;

  /**
   * Get the accurate system time for LWJGL.
   * 
   * @return The system time in milliseconds
   */
  public static long getTime()
  {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }
  
  /**
   * Calculate how many milliseconds have passed since last frame.
   * 
   * @return milliseconds passed since last frame
   */
  public static int getDelta()
  {
    long time = getTime();
    int delta = (int) (time - lastFrame);
    lastFrame = time;

    return delta;
  }
}
