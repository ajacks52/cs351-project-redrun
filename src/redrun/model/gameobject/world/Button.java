package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.PhysicsWorld;
import redrun.model.physics.SpherePhysicsBody;
import redrun.model.toolkit.Tools;

/**
 * Creates a button object. This button sits on a pedestal and reacts to clicks.
 * 
 * @author J. Jake Nichol
 * @since 11-11-2014
 * @version 1.0
 */
public class Button extends WorldObject
{
  private Vector3f defaultButtonPosition;

  public Button(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);

    defaultButtonPosition = new Vector3f(x, y, z);
    body = new SpherePhysicsBody(defaultButtonPosition, 0.08f, 20);
  }

  @Override
  public void interact()
  {
    System.out.println("Interacting with the game object: " + this.id);
    this.timer.resume();
  }

  @Override
  public void update()
  {
    if (this.timer.getTime() >= 0.8f) reset();

    // if (timer.isPaused() && button.getY() < defaultButtonPosition.y) button.setY(button.getY() + 0.02f);
    // else if (!timer.isPaused()) button.setY(button.getY() - 0.02f);
    if (timer.isPaused() && this.getY() < defaultButtonPosition.y) this.getBody().push(new Vector3f(10, -1.0f, 10));
    else if (!timer.isPaused()) this.getBody().push(new Vector3f(10, 1.0f, 10));

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glPushMatrix();
      {
        glNewList(displayListId, GL_COMPILE);
        {
          glBegin(GL_SPHERE_MAP);
          {
            // if (color != null)
            // {
            // glEnable(GL_COLOR_MATERIAL);
            // glColor3f(color.x, color.y, color.z);
            // new Sphere().draw(0.5f, 40, 40);
            // glColor3f(1.0f, 1.0f, 1.0f);
            // glDisable(GL_COLOR_MATERIAL);
            // }
            new Sphere().draw(0.5f, 40, 40);
          }
          glEnd();
        }
        glEndList();
      }
      glPopMatrix();
    }
    glEndList();
  }

  @Override
  public void reset()
  {
    System.out.println("Resetting game object: " + this.id);
    this.timer.reset();
    this.timer.pause();
  }
}
