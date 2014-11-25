package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.trap.Trap;
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
public class Field extends MapObject
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
  public Field(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    super(x, y, z, textureName, orientation, trap);
    
    int size = Scale.MAP_SCALE.scale() * 3;

    switch (orientation)
    {
      case NORTH:
      {
        components.add(new Plane(x, y, z, textureName, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size - 2.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size - 2.0f));
        components.add(new RectangularPrism(x + (size / 3), y + 1.5f, z + (size / 2), textureName, size / 3.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x - (size / 3), y + 1.5f, z + (size / 2), textureName, size / 3.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 3), y + 1.5f, z - (size / 2), textureName, size / 3.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x - (size / 3), y + 1.5f, z - (size / 2), textureName, size / 3.0f, 3.0f, 1.0f));
        break;
      }
      case EAST:
      {
        components.add(new Plane(x, y, z, textureName, size));
        components.add(new RectangularPrism(x, y + 1.5f, z - (size / 2), textureName, size - 2.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size - 2.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z + (size / 3), textureName, 1.0f, 3.0f, size / 3.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z - (size / 3), textureName, 1.0f, 3.0f, size / 3.0f));
        components.add(new RectangularPrism(x - (size / 2), y + 1.5f, z + (size / 3), textureName, 1.0f, 3.0f, size / 3.0f));
        components.add(new RectangularPrism(x - (size / 2), y + 1.5f, z - (size / 3), textureName, 1.0f, 3.0f, size / 3.0f));
        break;
      }
      case SOUTH:
      {
        components.add(new Plane(x, y, z, textureName, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size - 2.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size - 2.0f));
        components.add(new RectangularPrism(x + (size / 3), y + 1.5f, z + (size / 2), textureName, size / 3.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x - (size / 3), y + 1.5f, z + (size / 2), textureName, size / 3.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 3), y + 1.5f, z - (size / 2), textureName, size / 3.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x - (size / 3), y + 1.5f, z - (size / 2), textureName, size / 3.0f, 3.0f, 1.0f));
        break;
      }
      case WEST:
      {
        components.add(new Plane(x, y, z, textureName, size));
        components.add(new RectangularPrism(x, y + 1.5f, z - (size / 2), textureName, size - 2.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size - 2.0f, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z + (size / 3), textureName, 1.0f, 3.0f, size / 3.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z - (size / 3), textureName, 1.0f, 3.0f, size / 3.0f));
        components.add(new RectangularPrism(x - (size / 2), y + 1.5f, z + (size / 3), textureName, 1.0f, 3.0f, size / 3.0f));
        components.add(new RectangularPrism(x - (size / 2), y + 1.5f, z - (size / 3), textureName, 1.0f, 3.0f, size / 3.0f));
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
