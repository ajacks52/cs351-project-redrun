package redrun.model.gameobject.map;


import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import static org.lwjgl.opengl.GL11.*;

public class Corridor extends Map
{

  boolean DEBUG = true;

  public Corridor(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    super(x, y, z, textureName, orientation, trap);

    
  }
}
