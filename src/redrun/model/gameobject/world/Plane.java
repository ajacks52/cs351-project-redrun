package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

/**
 * This class represents a cube that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class Plane extends WorldObject
{
  /**
   * Creates a new cube at the specified position.
   * 
   * @param x the initial x position
   * @param y the initial y position
   * @param z the initial z position
   */
  public Plane(float x, float y, float z, String textureName, float size)
  {
    super(x, y, z, textureName);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        glNormal3f(0.0f, -1.0f, 0.0f);
        glColor3f(1.0f, 0.5f, 0.0f);
        glVertex3f(size / 2, 0.0f, size / 2);
        glTexCoord2f(0, 0);
        glVertex3f(-size / 2, 0.0f, size / 2);
        glTexCoord2f(0, 1);
        glVertex3f(-size / 2, 0.0f, -size / 2);
        glTexCoord2f(1, 1);
        glVertex3f(size / 2, 0.0f, -size / 2);
        glTexCoord2f(1, 0);
      }
      glEnd();
    }
    glEndList();
  }

  @Override
  public void interact()
  {
    System.out.println("Interacting with the game object: " + this.id);
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