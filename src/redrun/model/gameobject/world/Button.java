package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.Sphere;
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
  private boolean isPressed = false;

  private Cube pedestal;
  private Ball button;

  public Button(float x, float y, float z)
  {
    super(x, y, z);

    pedestal = new Cube(x, y, z);
    button = new Ball(x, y, z, 0.5f, new Vector3f(1.0f, 0.0f, 0.0f));

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      pedestal.draw();
      button.draw();
    }
    glEndList();
  }

  public void reset()
  {
    button.setY(button.getY() + 1);
  }

  @Override
  public void interact()
  {
    isPressed = true;
    button.setY(button.getY() - 1);
  }

  public boolean isPressed()
  {
    return this.isPressed;
  }

  // public void pressed()
  // {
  // isPressed = true;
  // }

  // public void update()
  // {
  // drawCube();
  // drawSphere();
  // }
  //
  // private void drawCube()
  // {
  // glPushMatrix();
  // glTranslatef(this.getX(), this.getY(), this.getZ());
  // new Cube(this.getX(), this.getY(), this.getZ()).draw();
  // glPopMatrix();
  // }
  //
  // private void drawSphere()
  // {
  // if (!isPressed)
  // {
  // System.out.println("button not pressed");
  // glPushMatrix();
  // glTranslatef(this.getX(), this.getY() + 1, this.getZ());
  // new Ball(this.getX(), this.getY(), this.getZ(), 0.5f, new Vector3f(1.0f,
  // 0.0f, 0.0f)).draw();
  // glPopMatrix();
  // }
  // else
  // {
  // System.out.println("button pressed");
  // glPushMatrix();
  // glTranslatef(this.getX(), this.getY() + 1, this.getZ());
  // new Ball(this.getX(), this.getY() - 1f, this.getZ(), 0.5f, new
  // Vector3f(1.0f, 0.0f, 0.0f)).draw();
  // glPopMatrix();
  // }
  // }
}
