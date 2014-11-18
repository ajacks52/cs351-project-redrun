package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;

/**
 * Class to make a hammer trap
 * 
 * @author Adam Mitchell
 *
 *
 */
public class Hammer extends Trap
{

  /**
   * The Hammer constructor
   * 
   * @param x pos
   * @param y pos
   * @param z pos
   * @param textureName
   */
  public Hammer(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);

    float height = 5f;
    float radius = .5f;
    float resolution = .1f;

    displayListId = glGenLists(1);
    // cylinder
    glNewList(displayListId, GL_COMPILE);
    {
      /* top triangle */
      glBegin(GL_TRIANGLE_FAN);
      {
        glVertex3f(0, height, 0); /* center */
        for (float i = 0; i <= 2 * Math.PI; i += resolution)
          glVertex3f((float) (radius * Math.cos(i)), (float) height, (float) (radius * Math.sin(i)));
      }
      glEnd();

      /* bottom triangle*/
      glBegin(GL_TRIANGLE_FAN);
      {
        glVertex3f(0, 0, 0); /* center */
        for (float i = (float) (2 * Math.PI); i >= 0; i -= resolution)
          glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
        /* close the loop back to 0 degrees */
        glVertex3f(radius, height, 0);
      }
      glEnd();

      /* middle tube */
      glBegin(GL_QUAD_STRIP);
      {
        for (float i = 0; i <= 2 * Math.PI; i += resolution)
        {
          glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
          glVertex3f((float) (radius * Math.cos(i)), height, (float) (radius * Math.sin(i)));
        }
        /* close the loop back to zero degrees */
        glVertex3f(radius, 0, 0);
        glVertex3f(radius, height, 0);
      }
      glEnd();

      glBegin(GL_QUAD_STRIP);
      {

      }
      glEnd();

    }
    glEndList();
  }

  public void render()
  {
    glPushMatrix();
    {
      glPushName(this.id);
      {
        glTranslatef(0, 0, 0);
        glColor3f(.301f, .207f, .007f);
        // glScalef(3f, 1f, 3f);
        this.draw();
      }
      glPopName();
    }
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
