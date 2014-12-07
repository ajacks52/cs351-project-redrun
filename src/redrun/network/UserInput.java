package redrun.network;

import org.lwjgl.input.Keyboard;

public class UserInput
{
  
  @Override
  public String toString()
  {
    return "=== User Input === "
      + "W:" + Keyboard.isKeyDown(Keyboard.KEY_W)
      + " A:" + Keyboard.isKeyDown(Keyboard.KEY_A)
      + " S:" + Keyboard.isKeyDown(Keyboard.KEY_S)
      + " D:" + Keyboard.isKeyDown(Keyboard.KEY_D)
      + " SPACE:" + Keyboard.isKeyDown(Keyboard.KEY_SPACE)
      + " ===";
  }
}
