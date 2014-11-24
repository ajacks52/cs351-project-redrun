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

public class Corner extends Map
{
  private Plane floor;

  private RectangularPrism leftWall;
  
  private RectangularPrism backWall;

  private RectangularPrism pylon;

  
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
