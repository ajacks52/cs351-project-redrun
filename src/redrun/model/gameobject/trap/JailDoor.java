package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class JailDoor extends Trap
{

  public JailDoor(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);

    float height = 5f;
    float radius = .3f;
    float resolution = .1f;

    displayListId = glGenLists(1);

    // cylinder
    glNewList(displayListId, GL_COMPILE);
    {
      // glRotatef(90, 1, 0, 0);
      glScalef(0.2f, 1f, 0.2f);
      glColor3f(.2f, .2f, .2f);
      for (int poles = 0; poles < 7; poles++)
      {
        if (poles > 0)
        {
          glTranslatef(3, 0, 0);
        }
        
        /* top triangle */
        glBegin(GL_TRIANGLE_FAN);
        {
          glVertex3f(0, height, 0); /* center */
          for (float i = 0; i <= 2 * Math.PI; i += resolution)
            glVertex3f((float) (radius * Math.cos(i)), (float) height, (float) (radius * Math.sin(i)));
        }
        glEnd();
        /* bottom triangle */
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
      }
    }
    glEndList();

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
