package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUseProgram;

import org.newdawn.slick.opengl.Texture;

import redrun.model.toolkit.ShaderLoader;
import redrun.model.toolkit.Tools;

/**
 * 
 * Class to make spikes. To make a new spike make a new object of this class
 * calling the constructor that wants x,y,z and to use call
 * spikeObject.drawTrapDoor(50, 0); followed by spikeObject.update(); when you
 * are rendering graphics.
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-13-10
 * 
 */
public class Spike extends Trap
{

  ShaderLoader sl;
  TrapDoor td;
  Texture wood;
  float occilate = 0;
  float occilate2 = 0;

  /**
   * The spike constructor pass in x,y,z
   * 
   * @param x starting coordinate
   * @param y starting coordinate
   * @param z starting coordinate
   */
  public Spike(float x, float y, float z)
  {
    super(x, y, z);

    td = new TrapDoor(1, 1, 1);
    wood = Tools.loadPNGTexture("wood");

    sl = new ShaderLoader();
    sl.loadShader("shader.fs");
    sl.loadShader("shader.vs");
    sl.deleteShaders();
    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_TRIANGLES);
      {
        // Front triangle...
        glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
        glTexCoord2f(0.5f, 1.0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.25f, 0.5f);
        glVertex3f(-1.0f, -1.0f, 0.0f);
        glNormal3f(-1.0f, -1.0f, 0.0f);
        glTexCoord2f(0.75f, 0.5f);
        glVertex3f(1.0f, -1.0f, 0.0f);
        glNormal3f(1.0f, -1.0f, 0.0f);
        // Right triangle...
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 0.0f);
        glNormal3f(1.0f, -1.0f, 0.0f);
        glTexCoord2f(0.5f, 0.0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.25f, 0.5f);
        glVertex3f(0.0f, -1.0f, -1.0f);
        glNormal3f(0.0f, -1.0f, -1.0f);
        // Left triangle...
        glTexCoord2f(0.5f, 0.0f);
        glVertex3f(-1.0f, -1.0f, 0.0f);
        glNormal3f(-1.0f, -1.0f, 0.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.75f, 0.5f);
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
   * @param the z coord
   */
  public void drawSpikes(float x, float z)
  {
    for (int i = 0; i < 4; i++)
    {
      glPushMatrix();
      {
        glPushName(this.id);
        {
          glUseProgram(sl.getShaderProgram());
          {
            glTranslatef(x + i, (float) (-5.5 + 3 * Math.sin(occilate)), (float) (Math.pow(-1, i) + z));
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
    glPushMatrix();
    {
      glPushName(td.id);
      {
        glColor3f(0.5f, 0.5f, 0.5f);
        glTranslatef((float) (x + 2 * Math.sin(occilate2)), -4.6f, z);
        glScalef(3f, 1f, 3f);
        glEnable(GL_TEXTURE_2D);
        wood.bind();
        td.draw();
        glDisable(GL_TEXTURE_2D);
      }
      glPopName();
    }
    glPopMatrix();
    glPushMatrix();
    {
      glPushName(td.id);
      {
        // glUseProgram(sl.getShaderProgram());
        glColor3f(0.0f, 0.0f, 0.0f);
        glTranslatef(x + 2, -4.7f, z);
        glScalef(2.8f, 1f, 2.8f);
        td.draw();
        // glUseProgram(0);
      }
      glPopName();
    }
    glPopMatrix();
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
    this.timer.resume();
  }

  @Override
  public void update()
  {
    System.out.println(this.timer.getTime());
    if (this.timer.getTime() == 0)
    {
      occilate = 0;
      occilate2 = 1;
    }
    else
    {
      occilate += 0.015f;
      occilate2 += 0.025f;
    }

    if ((int) this.timer.getTime() == 4)
    {
      System.out.println("Resetting game object: " + this.id);
      reset();
    }
  }

}
