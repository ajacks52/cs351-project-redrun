package redrun.model.gameobject.map;

import static org.lwjgl.opengl.GL11.*;
import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.world.RectangularPrism;

public class Pit extends Map
{  
  private RectangularPrism frontWall;
  
  private RectangularPrism backWall;

  private RectangularPrism leftWall;
  
  private RectangularPrism rightWall;
  
  private RectangularPrism bottom;
  
  public Pit(float x, float y, float z, String textureName, Direction orientation, Trap trap)
  {
    super(x, y, z, textureName, orientation, trap);
    
    int size = Scale.MAP_SCALE.scale();
        
    switch (orientation)
    {
      case NORTH:
      {        
        leftWall = new RectangularPrism(x + (size / 2), y - ((size + 3.0f) / 2) + 3.0f, z, textureName, 1.0f, size + 3.0f, size);
        rightWall = new RectangularPrism(x + -(size / 2), y - ((size + 3.0f) / 2) + 3.0f, z, textureName, 1.0f, size + 3.0f, size);
        frontWall = new RectangularPrism(x, y - (size / 2), z + (size / 2), textureName, size - 2.0f, size - 1.0f, 1.0f);
        backWall = new RectangularPrism(x, y - (size / 2), z + -(size / 2), textureName, size - 2.0f, size - 1.0f, 1.0f);
        bottom = new RectangularPrism(x, y - size + 0.5f, z, textureName, size - 2.0f, 1.0f, size);
        break;
      }
      case EAST:
      {
        leftWall = new RectangularPrism(x, y - ((size + 3.0f) / 2) + 3.0f, z + (size / 2), textureName, size, size + 3.0f, 1.0f);
        rightWall = new RectangularPrism(x, y - ((size + 3.0f) / 2) + 3.0f, z + -(size / 2), textureName, size, size + 3.0f, 1.0f);
        frontWall = new RectangularPrism(x + (size / 2), y - (size / 2), z, textureName, 1.0f, size - 1.0f, size - 2.0f);
        backWall = new RectangularPrism(x + -(size / 2), y - (size / 2), z, textureName, 1.0f, size - 1.0f, size - 2.0f);
        bottom = new RectangularPrism(x, y - size + 0.5f, z, textureName, size, 1.0f, size - 2.0f);
        break;
      }
      case SOUTH:
      {
        leftWall = new RectangularPrism(x + (size / 2), y - ((size + 3.0f) / 2) + 3.0f, z, textureName, 1.0f, size + 3.0f, size);
        rightWall = new RectangularPrism(x + -(size / 2), y - ((size + 3.0f) / 2) + 3.0f, z, textureName, 1.0f, size + 3.0f, size);
        frontWall = new RectangularPrism(x, y - (size / 2), z + (size / 2), textureName, size - 2.0f, size - 1.0f, 1.0f);
        backWall = new RectangularPrism(x, y - (size / 2), z + -(size / 2), textureName, size - 2.0f, size - 1.0f, 1.0f);
        bottom = new RectangularPrism(x, y - size + 0.5f, z, textureName, size - 2.0f, 1.0f, size);
        break;
      }
      case WEST:
      {
        leftWall = new RectangularPrism(x, y - ((size + 3.0f) / 2) + 3.0f, z + (size / 2), textureName, size, size + 3.0f, 1.0f);
        rightWall = new RectangularPrism(x, y - ((size + 3.0f) / 2) + 3.0f, z + -(size / 2), textureName, size, size + 3.0f, 1.0f);
        frontWall = new RectangularPrism(x + (size / 2), y - (size / 2), z, textureName, 1.0f, size - 1.0f, size - 2.0f);
        backWall = new RectangularPrism(x + -(size / 2), y - (size / 2), z, textureName, 1.0f, size - 1.0f, size - 2.0f);
        bottom = new RectangularPrism(x, y - size + 0.5f, z, textureName, size, 1.0f, size - 2.0f);
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
      frontWall.draw();
      backWall.draw();
      leftWall.draw();
      rightWall.draw();
      bottom.draw();
    }
    glEndList();
  }
}
