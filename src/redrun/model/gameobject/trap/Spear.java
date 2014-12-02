package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;

public class Spear extends Trap
{
  ShaderLoader sl;

  public Spear(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, textureName);
    float height = 10f;
    float radius = .3f;
    float resolution = .1f;

    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(0.2f, 12f, 0.2f), new Quat4f(), 0);

    // shaders to color the spikes
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();

    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glPushMatrix();
      {
        glTranslatef(0, -0.5f, 0);
        glUseProgram(sl.getShaderProgram());
        glBegin(GL_QUADS);
        {
          glNormal3f(0.0f, 1.0f, 0.0f);
          glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
          glVertex3f(0.5f, 0.5f, -0.5f);
          glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
          glVertex3f(-0.5f, 0.5f, -0.5f);
          glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
          glVertex3f(-0.5f, 0.5f, 0.5f);
          glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
          glVertex3f(0.5f, 0.5f, 0.5f);
        }
        glEnd();
        glUseProgram(0);
      }
      glPopMatrix();

      glColor3f(0.1f, 0.1f, 0.1f);
      
      glUseProgram(sl.getShaderProgram());
      glBegin(GL_TRIANGLE_FAN);
      {
        glColor3f(.1f, .1f, .1f);
         glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
        glVertex3f(0, height, 0); /* center */
        for (float i = 0; i <= 2 * Math.PI; i += resolution)
        {
           glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
          glVertex3f((float) (radius * Math.cos(i)), (float) height, (float) (radius * Math.sin(i)));
        }
      }
      glEnd();
      glBegin(GL_TRIANGLE_FAN);
      {
        glColor3f(.1f, .1f, .1f);
         glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
        glVertex3f(0, 0, 0); /* center */
        for (float i = (float) (2 * Math.PI); i >= 0; i -= resolution)
        {
           glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
          glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
        }
        glVertex3f(radius, height, 0);
      }
      glEnd();
      /* middle tube */
      glBegin(GL_QUAD_STRIP);
      {
        for (float i = 0; i <= 2 * Math.PI; i += resolution)
        {
          glColor3f(.1f, .1f, .1f);
           glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
          glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
          glColor3f(.1f, .1f, .1f);
           glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
          glVertex3f((float) (radius * Math.cos(i)), height, (float) (radius * Math.sin(i)));
        }
        glColor3f(.1f, .1f, .1f);
         glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
        glVertex3f(radius, 0, 0);
         glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
        glVertex3f(radius, height, 0);
      }
      glEnd();
      glUseProgram(0);

      glPushMatrix();
      glScalef(0.3f, 3f, 0.3f);
      glTranslatef(0.0f, 4.31f, 0.0f);
      glUseProgram(sl.getShaderProgram());

      glBegin(GL_TRIANGLES);
      // Front
      glVertexAttrib3f(program, 0.6f, 0.6f, 0.6f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glVertexAttrib3f(program, .1f, .1f, .1f);
      glVertex3f(1.0f, -1.0f, 1.0f);
      // Right
      glVertexAttrib3f(program, 0.6f, 0.6f, 0.6f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(1.0f, -1.0f, 1.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(1.0f, -1.0f, -1.0f);
      // Back
      glVertexAttrib3f(program, 0.6f, 0.6f, 0.6f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(1.0f, -1.0f, -1.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(-1.0f, -1.0f, -1.0f);
      // Left
      glVertexAttrib3f(program, 0.6f, 0.6f, 0.6f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(-1.0f, -1.0f, -1.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glEnd();
      glUseProgram(0);
      glPopMatrix();
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
