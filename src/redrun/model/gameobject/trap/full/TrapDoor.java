package redrun.model.gameobject.trap.full;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;
import static org.lwjgl.opengl.GL11.*;

/**
 * Class to make trap doors. To make a new trap door make a new object of this
 * class.
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-13-10
 * 
 */
public class TrapDoor extends Trap
{
  ShaderLoader sl;
  Direction orientation;
  private int count = 0;
  private boolean down = true;

  /**
   * Constructor to make a new trap door give the starting position
   * 
   * @param x pos
   * @param y pos
   * @param z pos
   * @param textureName
   */
  public TrapDoor(float x, float y, float z, Direction orientation, String textureName, boolean low)
  {
    super(x, y +3 , z, orientation, textureName);

    this.body = new BoxPhysicsBody(new Vector3f(x, y +3, z), new Vector3f(5, 0f, 5), new Quat4f(), 0.0f);

    this.orientation = orientation;

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertex3f(1.0f, 0.0f, -1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, 0.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, 0.0f, 1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, 0.0f, 1.0f);
        glTexCoord2f(1, 0);
      }
      glEnd();
    }
    glEndList();
  }

  @Override
  public void draw()
  {
    glPushMatrix();
    {
      glColor3f(1f, 1f, 1f);
      glMultMatrix(body.getOpenGLTransformMatrix());
      glScalef(6f, 1f, 6f);
      glEnable(GL_TEXTURE_2D);
      texture.bind();
      glCallList(displayListId);
      glDisable(GL_TEXTURE_2D);
    }
    glPopMatrix();
    update();
  }

  @Override
  public void activate()
  {
    this.timer.resume();
  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub
    this.timer.pause();
    this.timer.reset();
  }

  @Override
  public void interact()
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void update()
  {
    if (this.timer.getTime() > 0 && count < 10 && down)
    {
      count++;
      if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
      {
        body.translate(1f, 0f, 0f);
      }
      if (orientation == Direction.EAST || orientation == Direction.WEST)
      {
        body.translate(0f, 0f, 1f);
      }

      if (count == 10)
      {
        down = false;
      }
    }
    else if (count > 0 && !down && this.timer.getTime() > 6)
    {
      count--;
      if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
      {
        body.translate(-1f, 0f, 0f);
      }
      if (orientation == Direction.EAST || orientation == Direction.WEST)
      {
        body.translate(0f, 0f, -1f);
      }
      if (count == 0)
      {
        down = true;
      }
    }
    if (count == 0 && this.timer.getTime() > 0)
    {
      this.reset();
    }
  }
}
