package redrun.model.gameobject.trap;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import redrun.model.constants.Constants;
import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.world.Cube;

public class ExplodingBoxField extends Trap
{

  public ExplodingBoxField(float x, float y, float z, Direction dir, String textureName, boolean low)
  {
    super(x, y, z, textureName);

    int row = Constants.random.nextInt(4);

    if (dir == Direction.EAST || dir == Direction.WEST)
    {
      for (int i = -2; i < 3; i++)
      {
        if (row == 0)
        {
          ExplosiveBox box1 = new ExplosiveBox(x + 4, y - 4, z + 2 * i, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x + 1, y - 4, z + 2 * i, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x - 2, y - 4, z + 2 * i, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x - 5, y - 4, z + 2 * i, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 1)
        {
          ExplosiveBox box1 = new ExplosiveBox(x + 1, y - 4, z + 2 * i, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x - 5, y - 4, z + 2 * i, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x + 4, y - 4, z + 2 * i, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x - 2, y - 4, z + 2 * i, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 2)
        {
          ExplosiveBox box1 = new ExplosiveBox(x - 5, y - 4, z + 2 * i, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x + 1, y - 4, z + 2 * i, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x - 2, y - 4, z + 2 * i, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x + 4, y - 4, z + 2 * i, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 3)
        {
          ExplosiveBox box1 = new ExplosiveBox(x - 2, y - 4, z + 2 * i, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x + 1, y - 4, z + 2 * i, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x + 4, y - 4, z + 2 * i, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x - 5, y - 4, z + 2 * i, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
      }
    }
    if (dir == Direction.NORTH || dir == Direction.SOUTH)
    {
      for (int i = -2; i < 3; i++)
      {
        if (row == 0)
        {
          ExplosiveBox box1 = new ExplosiveBox(x+2*i, y - 4, z + 4, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x+2*i, y - 4, z + 1, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x+2*i, y - 4, z - 2, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x+2*i, y - 4, z - 5, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 1)
        {
          ExplosiveBox box1 = new ExplosiveBox(x+2*i, y - 4, z + 1, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x+2*i, y - 4, z - 5, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x+2*i, y - 4, z - 2, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x+2*i, y - 4, z + 4, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 2)
        {
          ExplosiveBox box1 = new ExplosiveBox(x+2*i, y - 4, z - 2, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x+2*i, y - 4, z - 5, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x+2*i, y - 4, z + 1, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x+2*i, y - 4, z + 4, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 3)
        {
          ExplosiveBox box1 = new ExplosiveBox(x+2*i, y - 4, z - 5, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x+2*i, y - 4, z - 2, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x+2*i, y - 4, z + 4, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x+2*i, y - 4, z + 1, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
      }
    }
    // displayListId = glGenLists(1);
    // glNewList(displayListId, GL_COMPILE);
    // {
    //
    // }

  }

  @Override
  public void activate()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void interact()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void update()
  {
    // TODO Auto-generated method stub

  }

}
