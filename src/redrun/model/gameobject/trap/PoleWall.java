package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;

import java.util.Random;

import redrun.model.toolkit.ShaderLoader;

public class PoleWall extends Trap
{
  Random rand = new Random();
  ShaderLoader sl;

  public PoleWall(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);
    float height = 5f;
    float radius = .3f;
    float resolution = .1f;

    // shaders to color the spikes red
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();

    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);

    // cylinder
    glNewList(displayListId, GL_COMPILE);
    {
      glRotatef(90, 1, 0, 0);
      glScalef(0.3f, 0.5f, 0.3f);
      glColor3f(.2f, .2f, .2f);
      for (int poles = 0; poles < 8; poles++)
      {
        if (poles > 0)
        {
          glTranslatef(3, 0, 5 * (float) (Math.pow(-1, poles)));
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

        glPushMatrix();
        glScalef(0.3f, 1.0f, 0.3f);
        glTranslatef(0.0f, 6.0f, 0.0f);
        glUseProgram(sl.getShaderProgram());

        glBegin(GL_TRIANGLES);
        // Front
        glVertexAttrib3f(program, 0.6f, 0f, 0f);
        glVertex3f(0.0f, 0.1f, 0.0f);
        glVertexAttrib3f(program, 0f, 0f, 0f);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glVertexAttrib3f(program, .5f, .6f, .6f);
        glVertex3f(1.0f, -1.0f, 1.0f);
        // Right
        glVertexAttrib3f(program, 0.6f, 0f, 0f);
        glVertex3f(0.0f, 0.1f, 0.0f);
        glVertexAttrib3f(program, 0f, 0f, 0f);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glVertexAttrib3f(program, .5f, .6f, .6f);
        glVertex3f(1.0f, -1.0f, -1.0f);
        // Back
        glVertexAttrib3f(program, 0.6f, 0f, 0f);
        glVertex3f(0.0f, 0.1f, 0.0f);
        glVertexAttrib3f(program, 0f, 0f, 0f);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glVertexAttrib3f(program, .5f, .6f, .6f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        // Left
        glVertexAttrib3f(program, 0.6f, 0f, 0f);
        glVertex3f(0.0f, 0.1f, 0.0f);
        glVertexAttrib3f(program, 0f, 0f, 0f);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glVertexAttrib3f(program, .5f, .6f, .6f);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glEnd();
        glUseProgram(0);
        glPopMatrix();
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
