package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.Sphere;

/**
 * Creates a button object. This button sits on a pedestal and reacts to clicks.
 * 
 * @author JakeNichol
 * @since 11-11-2014
 * @version 1.0
 */
public class Button extends WorldObject
{
  
  public Button(float x, float y, float z)
  {
    super(x, y, z);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      drawButton();
    }
    glEndList();
  }
  
  public void buttonPressed()
  {
    
  }
  
  public void buttonReset()
  {
    
  }

  private void drawButton()
  {
    glTranslatef(this.getX(), this.getY(), this.getZ());
    glEnable(GL_COLOR_MATERIAL);
    glColor3f(1.0f, 0.0f, 0.0f);
    new Sphere().draw(1.1f, 40, 40);
    glColor3f(1.0f, 1.0f, 1.0f);
    glDisable(GL_COLOR_MATERIAL);
    new Cube(this.getX(), this.getY(), this.getZ());
  }
}
