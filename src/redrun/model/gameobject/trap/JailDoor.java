package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.physics.PlanePhysicsBody;

public class JailDoor extends Trap
{

  public JailDoor(float x, float y, float z, Direction dir, String textureName)
  {
    super(x, y, z, textureName);

    float height = 7f;
    float radius = .5f;
    float resolution = .1f;
    
    this.body = new PlanePhysicsBody(new Vector3f(x, y, z), new Vector3f(1, 1, 1), 1000.0f);

    displayListId = glGenLists(1);

    // cylinder
    glNewList(displayListId, GL_COMPILE);
    {

      glPushMatrix();
      // glRotatef(90, 1, 0, 0);
      glScalef(0.3f, 2f, 0.3f);
      glColor3f(.2f, .2f, .2f);
      glTranslatef(-18, 0, 0);

      for (int poles = 0; poles < 8; poles++)
      {
        if (poles > 0)
        {
          if (dir == Direction.EAST || dir == Direction.WEST) glTranslatef(5, 0, 0);
          if (dir == Direction.NORTH || dir == Direction.SOUTH) glTranslatef(0, 0, 5);
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
      glPopMatrix();

      glTranslatef(5.0f, 0.0f, 0.0f);
      glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
      glScalef(0.3f, 1.5f, 0.3f);

      for (int j = 0; j < 5; j++)
      {
        glTranslatef(8.0f, 0.0f, 0.0f); 
        
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
