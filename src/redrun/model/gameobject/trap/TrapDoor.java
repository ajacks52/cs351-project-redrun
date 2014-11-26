package redrun.model.gameobject.trap;

import redrun.model.toolkit.ShaderLoader;
import static org.lwjgl.opengl.GL11.*;

/** 
 * Class to make trap doors.  To make a new trap door make a new object of this class.  
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
  float movementSpeed = 0.15f;

  /**
   * Constructor to make a new trap door give the starting position 
   * @param x pos
   * @param y pos
   * @param z pos 
   * @param textureName
   */
  public TrapDoor(float x, float y, float z, String textureName)
  {
    super(x, y-1.95f, z, textureName);

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
        glTranslatef((body.getX() - occilate), body.getY(), body.getZ());
        glScalef(3f, 1f, 3f);
        glEnable(GL_TEXTURE_2D);
        texture.bind(); 
        glCallList(displayListId);
        glDisable(GL_TEXTURE_2D);
    }
    glPopMatrix();
    glPushMatrix();
    {
      {
        glColor3f(0.0f, 0.0f, 0.0f);
        glTranslatef(body.getX()+0.05f, body.getY()-0.001f, body.getZ()-0.05f);
        glScalef(2.9f, 1f, 2.9f);
        glCallList(displayListId);
      }
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
    else if (occilate < 5.8 && forward)
    {
      occilate += movementSpeed;
    }
    else forward = false;
    if ((int) this.timer.getTime() == 4)
    {
      System.out.println("Resetting game object: " + this.id);
      reset();
    }
    else if ((int) this.timer.getTime() > 2 && occilate > 0)
    {
      occilate -= movementSpeed;
    }
  }

  @Override
  public void activate()
  {
    // TODO Auto-generated method stub
    
  }

}
