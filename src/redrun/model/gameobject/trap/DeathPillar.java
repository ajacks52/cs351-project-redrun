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
   * The DeathPillar constructor
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
    

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glEnable(GL_CULL_FACE);
      glColor3f(1f, 1f, 1f);
      for (int i = 0; i < 2; i++)
      {
        if(i == 1) glTranslatef(0.0f, -2.0f, 0.0f);
        glBegin(GL_QUADS);
        {
          // Top face...
          glNormal3f(0.0f, 1.0f, 0.0f);
          glVertex3f(1.0f, 1.0f, -1.0f);
          glTexCoord2f(0, 0);
          glVertex3f(-1.0f, 1.0f, -1.0f);
          glTexCoord2f(0, 1);
          glVertex3f(-1.0f, 1.0f, 1.0f);
          glTexCoord2f(1, 1);
          glVertex3f(1.0f, 1.0f, 1.0f);
          glTexCoord2f(1, 0);
          // Bottom face...
          glNormal3f(0.0f, -1.0f, 0.0f);
          glVertex3f(1.0f, -1.0f, 1.0f);
          glTexCoord2f(0, 0);
          glVertex3f(-1.0f, -1.0f, 1.0f);
          glTexCoord2f(0, 1);
          glVertex3f(-1.0f, -1.0f, -1.0f);
          glTexCoord2f(1, 1);
          glVertex3f(1.0f, -1.0f, -1.0f);
          glTexCoord2f(1, 0);
          // Front face...
          glNormal3f(0.0f, 0.0f, -1.0f);
          glVertex3f(1.0f, -1.0f, -1.0f);
          glTexCoord2f(0, 0);
          glVertex3f(-1.0f, -1.0f, -1.0f);
          glTexCoord2f(0, 1);
          glVertex3f(-1.0f, 1.0f, -1.0f);
          glTexCoord2f(1, 1);
          glVertex3f(1.0f, 1.0f, -1.0f);
          glTexCoord2f(1, 0);
          // Back face...
          glNormal3f(0.0f, 0.0f, 1.0f);
          glVertex3f(1.0f, 1.0f, 1.0f);
          glTexCoord2f(0, 0);
          glVertex3f(-1.0f, 1.0f, 1.0f);
          glTexCoord2f(0, 1);
          glVertex3f(-1.0f, -1.0f, 1.0f);
          glTexCoord2f(1, 1);
          glVertex3f(1.0f, -1.0f, 1.0f);
          glTexCoord2f(1, 0);
          // Left face...
          glNormal3f(-1.0f, 0.0f, 0.0f);
          glVertex3f(-1.0f, 1.0f, 1.0f);
          glTexCoord2f(0, 0);
          glVertex3f(-1.0f, 1.0f, -1.0f);
          glTexCoord2f(0, 1);
          glVertex3f(-1.0f, -1.0f, -1.0f);
          glTexCoord2f(1, 1);
          glVertex3f(-1.0f, -1.0f, 1.0f);
          glTexCoord2f(1, 0);
          // Right face...
          glNormal3f(1.0f, 0.0f, 0.0f);
          glVertex3f(1.0f, 1.0f, -1.0f);
          glTexCoord2f(0, 0);
          glVertex3f(1.0f, 1.0f, 1.0f);
          glTexCoord2f(0, 1);
          glVertex3f(1.0f, -1.0f, 1.0f);
          glTexCoord2f(1, 1);
          glVertex3f(1.0f, -1.0f, -1.0f);
          glTexCoord2f(1, 0);
        }
        glEnd();
        glDisable(GL_CULL_FACE);
      }
    }
    glEndList();
  }  
      
//      /* top triangle */
//      glBegin(GL_TRIANGLE_FAN);
//      {
//        glVertex3f(0, height, 0); /* center */
//        for (float i = 0; i <= 2 * Math.PI; i += resolution)
//          glVertex3f((float) (radius * Math.cos(i)), (float) height, (float) (radius * Math.sin(i)));
//      }
//      glEnd();
//
//      /* bottom triangle*/
//      glBegin(GL_TRIANGLE_FAN);
//      {
//        glVertex3f(0, 0, 0); /* center */
//        for (float i = (float) (2 * Math.PI); i >= 0; i -= resolution)
//          glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
//        /* close the loop back to 0 degrees */
//        glVertex3f(radius, height, 0);
//      }
//      glEnd();
//
//      /* middle tube */
//      glBegin(GL_QUAD_STRIP);
//      {
//        for (float i = 0; i <= 2 * Math.PI; i += resolution)
//        {
//          glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
//          glVertex3f((float) (radius * Math.cos(i)), height, (float) (radius * Math.sin(i)));
//        }
//        /* close the loop back to zero degrees */
//        glVertex3f(radius, 0, 0);
//        glVertex3f(radius, height, 0);
//      }
//      glEnd();
//
//      glBegin(GL_QUADS);
//      {
//        //glNormal3f(0.0f, 1.0f, 0.0f);
//        glVertex3f(1.0f, 1.0f, -1.0f);
//        glTexCoord2f(0, 0);
//        glVertex3f(-1.0f, 1.0f, -1.0f);
//        glTexCoord2f(0, 1);
//        glVertex3f(-1.0f, 1.0f, 1.0f);
//        glTexCoord2f(1, 1);
//        glVertex3f(1.0f, 1.0f, 1.0f);
//        glTexCoord2f(1, 0);
//      }
//      glEnd();


  @Override
  public void activate()
  {
    // TODO Auto-generated method stub
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
    this.timer.resume();
  }

  @Override
  public void update()
  {
    if (this.timer.getTime() == 0)
    {
      body.clearForces();
    }
    else if (this.timer.getTime() < 2)
    {
      System.out.println("interactiong with game object: " + this.id);
      body.push(new Vector3f(0.0f, -5000.0f, 0.0f));
    }
    else if (this.timer.getTime() < 4)
    {
      body.push(new Vector3f(0.0f, 5000.0f, 0.0f));
    }
    else if (this.timer.getTime() == 4)
    {
      reset();
    }
  }

}
