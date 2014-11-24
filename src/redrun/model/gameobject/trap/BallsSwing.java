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

import redrun.model.gameobject.world.Ball;

public class BallsSwing extends Trap
{

  public BallsSwing(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);
    
     

    float radius = 2;
    Vector3f color = new Vector3f(0.3f, 0.3f, 0.3f);
    
    Ball b1 = new Ball(x, y, z, null, radius, color);
    Ball b2 = new Ball(x, y, z+10, null, radius, color);
    Ball b3 = new Ball(x, y, z+20, null, radius, color);
    Ball b4 = new Ball(x, y, z+30, null, radius, color);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      b1.draw();
      b2.draw();
      b3.draw();
      b4.draw();
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
