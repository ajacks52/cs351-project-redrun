package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.SpherePhysicsBody;

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
