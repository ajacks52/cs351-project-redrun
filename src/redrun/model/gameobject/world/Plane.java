package redrun.model.gameobject.world;

import java.nio.FloatBuffer;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.BufferConverter;
import static org.lwjgl.opengl.GL11.*;

/**
 * This class represents a cube that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class Plane extends WorldObject
{
  /**
   * Creates a new cube at the specified position.
   * 
   * @param x the initial x position
   * @param y the initial y position
   * @param z the initial z position
   */
  public Plane(float x, float y, float z, String textureName, float size)
  {
    super(x, y, z, textureName);
    
    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(size / 2, 0, size / 2), new Quat4f(), 0);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        FloatBuffer materialColor = BufferConverter.asFloatBuffer(new float[] {0.2f, 0.2f, 1.0f, 1.0f});
        FloatBuffer materialSpecular = BufferConverter.asFloatBuffer(new float[] {1.0f, 1.0f, 1.0f, 1.0f});
        FloatBuffer materialEmission = BufferConverter.asFloatBuffer(new float[] {0.05f, 0.05f, 0.05f, 1.0f});
        
        glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, materialColor);
        glMaterial(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterial(GL_FRONT, GL_EMISSION, materialEmission);
      	
        glNormal3f(0.0f, -1.0f, 0.0f);
        glColor3f(1.0f, 0.5f, 0.0f);
        glVertex3f(size / 2, 0.0f, size / 2);
        glTexCoord2f(0, 0);
        glVertex3f(-size / 2, 0.0f, size / 2);
        glTexCoord2f(0, 1);
        glVertex3f(-size / 2, 0.0f, -size / 2);
        glTexCoord2f(1, 1);
        glVertex3f(size / 2, 0.0f, -size / 2);
        glTexCoord2f(1, 0);
      }
      glEnd();
    }
    glEndList();
  }

  @Override
  public void interact()
  {
    System.out.println("Interacting with the game object: " + this.id);
  }

  @Override
  public void update()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub

  }
}