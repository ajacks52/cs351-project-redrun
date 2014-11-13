package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

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
  private Ball button;

  public Button(float x, float y, float z)
  {
    super(x, y, z);
    defaultButtonPosition = new Vector3f(x, y + 0.8f, z);

    button = new Ball(x, y + 0.8f, z, 0.5f, new Vector3f(1.0f, 0.0f, 0.0f));
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
    if (this.timer.getTime() >= 0.1f) reset();

    if (timer.isPaused() && button.getY() < defaultButtonPosition.y) button.setY(button.getY() + 0.02f);
    else if (!timer.isPaused()) button.setY(button.getY() - 0.02f);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glPushMatrix();
      {
        glTranslatef(button.getX(), button.getY(), button.getZ());
        button.draw();
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
