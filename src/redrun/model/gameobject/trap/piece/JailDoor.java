package redrun.model.gameobject.trap.piece;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;

/**
 * Creates a jail door to block the path of players
 * 
 * @author Adam Mitchell
 *
 */
public class JailDoor extends Trap
{
  private ShaderLoader sl;
  private int count = 0;
  private boolean down = true;

  /**
   * Constructor
   * 
   * @param x same as map object
   * @param y same as map object
   * @param z same as map object
   * @param same direction as the map object
   * @param just use null
   */
  public JailDoor(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, null);
    // zero in the axis that is flat
    if (orientation == Direction.EAST || orientation == Direction.WEST)
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(5, 13, 0), new Quat4f(), 0.0f);
    if (orientation == Direction.SOUTH || orientation == Direction.NORTH)
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(0, 13, 5), new Quat4f(), 0.0f);
    float height = 7f;
    float radius = .5f;
    float resolution = .1f;

    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();
    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glPushMatrix();
      // glRotatef(90, 1, 0, 0);

      glUseProgram(sl.getShaderProgram());

      glScalef(0.3f, 2f, 0.3f);
      if (orientation == Direction.EAST || orientation == Direction.WEST) glTranslatef(-18, 0, 0);
      if (orientation == Direction.NORTH || orientation == Direction.SOUTH) glTranslatef(0, 0, -18);
      for (int poles = 0; poles < 8; poles++)
      {
        if (poles > 0)
        {
          if (orientation == Direction.EAST || orientation == Direction.WEST) glTranslatef(5, 0, 0);
          if (orientation == Direction.NORTH || orientation == Direction.SOUTH) glTranslatef(0, 0, 5);
        }
        /* top triangle */
        glBegin(GL_TRIANGLE_FAN);
        {
          glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
          glVertex3f(0, height, 0); /* center */
          for (float i = 0; i <= 2 * Math.PI; i += resolution)
          {
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f((float) (radius * Math.cos(i)), (float) height, (float) (radius * Math.sin(i)));
          }
        }
        glEnd();
        /* bottom triangle */
        glBegin(GL_TRIANGLE_FAN);
        {
          glVertex3f(0, 0, 0); /* center */
          for (float i = (float) (2 * Math.PI); i >= 0; i -= resolution)
          {
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
          }
          glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
          glVertex3f(radius, height, 0);
        }
        glEnd();
        /* middle tube */
        glBegin(GL_QUAD_STRIP);
        {
          for (float i = 0; i <= 2 * Math.PI; i += resolution)
          {
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f((float) (radius * Math.cos(i)), height, (float) (radius * Math.sin(i)));
          }
          glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
          glVertex3f(radius, 0, 0);
          glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
          glVertex3f(radius, height, 0);
        }
        glEnd();
      }
      glUseProgram(0);
      glPopMatrix();
      glPushMatrix();
      {
        glUseProgram(sl.getShaderProgram());
        if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
        {
          glTranslatef(0.0f, 0.0f, -5.5f);
          glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (orientation == Direction.EAST || orientation == Direction.WEST)
        {
          glTranslatef(5.0f, 0.0f, 0.0f);
          glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        }
        glScalef(0.3f, 1.5f, 0.3f);
        for (int j = 0; j < 5; j++)
        {
          if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
          {
            glTranslatef(0.0f, 0.0f, -8.0f);
          }
          else if (orientation == Direction.EAST || orientation == Direction.WEST)
          {
            glTranslatef(8.0f, 0.0f, 0.0f);
          }
          /* top triangle */
          glBegin(GL_TRIANGLE_FAN);
          {
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f(0, height, 0); /* center */
            for (float i = 0; i <= 2 * Math.PI; i += resolution)
            {
              glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
              glVertex3f((float) (radius * Math.cos(i)), (float) height, (float) (radius * Math.sin(i)));
            }
          }
          glEnd();
          /* bottom triangle */
          glBegin(GL_TRIANGLE_FAN);
          {
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f(0, 0, 0); /* center */
            for (float i = (float) (2 * Math.PI); i >= 0; i -= resolution)
            {
              glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
              glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
            }
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f(radius, height, 0);
          }
          glEnd();
          /* middle tube */
          glBegin(GL_QUAD_STRIP);
          {
            for (float i = 0; i <= 2 * Math.PI; i += resolution)
            {
              glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
              glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
              glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
              glVertex3f((float) (radius * Math.cos(i)), height, (float) (radius * Math.sin(i)));
            }
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f(radius, 0, 0);
            glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
            glVertex3f(radius, height, 0);
          }
          glEnd();
        }
      }
      glUseProgram(0);
      glPopMatrix();
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
      body.translate(0f, -1f, 0f);
      if (count == 15)
      {
        down = false;
      }
    }
    else if (count > 0 && !down && this.timer.getTime() > 10)
    {
      count--;
      body.translate(0f, 1f, 0f);
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
