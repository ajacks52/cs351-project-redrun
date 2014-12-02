package redrun.model.gameobject.trap;

import redrun.model.constants.Direction;
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
  boolean forward = true;
  float occilate = 0;
  float movementSpeed = 1.5f;
  Direction dir;

  /**
   * Constructor to make a new trap door give the starting position
   * 
   * @param x pos
   * @param y pos
   * @param z pos
   * @param textureName
   */
  public TrapDoor(float x, float y, float z, Direction dir, String textureName)
  {
    super(x, y - 1.70f, z, dir, textureName);

    this.dir = dir;
    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, 1.0f, 1.0f);
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
      glColor3f(0.5f, 0.5f, 0.5f);
      if (dir == Direction.EAST) glTranslatef((body.getX()), body.getY(), body.getZ() + occilate);
      if (dir == Direction.SOUTH) glTranslatef((body.getX()), body.getY(), body.getZ() - occilate);
      if (dir == Direction.WEST) glTranslatef((body.getX() - occilate), body.getY(), body.getZ());
      if (dir == Direction.NORTH) glTranslatef((body.getX() + occilate), body.getY(), body.getZ());

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
  public void reset()
  {
    this.timer.reset();
    this.timer.pause();
    this.forward = true;
  }

  @Override
  public void interact()
  { 
  }

  @Override
  public void update()
  {
    if (this.timer.getTime() == 0)
    {
      occilate = 0;
    }
    else if (occilate < 9.5 && forward)
    {
      occilate += movementSpeed;
    }
    else forward = false;
    if ((int) this.timer.getTime() == 5)
    {
      System.out.println("Resetting game object: " + this.id);
      reset();
    }
    else if ((int) this.timer.getTime() > 4 && occilate > 0)
    {
      occilate -= movementSpeed;
    }
  }

  @Override
  public void activate()
  {
    // TODO Auto-generated method stub
    System.out.println("Interacting with the game object: " + this.id);
    this.timer.resume();
  }

}
