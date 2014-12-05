package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.RectangularPrism;

public class Platform extends MapObject
{
  public Platform(float x, float y, float z, String textureName, Direction orientation, TrapType type)
  {
    super(x, y, z, textureName, orientation, type);

    int size = Scale.MAP_SCALE.scale();

    components.add(new RectangularPrism(x, y, z, textureName, size - 3.0f, 1.0f, size - 3.0f));
  }

  @Override
  public int compareTo(MapObject o)
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
