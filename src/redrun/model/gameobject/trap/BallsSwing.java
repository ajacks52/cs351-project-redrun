package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_SPHERE_MAP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.world.Ball;
import redrun.model.physics.SpherePhysicsBody;

public class BallsSwing extends Trap
{
  Sphere sphere;

  public BallsSwing(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, textureName);

    float radius = 1;
    sphere = new Sphere();

    body = new SpherePhysicsBody(new Vector3f(x, y, z), radius, 1.0f);

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
