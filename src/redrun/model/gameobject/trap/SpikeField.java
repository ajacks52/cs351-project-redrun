package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;

import java.awt.Dimension;

import redrun.model.gameobject.world.CheckerBoard;
import redrun.model.toolkit.ShaderLoader;


/**
 * Draws a strip of spikes to the position and size set in the constructor
 *  
 * @author Adam Mitchell
 *
 *
 */
public class SpikeField extends Trap
{

  ShaderLoader sl;
  CheckerBoard board;
  Dimension dim;

  /**
   * Constructor 
   * @param x pos
   * @param y pos
   * @param z pos 
   * @param textureName
   * @param dimension of the trap x,z (not x y)
   */
  public SpikeField(float x, float y, float z, String textureName, Dimension dim)
  {
    super(x, y, z, null);

    this.dim = dim;
    
    // simple shaders to color the spikes red
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();
    
    // the ground of the spike field
    board = new CheckerBoard(x/dim.width, y, z/dim.height, textureName, new Dimension(dim.width, dim.height));

    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_TRIANGLES);
      // Front
      glVertexAttrib3f(program, 0.6f, 0f, 0f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glTexCoord2f(0.5f, 1.0f);
      glVertexAttrib3f(program, 0f, 0f, 0f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glTexCoord2f(0.0f, 0.0f);
      glVertex3f(1.0f, -1.0f, 1.0f);
      glTexCoord2f(1.0f, 0.0f);

      // Right
      glVertexAttrib3f(program, 0.6f, 0f, 0f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glTexCoord2f(0.5f, 1.0f);
      glVertexAttrib3f(program, 0f, 0f, 0f);
      glVertex3f(1.0f, -1.0f, 1.0f);
      glTexCoord2f(0.0f, 0.0f);
      glVertex3f(1.0f, -1.0f, -1.0f);
      glTexCoord2f(1.0f, 0.0f);

      // Back
      glVertexAttrib3f(program, 0.6f, 0f, 0f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glTexCoord2f(0.5f, 1.0f);
      glVertexAttrib3f(program, 0f, 0f, 0f);
      glVertex3f(1.0f, -1.0f, -1.0f);
      glTexCoord2f(0.0f, 0.0f);
      glVertex3f(-1.0f, -1.0f, -1.0f);
      glTexCoord2f(1.0f, 0.0f);

      // Left
      glVertexAttrib3f(program, 0.6f, 0f, 0f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glTexCoord2f(0.5f, 1.0f);
      glVertexAttrib3f(program, 0f, 0f, 0f);
      glVertex3f(-1.0f, -1.0f, -1.0f);
      glTexCoord2f(0.0f, 0.0f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glTexCoord2f(1.0f, 0.0f);
      glEnd();
    }
    glEndList();
  }

  /**
   *  Draws the object in 3d space applies some transformations then calls draw
   * 
   * @param x pos
   * @param y pos
   * @param z pos
   */
  public void render(float x, float y, float z)
  {
    
    glPushMatrix();
    {
      glTranslatef(x, y+.01f, z);
      board.draw();
    }
    glPopMatrix();
    
    for (float j = .5f; j <= this.dim.height-1; j+= 1f)
    for (float i = .5f; i <= this.dim.width-1; i+= 1f)
    {
      glPushMatrix();
      {
        glPushName(this.id);
        {
          glUseProgram(sl.getShaderProgram());
          {
            glTranslatef(x + i , y, z + j);
            glScalef(0.05f, 1f, 0.05f);
            this.draw();
          }
          glUseProgram(0);
        }
        glPopName();
      }
      glPopMatrix();
    }
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
