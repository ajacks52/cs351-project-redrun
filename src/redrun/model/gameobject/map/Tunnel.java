package redrun.model.gameobject.map;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.RectangularPrism;

public class Tunnel extends Map
{
  private Plane floor;
  
  private RectangularPrism leftWall;
  
  private RectangularPrism rightWall;
  
  private RectangularPrism roof;
  
  public Tunnel(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    super(x, y, z, textureName, orientation, trap);
    
    int size = Scale.MAP_SCALE.scale();
        
    switch (orientation)
    {
      case NORTH:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + (size / 2), y + 5.0f, z, textureName, 1.0f, 10.0f, size);
        rightWall = new RectangularPrism(x + -(size / 2), y + 5.0f, z, textureName, 1.0f, 10.0f, size);
        roof = new RectangularPrism(x, y + 10.5f, z, textureName, size, 1.0f, size);
        break;
      }
      case EAST:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x, y + 5.0f, z + (size / 2), textureName, size, 10.0f, 1.0f);
        rightWall = new RectangularPrism(x, y + 5.0f, z + -(size / 2), textureName, size, 10.0f, 1.0f);
        roof = new RectangularPrism(x, y + 10.5f, z, textureName, size, 1.0f, size);
        break;
      }
      case SOUTH:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + (size / 2), y + 5.0f, z, textureName, 1.0f, 10.0f, size);
        rightWall = new RectangularPrism(x + -(size / 2), y + 5.0f, z, textureName, 1.0f, 10.0f, size);
        roof = new RectangularPrism(x, y + 10.5f, z, textureName, size, 1.0f, size);
        break;
      }
      case WEST:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x, y + 5.0f, z + (size / 2), textureName, size, 10.0f, 1.0f);
        rightWall = new RectangularPrism(x, y + 5.0f, z + -(size / 2), textureName, size, 10.0f, 1.0f);
        roof = new RectangularPrism(x, y + 10.5f, z, textureName, size, 1.0f, size);
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
