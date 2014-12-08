package redrun.network;

import org.lwjgl.input.Keyboard;

/**
 * Translate user input to string for transmission over the network
 * 
 * @author Troy Squillaci ( zivia @ unm.edu ), Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-12-05
 */
public class UserInput
{

  @Override
  /**
   * Translate input to string format
   */
  public String toString()
  {
    return "=== User Input === " + "W:" + Keyboard.isKeyDown(Keyboard.KEY_W) + " A:"
        + Keyboard.isKeyDown(Keyboard.KEY_A) + " S:" + Keyboard.isKeyDown(Keyboard.KEY_S) + " D:"
        + Keyboard.isKeyDown(Keyboard.KEY_D) + " SPACE:" + Keyboard.isKeyDown(Keyboard.KEY_SPACE) + " ===";
  }
}
