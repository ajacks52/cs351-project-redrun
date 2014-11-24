package redrun.model.gameobject.player;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

import redrun.graphics.camera.Camera;
import redrun.model.gameobject.world.*;
import redrun.model.physics.*;

public class Runner extends Player
{

  private Ball player;

  public Runner(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);

  }

  

  @Override
  public void interact()
  {

  }
  
  @Override
  public void update()
  {
    
  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub

  }

}
