package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.SpherePhysicsBody;
import redrun.model.toolkit.BufferConverter;

public class Ball extends WorldObject
{  
  private Sphere sphere;

  public Ball(float x, float y, float z, String textureName, float radius)
  {
    super(x, y, z, textureName);
    
    this.sphere = new Sphere();
    
    this.body = new SpherePhysicsBody(new Vector3f(x, y, z), radius, 1.0f);
    
    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_SPHERE_MAP);
      {
        FloatBuffer materialColor = BufferConverter.asFloatBuffer(new float[] {0.7f, 0.7f, 0.7f, 1.0f});
        FloatBuffer materialSpecular = BufferConverter.asFloatBuffer(new float[] {1.0f, 0.75f, 0.75f, 1.0f});
        FloatBuffer materialShininess = BufferConverter.asFloatBuffer(new float[] {30.0f, 0.0f, 0.0f, 0.0f});
        
        glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, materialColor);
        glMaterial(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterial(GL_FRONT, GL_SHININESS, materialShininess);
        
        sphere.draw(radius, 40, 40);
      }
      glEnd();
    }
    glEndList();
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

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub

  }
}
