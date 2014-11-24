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

public class Start extends Map
{
  private Plane floor;

  private RectangularPrism leftWall;
  
  private RectangularPrism backWall;

  private RectangularPrism rightWall;

  
  public Start(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    super(x, y, z, textureName, orientation, trap);
    
    int size = Scale.MAP_SCALE.scale();

    switch (orientation)
    {
      case NORTH:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size);
        backWall = new RectangularPrism(x, y + 1.5f, z + -(size / 2), textureName, size - 2, 3.0f, 1.0f);
        rightWall = new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size);
        break;
      }
      case EAST:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x, y + 1.5f, z + -(size / 2), textureName, size, 3.0f, 1.0f);
        backWall = new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size - 2);
        rightWall = new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size, 3.0f, 1.0f);
        break;
      }
      case SOUTH:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x + -(size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size);
        backWall = new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size - 2, 3.0f, 1.0f);
        rightWall = new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size);
        break;
      }
      case WEST:
      {
        floor = new Plane(x, y, z, textureName, size);
        leftWall = new RectangularPrism(x, y + 1.5f, z + (size / 2), textureName, size, 3.0f, 1.0f);
        backWall = new RectangularPrism(x + (size / 2), y + 1.5f, z, textureName, 1.0f, 3.0f, size - 2);
        rightWall = new RectangularPrism(x, y + 1.5f, z + -(size / 2), textureName, size, 3.0f, 1.0f);
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
      rightWall.draw();
    }
    glEndList();
  }
}
