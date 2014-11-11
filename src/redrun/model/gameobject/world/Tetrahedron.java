package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

/**
 * This class represents a tetrahedron that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class Tetrahedron extends WorldObject
{
  /**
   * Creates a new tetrahedron at the specified position.
   * 
   * @param x the initial x position
   * @param y the initial y position
   * @param z the initial z position
   */
  public Tetrahedron(float x, float y, float z)
  {
    super(x, y, z);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_TRIANGLES);
      {
        // Front triangle...
        glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        glTexCoord2f(0.5f, 1.0f); glVertex3f(0.0f, 1.0f, 0.0f); glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.25f, 0.5f); glVertex3f( -1.0f, -1.0f, 0.0f); glNormal3f( -1.0f, -1.0f, 0.0f);
        glTexCoord2f(0.75f, 0.5f); glVertex3f( 1.0f,  -1.0f, 0.0f); glNormal3f( 1.0f,  -1.0f, 0.0f);

        // Right triangle...
        glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f); glVertex3f( 1.0f,  -1.0f, 0.0f); glNormal3f( 1.0f,  -1.0f, 0.0f);
        glTexCoord2f(0.5f, 0.0f); glVertex3f(0.0f, 1.0f, 0.0f); glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.25f, 0.5f); glVertex3f( 0.0f,  -1.0f, -1.0f); glNormal3f( 0.0f,  -1.0f, -1.0f);

        // Left triangle...
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        glTexCoord2f(0.5f, 0.0f); glVertex3f( -1.0f, -1.0f, 0.0f); glNormal3f( -1.0f, -1.0f, 0.0f);
        glTexCoord2f(1.0f, 0.0f); glVertex3f(0.0f, 1.0f, 0.0f); glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.75f, 0.5f); glVertex3f( 0.0f,  -1.0f, -1.0f); glNormal3f( 0.0f,  -1.0f, -1.0f);

        // Bottom triangle...
        glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        glTexCoord2f(0.25f, 0.5f); glVertex3f( -1.0f, -1.0f, 0.0f); glNormal3f( -1.0f, -1.0f, 0.0f);
        glTexCoord2f(0.75f, 0.5f); glVertex3f( 1.0f,  -1.0f, 0.0f); glNormal3f( 1.0f,  -1.0f, 0.0f);
        glTexCoord2f(0.5f, 0.0f); glVertex3f( 0.0f,  -1.0f, -1.0f); glNormal3f( 0.0f,  -1.0f, -1.0f);
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
}