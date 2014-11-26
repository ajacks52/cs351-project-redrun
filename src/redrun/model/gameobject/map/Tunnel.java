package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.gameobject.MapObject;
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
public class Tunnel extends MapObject
{
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
        components.add(new Plane(x, y, z, textureName, size));
        components.add(new RectangularPrism(x, y + (size / 2) + 0.5f, z + (size / 2), textureName, size, size, 1.0f));
        components.add(new RectangularPrism(x, y + (size / 2) + 0.5f, z + -(size / 2), textureName, size, size, 1.0f));
        components.add(new RectangularPrism(x, y + size - 0.5f, z, textureName, size, 1.0f, size - 2.0f));
        break;
      }
      case EAST:
      {
        components.add(new Plane(x, y, z, textureName, size));
        components.add(new RectangularPrism(x + (size / 2), y + (size / 2) + 0.5f, z, textureName, 1.0f, size, size));
        components.add(new RectangularPrism(x + -(size / 2), y + (size / 2) + 0.5f, z, textureName, 1.0f, size, size));
        components.add(new RectangularPrism(x, y + size - 0.5f, z, textureName, size - 2.0f, 1.0f, size));        
        break;
      }
      case SOUTH:
      {
        components.add(new Plane(x, y, z, textureName, size));
        components.add(new RectangularPrism(x, y + + (size / 2) + 0.5f, z + (size / 2), textureName, size, size, 1.0f));
        components.add(new RectangularPrism(x, y + + (size / 2) + 0.5f, z + -(size / 2), textureName, size, size, 1.0f));
        components.add(new RectangularPrism(x, y + size - 0.5f, z, textureName, size, 1.0f, size - 2.0f));
        break;
      }
      case WEST:
      {
        components.add(new Plane(x, y, z, textureName, size));
        components.add(new RectangularPrism(x + (size / 2), y + (size / 2) + 0.5f, z, textureName, 1.0f, size, size));
        components.add(new RectangularPrism(x + -(size / 2), y + (size / 2) + 0.5f, z, textureName, 1.0f, size, size));
        components.add(new RectangularPrism(x, y + size - 0.5f, z, textureName, size - 2.0f, 1.0f, size));        
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
