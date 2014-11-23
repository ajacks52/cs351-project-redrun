package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

/**
 * This class represents a rectanguler prism that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class RectangularPrism extends WorldObject
{
  /**
   * Creates a new cube at the specified position.
   * 
   * @param x the initial x position
   * @param y the initial y position
   * @param z the initial z position
   */
  public RectangularPrism(float x, float y, float z, String textureName, float width, float height, float depth)
  {
    super(x, y, z, textureName);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        // Top face...
        glNormal3f(0.0f, height / 2, 0.0f);
        glColor3f(0.0f, 1.0f, 0.0f);
        glVertex3f(width / 2, height / 2, -depth / 2);
        glTexCoord2f(0, 0);
        glVertex3f(-width / 2, height / 2, -depth / 2);
        glTexCoord2f(0, 1);
        glVertex3f(-width / 2, height / 2, depth / 2);
        glTexCoord2f(1, 1);
        glVertex3f(width / 2, height / 2, depth / 2);
        glTexCoord2f(1, 0);

        // Bottom face...
        glNormal3f(0.0f, -height / 2, 0.0f);
        glColor3f(1.0f, 0.5f, 0.0f);
        glVertex3f(width / 2, -height / 2, depth / 2);
        glTexCoord2f(0, 0);
        glVertex3f(-width / 2, -height / 2, depth / 2);
        glTexCoord2f(0, 1);
        glVertex3f(-width / 2, -height / 2, -depth / 2);
        glTexCoord2f(1, 1);
        glVertex3f(width / 2, -height / 2, -depth / 2);
        glTexCoord2f(1, 0);

        // Front face...
        glNormal3f(0.0f, 0.0f, -1.0f);
        glColor3f(1.0f, 1.0f, 0.0f);
        glVertex3f(width / 2, -height / 2, -depth / 2);
        glTexCoord2f(0, 0);
        glVertex3f(-width / 2, -height / 2, -depth / 2);
        glTexCoord2f(0, 1);
        glVertex3f(-width / 2, height / 2, -depth / 2);
        glTexCoord2f(1, 1);
        glVertex3f(width / 2, height / 2, -depth / 2);
        glTexCoord2f(1, 0);

        // Back face...
        glNormal3f(0.0f, 0.0f, 1.0f);
        glColor3f(1.0f, 0.0f, 0.0f);
        glVertex3f(width / 2, -height / 2, depth / 2);
        glTexCoord2f(0, 0);
        glVertex3f(-width / 2, -height / 2, depth / 2);
        glTexCoord2f(0, 1);
        glVertex3f(-width / 2, height / 2, depth / 2);
        glTexCoord2f(1, 1);
        glVertex3f(width / 2, height / 2, depth / 2);
        glTexCoord2f(1, 0);

        // Left face...
        glNormal3f(-1.0f, 0.0f, 0.0f);
        glColor3f(0.0f, 0.0f, 1.0f);
        glVertex3f(-width / 2, height / 2, depth / 2);
        glTexCoord2f(0, 0);
        glVertex3f(-width / 2, height / 2, -depth / 2);
        glTexCoord2f(0, 1);
        glVertex3f(-width / 2, -height / 2, -depth / 2);
        glTexCoord2f(1, 1);
        glVertex3f(-width / 2, -height / 2, depth / 2);
        glTexCoord2f(1, 0);

        // Right face...
        glNormal3f(1.0f, 0.0f, 0.0f);
        glColor3f(1.0f, 0.0f, 1.0f);
        glVertex3f(width / 2, height / 2, -depth / 2);
        glTexCoord2f(0, 0);
        glVertex3f(width / 2, height / 2, depth / 2);
        glTexCoord2f(0, 1);
        glVertex3f(width / 2, -height / 2, depth / 2);
        glTexCoord2f(1, 1);
        glVertex3f(width / 2, -height / 2, -depth / 2);
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