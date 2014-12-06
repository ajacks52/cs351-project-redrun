package redrun.model.gameobject.trap.full;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;

/**
 * Class to make spike trap door. To make a new spike trap door make a new
 * object of this class.
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-13-10
 * 
 */
public class SpikeTrapDoor extends Trap
{
  ShaderLoader sl;
  TrapDoor td1;
  TrapDoor td2;
  float occilate = 0;
  float occilate2 = 0;
  float movementSpeed = 0.15f;

  /**
   * The spike trap constructor pass in x,y,z
   * 
   * @param x starting coordinate
   * @param y starting coordinate
   * @param z starting coordinate
   * @param low
   */
  public SpikeTrapDoor(float x, float y, float z, Direction orientation, String textureName, boolean low)
  {
    super(x, y, z, orientation, null);

    // Physics body...
    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(5, 5, 5), new Quat4f(), 0.0f);
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();
    td1 = new TrapDoor(x, y, z, orientation, textureName);
    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glTranslatef(0.0f, -1.65f, 0.0f);
      glScalef(0.3f, 2.f, 0.2f);
      glColor3f(0.5f, 0.5f, 0.5f);
      for (float z_axis = -10; z_axis < 20; z_axis += 7f)
        for (float x_axis = -8; x_axis < 20; x_axis += 7f)
        {
          glBegin(GL_TRIANGLES);
          {
            // Front triangle...
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 1.0f, 0.0f + z_axis);
            glNormal3f(0.0f, 1.0f, 0.0f);
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(-1.0f + x_axis, -1.0f, 0.0f + z_axis);
            glNormal3f(-1.0f, -1.0f, 0.0f);
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(1.0f + x_axis, -1.0f, 0.0f + z_axis);
            glNormal3f(1.0f, -1.0f, 0.0f);
            // Right triangle...
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(1.0f + x_axis, -1.0f, 0.0f + z_axis);
            glNormal3f(1.0f, -1.0f, 0.0f);
            glVertexAttrib3f(program, 0.5f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 1.0f, 0.0f + z_axis);
            glNormal3f(0.0f, 1.0f, 0.0f);
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(0.0f + x_axis, -1.0f, -1.0f + z_axis);
            glNormal3f(0.0f, -1.0f, -1.0f);
            // Left triangle...
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(-1.0f + x_axis, -1.0f, 0.0f + z_axis);
            glNormal3f(-1.0f, -1.0f, 0.0f);
            glVertexAttrib3f(program, 0.5f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 1.0f, 0.0f + z_axis);
            glNormal3f(0.0f, 1.0f, 0.0f);
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(0.0f + x_axis, -1.0f, -1.0f + z_axis);
            glNormal3f(0.0f, -1.0f, -1.0f);
          }
          glEnd();
        }
    }
    glEndList();
  }

  /**
   * Draws spikes to the screen at the given position
   * 
   * @param the x coord
   * @param the y coord
   * @param the z coord
   */
  @Override
  public void draw()
  {
    for (int i = 0; i < 5; i++)
    {
      glPushMatrix();
      {
        glPushName(this.id);
        {
          glUseProgram(sl.getShaderProgram());
          {
            glTranslatef(body.getX(), (float) ((body.getY() - 1) + 2 * Math.sin(occilate)), body.getZ());
            glCallList(displayListId);
          }
          glUseProgram(0);
        }
        glPopName();
      }
      glPopMatrix();
    }
    update();
    td1.draw();
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    td1.activate();
    this.timer.resume();
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
  }

  @Override
  public void update()
  {
    if (this.timer.getTime() == 0)
    {
      occilate2 = 0;
      occilate = 0;
    }
    else if (occilate2 < 6)
    {
      occilate2 += 0.85f;
    }
    else
    {
      occilate += 0.095f;
    }
    if ((int) this.timer.getTime() == 4)
    {
      System.out.println("Resetting game object: " + this.id);
      reset();
    }
  }

}
