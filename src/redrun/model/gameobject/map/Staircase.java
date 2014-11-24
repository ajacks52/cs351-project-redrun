package redrun.model.gameobject.map;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * This particular class is a staircase. Multiple staircases can be aligned
 * to make longer staircases.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 */
public class Staircase extends Map
{  
	/** The left wall. */
  private RectangularPrism leftWall;
  
  /** The right wall. */
  private RectangularPrism rightWall;
  
  /** The steps. */
  private RectangularPrism[] stairs;
  
  /**
   * Creates a new staircase at the specified location. If the texture name is not null, the specified
   * texture will be applied to the staircase. In addition the orientation of the staircase and the trap
   * associated with it are specified here. Trap may be null.
   * 
   * @param x the x position of the staircase
   * @param y the y position of the staircase
   * @param z the z position of the staircase
   * @param textureName the name of the texture to apply to the staircase
   * @param orientation the cardinal direction that staircase should be aligned to
   * @param trap the trap to place on the staircase
   */
  public Staircase(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    super(x, y, z, textureName, orientation, trap);
    
    int size = Scale.MAP_SCALE.scale();
    
    stairs = new RectangularPrism[size];
        
    switch (orientation)
    {
      case NORTH:
      {
        leftWall = new RectangularPrism(x + (size / 2), y + (size / 2), z, textureName, 1.0f, size, size);
        rightWall = new RectangularPrism(x + -(size / 2), y + (size / 2), z, textureName, 1.0f, size, size);
        for (int i = 0; i < stairs.length; i++)
        {
          stairs[i] = new RectangularPrism(x, (y - (size / 2)) + i + (size / 2), (z - (size / 2)) + i, textureName, size - 2, 1.0f, 1.0f);
        }        
        break;
      }
      case EAST:
      {
        leftWall = new RectangularPrism(x, y + (size / 2) + 0.5f, z + (size / 2), textureName, size, size, 1.0f);
        rightWall = new RectangularPrism(x, y + (size / 2) + 0.5f, z + -(size / 2), textureName, size, size, 1.0f);
        for (int i = 0; i < stairs.length; i++)
        {
          stairs[i] = new RectangularPrism((x - (size / 2)) + i, (y - (size / 2)) + i + (size / 2) + 0.5f, z, textureName, 1.0f, 1.0f, size - 2);
        }  
        break;
      }
      case SOUTH:
      {
        leftWall = new RectangularPrism(x + (size / 2), y + (size / 2), z, textureName, 1.0f, size, size);
        rightWall = new RectangularPrism(x + -(size / 2), y + (size / 2), z, textureName, 1.0f, size, size);
        for (int i = 0; i < stairs.length; i++)
        {
          stairs[i] = new RectangularPrism(x, (y - (size / 2)) + i + (size / 2), (z + (size / 2)) - i, textureName, size - 2, 1.0f, 1.0f);
        } 
        break;
      }
      case WEST:
      {
        leftWall = new RectangularPrism(x, y + (size / 2), z + (size / 2), textureName, size, size, 1.0f);
        rightWall = new RectangularPrism(x, y + (size / 2), z + -(size / 2), textureName, size, size, 1.0f);
        for (int i = 0; i < stairs.length; i++)
        {
          stairs[i] = new RectangularPrism((x + (size / 2)) - i, (y - (size / 2)) + i + (size / 2), z, textureName, 1.0f, 1.0f, size - 2);
        }  
        break;
      }
      default:
      {
        try
        {
          throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException ex)
        {
          ex.printStackTrace();
        }
      }
    }

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      leftWall.draw();
      rightWall.draw();

      for (int i = 0; i < stairs.length; i++)
      {
        stairs[i].draw();
      }
    }
    glEndList();
  }
}
