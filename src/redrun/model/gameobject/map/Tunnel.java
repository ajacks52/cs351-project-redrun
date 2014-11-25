package redrun.model.gameobject.map;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * This particular class is a tunnel. Multiple tunnels can be aligned
 * to make hallways.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 */
public class Tunnel extends Map
{
	/** The floor defined by a plane. */
  private Plane floor;
  
  /** The left wall. */
  private RectangularPrism leftWall;
  
  /** The right wall. */
  private RectangularPrism rightWall;
  
  /** The roof. */
  private RectangularPrism roof;
  
  /**
   * Creates a new tunnel at the specified location. If the texture name is not null, the specified
   * texture will be applied to the tunnel. In addition the orientation of the tunnel and the trap
   * associated with it are specified here. Trap may be null.
   * 
   * @param x the x position of the tunnel
   * @param y the y position of the tunnel
   * @param z the z position of the tunnel
   * @param textureName the name of the texture to apply to the tunnel
   * @param orientation the cardinal direction that tunnel should be aligned to
   * @param trap the trap to place on the tunnel
   */
  public Tunnel(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    super(x, y, z, textureName, orientation, trap);
    
    int size = Scale.MAP_SCALE.scale();
        
    switch (orientation)
    {
      case NORTH:
      {   
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + (size / 2), y + (size / 2) + 0.5f, z, textureName, 1.0f, size, size);
        rightWall = new RectangularPrism(x + -(size / 2), y + (size / 2) + 0.5f, z, textureName, 1.0f, size, size);
        roof = new RectangularPrism(x, y + size - 1.0f, z, textureName, size - 2.0f, 1.0f, size);
        break;
      }
      case EAST:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x, y + (size / 2) + 0.5f, z + (size / 2), textureName, size, size, 1.0f);
        rightWall = new RectangularPrism(x, y + (size / 2) + 0.5f, z + -(size / 2), textureName, size, size, 1.0f);
        roof = new RectangularPrism(x, y + size - 0.5f, z, textureName, size, 1.0f, size - 2.0f);
        break;
      }
      case SOUTH:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + (size / 2), y + (size / 2) + 0.5f, z, textureName, 1.0f, size, size);
        rightWall = new RectangularPrism(x + -(size / 2), y + (size / 2) + 0.5f, z, textureName, 1.0f, size, size);
        roof = new RectangularPrism(x, y + size - 1.0f, z, textureName, size - 2.0f, 1.0f, size);
        break;
      }
      case WEST:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x, y + + (size / 2) + 0.5f, z + (size / 2), textureName, size, size, 1.0f);
        rightWall = new RectangularPrism(x, y + + (size / 2) + 0.5f, z + -(size / 2), textureName, size, size, 1.0f);
        roof = new RectangularPrism(x, y + size - 1.0f, z, textureName, size, 1.0f, size - 2.0f);
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
      rightWall.draw();
      roof.draw();
    }
    glEndList();
  }
}
