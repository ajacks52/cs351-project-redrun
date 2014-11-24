package redrun.model.gameobject.map;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * This particular class is a corner that turns ninety degrees.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 */
public class Corner extends Map
{
	/** The floor defined by a plane. */
  private Plane floor;

  /** The left wall. */
  private RectangularPrism leftWall;
  
  /** The back wall. */
  private RectangularPrism backWall;

  /** The pylon. */
  private RectangularPrism pylon;

  /**
   * Creates a new corner at the specified location. If the texture name is not null, the specified
   * texture will be applied to the corner. In addition the orientation of the corner and the trap
   * associated with it are specified here. Trap may be null.
   * 
   * @param x the x position of the corner
   * @param y the y position of the corner
   * @param z the z position of the corner
   * @param textureName the name of the texture to apply to the corner
   * @param orientation the cardinal direction that corner should be aligned to
   * @param trap the trap to place on the corner
   */
  public Corner(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    super(x, y, z, textureName, orientation, trap);
    
    int size = Scale.MAP_SCALE.scale();

    switch (orientation)
    {
      case NORTH:
      {        
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size);
        backWall = new RectangularPrism(x + 0.5f, y + 1.5f, z + ((size - 1.0f) / 2.0f), textureName, size - 1.0f, 3.0f, 1.0f);
        pylon = new RectangularPrism(x + (size / 2), y + 1.5f, z + -(size / 2), textureName, 1.0f, 3.0f, 1.0f);
        break;
      }
      case EAST:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size, 3.0f, 1.0f);
        backWall = new RectangularPrism(x + ((size - 1.0f) / 2), y + 1.5f, z - 0.5f, textureName, 1.0f, 3.0f, size - 1.0f);
        pylon = new RectangularPrism(x + -(size / 2), y + 1.5f, z + -(size / 2), textureName, 1.0f, 3.0f, 1.0f);
        break;
      }
      case SOUTH:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size);
        backWall = new RectangularPrism(x - 0.5f, y + 1.5f, z + -((size - 1.0f) / 2), textureName, size - 1.0f, 3.0f, 1.0f);
        pylon = new RectangularPrism(x + -(size / 2), y + 1.5f, z + (size / 2), textureName, 1.0f, 3.0f, 1.0f);
        break;
      }
      case WEST:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size);
        backWall = new RectangularPrism(x + 0.5f, y + 1.5f, z + -((size - 1.0f) / 2), textureName, size - 1.0f, 3.0f, 1.0f);
        pylon = new RectangularPrism(x + (size / 2), y + 1.5f, z + (size / 2), textureName, 1.0f, 3.0f, 1.0f);
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
      floor.draw();
      leftWall.draw();
      backWall.draw();
      pylon.draw();
    }
    glEndList();
  }
}
