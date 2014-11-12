package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.Timer;
import org.lwjgl.util.vector.Vector3f;

/**
 * Creates a button object. This button sits on a pedestal and reacts to clicks.
 * 
 * @author JakeNichol
 * @since 11-11-2014
 * @version 1.0
 */
public class Button extends WorldObject
{
  private Vector3f defaultButtonPosition;

  private Cube pedestal;
  private Ball button;

  public Button(float x, float y, float z)
  {
    super(x, y, z);
    defaultButtonPosition = new Vector3f(x, y + 1, z);

    pedestal = new Cube(x, y, z);
    button = new Ball(x, y + 1, z, 0.5f, new Vector3f(1.0f, 0.0f, 0.0f));

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glPushMatrix();
      {
        pedestal.draw();
      }
      glPopMatrix();

      glPushMatrix();
      {
        button.draw();
      }
      glPopMatrix();
    }
    glEndList();
  }

  @Override
  public void interact()
  {
    System.out.println("Interacting with the game object: " + this.id);
    this.timer.resume();
    button.setY(button.getY() - 1);
  }

  @Override
  public void update()
  {
    if ((int) this.timer.getTime() == 4) reset();
    button.setY(button.getY() - 0.001f);
  }

  @Override
  public void reset()
  {
    System.out.println("Resetting game object: " + this.id);
    this.timer.reset();
    this.timer.pause();
//    button.setY(button.getY() + 1);
  }
}
