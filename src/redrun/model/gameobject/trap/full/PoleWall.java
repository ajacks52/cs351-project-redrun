package redrun.model.gameobject.trap.full;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionObject;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;

public class PoleWall extends Trap
{
  private ShaderLoader sl;
  private int count = 0;
  private boolean down = true;
  private Direction orientation;

  public PoleWall(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, textureName);
    float height = 17f;
    float radius = .6f;
    float resolution = .1f;
    this.orientation = orientation;

    if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
    {
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(4, 5, 12), new Quat4f(), 0)
      {
        public void collidedWith(CollisionObject other)
        {
          
        }
      };
    }
    if (orientation == Direction.WEST || orientation == Direction.EAST)
    {
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(12, 5, 4), new Quat4f(), 0)
      {
        public void collidedWith(CollisionObject other)
        {
          
        }
      };
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

        if (poles > 0 && (orientation == Direction.NORTH || orientation == Direction.SOUTH))
        {
          glTranslatef(4, 0, 6 * (float) (Math.pow(-1, poles)));
        }
        else if (poles > 0 && (orientation == Direction.WEST || orientation == Direction.EAST))
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
    this.timer.resume();
  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub
    this.timer.pause();
    this.timer.reset();
  }

  @Override
  public void interact()
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void update()
  {
    if (this.timer.getTime() > 0 && count < 15 && down)
    {
      count++;
      if (orientation == Direction.EAST || orientation == Direction.WEST) body.translate(-1f, 0f, 0f);
      if (orientation == Direction.SOUTH || orientation == Direction.NORTH) body.translate(0f, 0f, -1f);
      if (count == 15)
      {
        down = false;
      }
    }
    else if (count > 0 && !down && this.timer.getTime() > 4)
    {
      count--;
      if (orientation == Direction.EAST || orientation == Direction.WEST) body.translate(1f, 0f, 0f);
      if (orientation == Direction.SOUTH || orientation == Direction.NORTH) body.translate(0f, 0f, 1f);
      if (count == 0)
      {
        down = true;
      }
    }
    if (count == 0 && this.timer.getTime() > 0)
    {
      this.reset();
    }
  }

}
