package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;

/**
 * This class represents a checker-board that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class CheckerBoard extends WorldObject
{
  /** The width of the checker-board */
  Dimension dimensions = null;

  /**
   * Creates a new checker-board of the specified size.
   * 
   * @param width the width of the checker-board
   * @param depth the depth of the checker-board
   */
  public CheckerBoard(float x, float y, float z, Dimension dimensions)
  {
    super(x, y, z);
    
    this.dimensions = dimensions;

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        glNormal3f(0.0f, 1.0f, 0.0f);
        
        for (int width = 0; width < dimensions.width - 1; width++)
        {
          for (int height = 0; height < dimensions.height - 1; height++)
          {
            if ((width + height) % 2 == 0)
            {
              glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            }
            else
            {
              glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            
            glVertex3d(width, -1.0f, height);
            glVertex3d(width + 1, -1.0f, height);
            glVertex3d(width + 1, -1.0f, height + 1);
            glVertex3d(width, -1.0f, height + 1);
          }
        }
      }
      glEnd();
    }
    glEndList();
  }

  /**
   * Gets the center X position of the checker-board.
   * 
   * @return the center X position of the checker-board
   */
  public float getCenterX()
  {
    return (float) dimensions.width / 2;
  }

  /**
   * Gets the center Z position of the checker-board.
   * 
   * @return the center Z position of the checker-board
   */
  public float getCenterZ()
  {
    return (float) dimensions.height / 2;
  }

  @Override
  public void interact()
  {
    System.out.println("Interacting with the game object: " + this.id);
  }
}
