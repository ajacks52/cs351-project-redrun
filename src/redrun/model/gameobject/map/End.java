package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * This particular class is the ending of the map that denotes where runners
 * must get to in order to win.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 */
public class End extends MapObject
{
  /**
   * Creates a new ending at the specified location. If the texture name is not null, the specified
   * texture will be applied to the ending. In addition the orientation of the ending and the trap
   * associated with it are specified here. Trap may be null.
   * 
   * @param x the x position of the ending
   * @param y the y position of the ending
   * @param z the z position of the ending
   * @param textureName the name of the texture to apply to the ending
   * @param orientation the cardinal direction that ending should be aligned to
   * @param trap the trap to place on the ending
   */
  public End(float x, float y, float z, String textureName, Direction orientation, TrapType type)
  {
    super(x, y, z, textureName, orientation, type);
    
    int size = Scale.MAP_SCALE.scale();

    switch (orientation)
    {
      case NORTH:
      {
        components.add(new Plane(x, y, z, textureName, Direction.NORTH, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size - 2));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), textureName, size, 3.0f, 1.0f));      
        break;
      }
      case EAST:
      {
        components.add(new Plane(x, y, z, textureName, Direction.EAST, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size - 2, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size));
        break;
      }
      case SOUTH:
      {
        components.add(new Plane(x, y, z, textureName, Direction.SOUTH, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), textureName, size, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size - 2));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size, 3.0f, 1.0f));
        break;
      }
      case WEST:
      {
        components.add(new Plane(x, y, z, textureName, Direction.WEST, size));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), textureName, size - 2, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size));
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
  }

  @Override
  public int compareTo(MapObject o)
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
