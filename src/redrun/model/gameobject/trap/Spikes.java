package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import redrun.model.toolkit.ShaderLoader;

/**
 * 
 * Class to make spikes. To make a new spike make a new object of this class.
 * 
 * objectName.update(); when you are rendering graphics. 
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-13-10
 * 
 */
public class Spikes extends Trap
{
  ShaderLoader sl;
  TrapDoor td1;
  TrapDoor td2;
  float occilate = 0;
  float occilate2 = 0;

  /**
   * The spike trap constructor pass in x,y,z
   * 
   * @param x starting coordinate
   * @param y starting coordinate
   * @param z starting coordinate
   */
  public Spikes(float x, float y, float z, String textureName)
  {
    super(x, y, z, null);

    td1 = new TrapDoor(1, 1, 1, textureName);
    td2 = new TrapDoor(1, 1, 1, null);

    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();

    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_TRIANGLES);
      {
        // Front triangle...
        // glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
        glVertexAttrib3f(program, 0.6f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(-1.0f, -1.0f, 0.0f);
        glNormal3f(-1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 0.0f);
        glNormal3f(1.0f, -1.0f, 0.0f);
        // Right triangle...
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 0.0f);
        glNormal3f(1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.5f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(0.0f, -1.0f, -1.0f);
        glNormal3f(0.0f, -1.0f, -1.0f);
        // Left triangle...
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(-1.0f, -1.0f, 0.0f);
        glNormal3f(-1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.5f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(0.0f, -1.0f, -1.0f);
        glNormal3f(0.0f, -1.0f, -1.0f);
      }
      glEnd();
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
  public void render(float x, float y, float z)
  {
    for (int i = 0; i < 5; i++)
    {
      glPushMatrix();
      {
        glPushName(this.id);
        {
          glUseProgram(sl.getShaderProgram());
          {
            glTranslatef((x + i), (float) (-5 - 2 * Math.sin(occilate)), 2 + z + i % 2);
            glScalef(0.3f, 2.f, 0.2f);
            glColor3f(0.5f, 0.5f, 0.5f);
            this.draw();
          }
          glUseProgram(0);
        }
        glPopName();
      }
      glPopMatrix();
    }
    td1.render(x, y, z);
  }

  @Override
  public void activate()
  {

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
    System.out.println("Interacting with the game object: " + this.id);
    td1.interact();
    this.timer.resume();
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
      occilate2 += 0.45f;
    }
    else
    {
      occilate += 0.045f;
    }
    if ((int) this.timer.getTime() == 2)
    {
      System.out.println("Resetting game object: " + this.id);
      reset();
    }
  }

}
