package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.gameobject.world.*;
import java.awt.Dimension;

/**
 * 
 * Class to make a spike trench.  To make a new spike trench make a new object of this class.
 * 
 * objectName.update(); when you are rendering graphics. 
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-13-10
 * 
 */
public class SpikeTrench extends Trap
{

  private final int trenchDepth = 0;
  SpikeField sf;
  Cube trench;

  /**
   * 
   * @param x pos
   * @param y pos
   * @param z pos
   * @param textureName
   * @param dimension x,z
   */
  public SpikeTrench(float x, float y, float z, String textureName, Dimension dim)
  {
    super(x, y, z, textureName);

    sf = new SpikeField(x, trenchDepth, z, textureName, dim);
    trench = new Cube(x, y, z, null);
  }

  /**
   *  Draws the object in 3d space applies some transformations then calls draw
   * 
   * @param x pos
   * @param y pos
   * @param z pos
   */
  public void render(float x, float y, float z)
  {
    glPushMatrix();
    sf.render(x, trenchDepth, z);
    glPopMatrix();

    glPushMatrix();
    glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
    sf.render(x, trenchDepth, z);
    glPopMatrix();

    glPushMatrix();
    glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
    sf.render(x, trenchDepth, z);
    glPopMatrix();

  }

  @Override
  public void activate()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void interact()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void update()
  {
    // TODO Auto-generated method stub

  }

}
