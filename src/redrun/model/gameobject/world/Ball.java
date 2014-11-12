package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

public class Ball extends WorldObject
{

  public Ball(float x, float y, float z, float radius, Vector3f color)
  {
    super(x, y, z);

    displayListId = glGenLists(1);
    
    glNewList(displayListId, GL_COMPILE);
    {
      glTranslatef(this.getX(), this.getY(), this.getZ());

      glBegin(GL_SPHERE_MAP);
      {
        if (color != null)
        {
          glEnable(GL_COLOR_MATERIAL);
          glColor3f(color.x, color.y, color.z);
          new Sphere().draw(radius, 40, 40);
          glColor3f(1.0f, 1.0f, 1.0f);
          glDisable(GL_COLOR_MATERIAL);
        }
        else new Sphere().draw(radius, 40, 40);
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
