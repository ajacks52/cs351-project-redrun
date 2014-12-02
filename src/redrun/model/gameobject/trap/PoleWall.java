package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;

import java.util.Random;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;

public class PoleWall extends Trap
{
  Random rand = new Random();
  ShaderLoader sl;

  public PoleWall(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, textureName);
    float height = 17f;
    float radius = .6f;
    float resolution = .1f;

    if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
    {
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(4, 4, 12), new Quat4f(), 0);
    }
    if (orientation == Direction.WEST || orientation == Direction.EAST)
    {
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(12, 4, 4), new Quat4f(), 0);
    }

    // shaders to color the spikes red
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();

    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      
      if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
      {
        glTranslatef(-2, 4, 0);
        glRotatef(90, 1, 0, 0);
      }
      else if (orientation == Direction.WEST || orientation == Direction.EAST)
      {
        glTranslatef(0, 5, -2);
        glRotatef(90, 0, 0, 1);
      }
      glScalef(0.3f, 0.5f, 0.3f);
      glColor3f(.1f, .1f, .1f);
      for (int poles = 0; poles < 8; poles++)
      {
        
        if (poles > 0  && (orientation == Direction.NORTH || orientation == Direction.SOUTH))
        {
          glTranslatef(4, 0, 6 * (float) (Math.pow(-1, poles)));
        }
        else if (poles > 0  && (orientation == Direction.WEST || orientation == Direction.EAST))
        {
          glTranslatef(6 * (float) (Math.pow(-1, poles)), 0, 4);
        }

        glBegin(GL_TRIANGLE_FAN);
        {
          glColor3f(.1f, .1f, .1f);
          glVertex3f(0, height, 0); /* center */
          for (float i = 0; i <= 2 * Math.PI; i += resolution)
            glVertex3f((float) (radius * Math.cos(i)), (float) height, (float) (radius * Math.sin(i)));
        }
        glEnd();

        glBegin(GL_TRIANGLE_FAN);
        {
          glColor3f(.1f, .1f, .1f);
          glVertex3f(0, 0, 0); /* center */
          for (float i = (float) (2 * Math.PI); i >= 0; i -= resolution)
            glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
          glVertex3f(radius, height, 0);
        }
        glEnd();

        /* middle tube */
        glBegin(GL_QUAD_STRIP);
        {
          for (float i = 0; i <= 2 * Math.PI; i += resolution)
          {
            glColor3f(.1f, .1f, .1f);
            glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
            glColor3f(.1f, .1f, .1f);
            glVertex3f((float) (radius * Math.cos(i)), height, (float) (radius * Math.sin(i)));
          }
          glColor3f(.1f, .1f, .1f);
          glVertex3f(radius, 0, 0);
          glVertex3f(radius, height, 0);
        }
        glEnd();

        glPushMatrix();
        glScalef(0.6f, 1.5f, 0.6f);
        glTranslatef(0.0f, 12.35f, 0.0f);
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
