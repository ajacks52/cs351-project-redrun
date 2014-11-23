package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.SpherePhysicsBody;

/**
 * Creates a button object. This button sits on a pedestal and reacts to clicks.
 * 
 * @author J. Jake Nichol
 * @since 11-11-2014
 * @version 1.0
 */
public class Button extends WorldObject
{
  Sphere sphere = new Sphere();
//  private Vector3f defaultButtonPosition; // See todo below.
  Vector3f color;

  public Button(float x, float y, float z, String textureName, Vector3f color)
  {
    super(x, y, z, textureName);

//    defaultButtonPosition = new Vector3f(x, y, z); // See todo below.
    this.color = color;

    sphere.setDrawStyle(GLU.GLU_FILL);
    if (textureName != null) sphere.setTextureFlag(true);
    sphere.setNormals(GLU.GLU_SMOOTH);
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

    // TODO: For animation, under construction.
    // if (timer.isPaused() && button.getY() < defaultButtonPosition.y)
    // button.setY(button.getY() + 0.02f);
    // else if (!timer.isPaused()) button.setY(button.getY() - 0.02f);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glPushMatrix();
      {
        glNewList(displayListId, GL_COMPILE);
        {
          glBegin(GL_SPHERE_MAP);
          {
            if (color != null)
            {
              glColor3f(color.x, color.y, color.z);
              sphere.draw(0.5f, 40, 40);
            }
            sphere.draw(0.5f, 40, 40);
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
