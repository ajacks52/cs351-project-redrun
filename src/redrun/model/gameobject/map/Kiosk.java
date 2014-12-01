package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.Button;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.RectangularPrism;

public class Kiosk extends MapObject
{
  public Kiosk(float x, float y, float z, String textureName, Direction orientation, TrapType type)
  {
    super(x, y, z, textureName, orientation, type);

    int size = Scale.MAP_SCALE.scale();
        
    switch (orientation)
    {
      case NORTH:
      {
        components.add(new Plane(x, y, z, textureName, Direction.NORTH, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size, 3.0f, 1.0f));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), textureName, size, 3.0f, 1.0f));  
        components.add(new Button(x, y + 3.5f, z + -(size / 2), "button"));  
        break;
      }
      case EAST:
      {
        components.add(new Plane(x, y, z, textureName, Direction.EAST, size));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size));
        break;
      }
      case SOUTH:
      {
        components.add(new Plane(x, y, z, textureName, Direction.SOUTH, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size, 3.0f, 1.0f));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), textureName, size, 3.0f, 1.0f));
        break;
      }
      case WEST:
      {
        components.add(new Plane(x, y, z, textureName, Direction.WEST, size));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size));
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
  public int compareTo(MapObject arg0)
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
