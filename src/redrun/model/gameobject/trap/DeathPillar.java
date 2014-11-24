package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;

import javax.vecmath.Quat4f;
//import javax.vecmath.Vector3f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.BoxPhysicsBody;

/**
 * Class to make a hammer trap
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-19-10
 *
 */
public class DeathPillar extends Trap
{

  /**
   * The Hammer constructor
   * 
   * @param x pos
   * @param y pos
   * @param z pos
   * @param textureName
   */
  public DeathPillar(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);
    body = new BoxPhysicsBody(new Vector3f(x,y,z), new Vector3f(0.5f,1.0f,0.5f), new Quat4f(), 100.0f);
    
    float height = 5f;
    float radius = .5f;
    float resolution = .1f;

    displayListId = glGenLists(1);
    // cylinder
    glNewList(displayListId, GL_COMPILE);
    {
      glColor3f(.301f, .207f, .007f);
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

      glBegin(GL_QUADS);
      {
        //glNormal3f(0.0f, 1.0f, 0.0f);
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
    if ((int) this.timer.getTime() == 0)
    {
      
    }
    else if ((int) this.timer.getTime() < 3)
    {
      body.push(new Vector3f(0.0f,-1.0f,0.0f));

    }
    else
    {
      System.out.println("Resetting game object: " + this.id);
      reset();
    }
  }

}
