package redrun.model.gameobject.trap;

import redrun.model.toolkit.ShaderLoader;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Class to make trap doors.  To make a new trap door make a new object of this class. 
 * 
 * objectName.update(); when you are rendering graphics. 
 * 
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-13-10
 * 
 */
public class TrapDoor extends Trap
{

  ShaderLoader sl;
  float occilate = 0;

  /**
   * Constructor to make a new trap door give the starting position 
   * @param x pos
   * @param y pos
   * @param z pos 
   * @param textureName
   */
  public TrapDoor(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);

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

  /**
   * Draws a trap door to the screen at the given position
   *  
   * @param the x coord 
   * @param the y coord 
   * @param the z coord
   */
  public void render(float x, float y,  float z)
  {
    glPushMatrix();
    {
      glPushName(this.id);
      {
        glColor3f(0.5f, 0.5f, 0.5f);
        glTranslatef((x - occilate), -2.98f, z);
        glScalef(3f, 1f, 3f);
        this.draw();
      }
      glPopName();
    }
    glPopMatrix();
    glPushMatrix();
    {
      glPushName(this.id);
      {
        glColor3f(0.0f, 0.0f, 0.0f);
        glTranslatef(x, -2.99f, z);
        glScalef(2.9f, 1f, 2.9f);
        this.draw();
      }
      glPopName();
    }
    glPopMatrix();
  }

  @Override
  public void reset()
  {
    this.timer.reset();
    this.timer.pause();
  }

  @Override
  public void interact()
  {
    System.out.println("Interacting with the game object: " + this.id);
    this.timer.resume();
  }

  @Override
  public void update()
  {
    if (this.timer.getTime() == 0)
    {
      occilate = 0;
    }
    else if (occilate < 6)
    {
      occilate += 0.15f;
    }
    if ((int) this.timer.getTime() == 2)
    {
      System.out.println("Resetting game object: " + this.id);
      reset();
    }
  }

  @Override
  public void activate()
  {
    // TODO Auto-generated method stub
    
  }

}
