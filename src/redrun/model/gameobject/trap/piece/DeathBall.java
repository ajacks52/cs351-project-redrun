package redrun.model.gameobject.trap.piece;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.SpherePhysicsBody;
import redrun.model.toolkit.BufferConverter;

public class DeathBall extends Trap
{  
  private Sphere sphere;

  public DeathBall(float x, float y, float z,  Direction orientation, String textureName, float startTime)
  {
    super(x, y, z, orientation, null);
    
    this.sphere = new Sphere();
    
    float radius = 5;
    
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

  @Override
  public void activate()
  {
    // TODO Auto-generated method stub
    
  }
}
