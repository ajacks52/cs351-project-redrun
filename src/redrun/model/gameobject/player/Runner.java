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
    // cam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, -10f, -3f, -10f);
    constructPlayer(x, y, z);

  }

  public void constructPlayer(float x, float y, float z)
  {
    player = new Ball(x, y, x, null, 1, new Vector3f(1.0f, 0.0f, 0.0f));

  }

  @Override
  public void interact()
  {

  }

  public void render()
  {
    glPushMatrix();
    {
      glPushName(player.id);
      {
        glTranslatef(player.getX(), player.getY(), player.getZ());
        player.draw();
      }
      glPopName();
    }
    glPopMatrix();
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
