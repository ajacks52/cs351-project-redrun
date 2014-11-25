package redrun.model.gameobject.trap;

import java.awt.Dimension;
import javax.vecmath.Quat4f;
import org.lwjgl.util.vector.Vector3f;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Draws a strip of spikes to the position and size set in the constructor
 * 
 * @author Adam Mitchell
 */
public class SpikeField extends Trap
{
  ShaderLoader sl;
  Dimension dim;

  /**
   * Constructor
   * 
   * @param x pos
   * @param y pos
   * @param z pos
   * @param textureName
   * @param dimension of the trap x,z (not x y)
   */
  public SpikeField(float x, float y, float z, String textureName, Dimension dim)
  {
    super(x, y, z, textureName);
    body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(dim.width, 1, dim.height), new Quat4f(0, 0, 0, 1), 0);
    this.dim = dim;

    // shaders to color the spikes red
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();

    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      glNormal3f(0.0f, 1.0f, 0.0f);
      for (int width = 0; width < dim.width - 1; width++)
        for (int height = 0; height < dim.height - 1; height++)
        {
          glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
          glVertex3d(width, -0.999f, height);
          glTexCoord2f(0, 0);
          glVertex3d(width + 1, -0.999f, height);
          glTexCoord2f(0, 1);
          glVertex3d(width + 1, -0.999f, height + 1);
          glTexCoord2f(1, 1);
          glVertex3d(width, -0.999f, height + 1);
          glTexCoord2f(1, 0);
        }
      glEnd();

      glScalef(0.05f, 1f, 0.05f);
      for (float z_axis = 2f; z_axis < dim.height * 20; z_axis += 25f)
        for (float x_axis = 2f; x_axis < dim.width * 20; x_axis += 25f)
        {
          glUseProgram(sl.getShaderProgram());

          glBegin(GL_TRIANGLES);
          // Front
          glVertexAttrib3f(program, 0.6f, 0f, 0f);
          glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
          glVertexAttrib3f(program, 0f, 0f, 0f);
          glVertex3f(-1.0f + x_axis, -1.0f, 1.0f + z_axis);
          glVertex3f(1.0f + x_axis, -1.0f, 1.0f + z_axis);
          // Right
          glVertexAttrib3f(program, 0.6f, 0f, 0f);
          glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
          glVertexAttrib3f(program, 0f, 0f, 0f);
          glVertex3f(1.0f + x_axis, -1.0f, 1.0f + z_axis);
          glVertex3f(1.0f + x_axis, -1.0f, -1.0f + z_axis);
          // Back
          glVertexAttrib3f(program, 0.6f, 0f, 0f);
          glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
          glVertexAttrib3f(program, 0f, 0f, 0f);
          glVertex3f(1.0f + x_axis, -1.0f, -1.0f + z_axis);
          glVertex3f(-1.0f + x_axis, -1.0f, -1.0f + z_axis);
          // Left
          glVertexAttrib3f(program, 0.6f, 0f, 0f);
          glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
          glVertexAttrib3f(program, 0f, 0f, 0f);
          glVertex3f(-1.0f + x_axis, -1.0f, -1.0f + z_axis);
          glVertex3f(-1.0f + x_axis, -1.0f, 1.0f + z_axis);
          glEnd();
          glUseProgram(0);
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
