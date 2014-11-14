package redrun.model.gameobject.trap;

import org.newdawn.slick.opengl.Texture;

import redrun.model.gameobject.GameObject;
import redrun.model.toolkit.ShaderLoader;
import redrun.model.toolkit.Tools;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Class to make trap doors.  To make a new trap door make a new object of this class 
 * calling the constructor that wants x,y,z and to use call trapDoorObject.drawTrapDoor(50, 0);
 * followed by trapDoorObject.update(); when you are rendering graphics. 
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-13-10
 * 
 */
public class TrapDoor extends GameObject
{

  ShaderLoader sl;
  Texture wood;
  float occilate = 0;

  /**
   * Constructor to make a new trap door give the starting position 
   * @param starting x
   * @param starting y
   * @param starting z
   */
  public TrapDoor(float x, float y, float z)
  {
    super(x, y, z);

    displayListId = glGenLists(1);
    wood = Tools.loadTexture("wood", "png");

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
   * @param the z coord
   */
  public void drawTrapDoor(float x, float z)
  {
    System.out.println("td occilate "+ occilate);
    glPushMatrix();
    {
      glPushName(this.id);
      {
        glColor3f(0.5f, 0.5f, 0.5f);
        glTranslatef((float) (x - occilate), -4.6f, z);
        glScalef(3f, 1f, 3f);
        glEnable(GL_TEXTURE_2D);
        wood.bind();
        this.draw();
        glDisable(GL_TEXTURE_2D);
      }
      glPopName();
    }
    glPopMatrix();
    glPushMatrix();
    {
      glPushName(this.id);
      {
        glColor3f(0.0f, 0.0f, 0.0f);
        glTranslatef(x, -4.7f, z);
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
    else if (occilate < 5)
    {
      occilate += 0.15f;
    }
    if ((int) this.timer.getTime() == 2)
    {
      System.out.println("Resetting game object: " + this.id);
      reset();
    }
  }

}
