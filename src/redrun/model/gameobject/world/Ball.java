package redrun.model.gameobject.world;

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

public class Ball extends WorldObject
{

  public Ball(float x, float y, float z, float radius, Vector3f color)
  {
    super(x, y, z);

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
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
        else
        {
          new Sphere().draw(radius, 40, 40);
        }
      }
      glEnd();
    }
    glEndList();
  }

}
