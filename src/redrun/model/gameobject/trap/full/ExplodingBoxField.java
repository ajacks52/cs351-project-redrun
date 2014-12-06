package redrun.model.gameobject.trap.full;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import redrun.model.constants.Constants;
import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.ExplosiveBox;
import redrun.model.gameobject.trap.piece.NonExplosiveBox;
import redrun.model.gameobject.world.Cube;

public class ExplodingBoxField extends Trap
{

  public ExplodingBoxField(float x, float y, float z, Direction orientation, String textureName, boolean low)
  {
    super(x, y, z, orientation,textureName);

    int row = Constants.random.nextInt(4);

    if (orientation == Direction.EAST || orientation == Direction.WEST)
    {
      for (int i = -2; i < 3; i++)
      {
        if (row == 0)
        {
          ExplosiveBox box1 = new ExplosiveBox(x + 4, y - 4, z + 2 * i, null, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x + 1, y - 4, z + 2 * i, null, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x - 2, y - 4, z + 2 * i, null, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x - 5, y - 4, z + 2 * i, null, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 1)
        {
          ExplosiveBox box1 = new ExplosiveBox(x + 1, y - 4, z + 2 * i,null, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x - 5, y - 4, z + 2 * i,null, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x + 4, y - 4, z + 2 * i,null, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x - 2, y - 4, z + 2 * i, null, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 2)
        {
          ExplosiveBox box1 = new ExplosiveBox(x - 5, y - 4, z + 2 * i, null, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x + 1, y - 4, z + 2 * i, null, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x - 2, y - 4, z + 2 * i, null, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x + 4, y - 4, z + 2 * i, null, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 3)
        {
          ExplosiveBox box1 = new ExplosiveBox(x - 2, y - 4, z + 2 * i, null, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x + 1, y - 4, z + 2 * i, null, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x + 4, y - 4, z + 2 * i, null, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x - 5, y - 4, z + 2 * i, null, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
      }
    }
    if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
    {
      for (int i = -2; i < 3; i++)
      {
        if (row == 0)
        {
          ExplosiveBox box1 = new ExplosiveBox(x+2*i, y - 4, z + 4, null, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x+2*i, y - 4, z + 1,null, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x+2*i, y - 4, z - 2,null, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x+2*i, y - 4, z - 5,null, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 1)
        {
          ExplosiveBox box1 = new ExplosiveBox(x+2*i, y - 4, z + 1, null, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x+2*i, y - 4, z - 5, null, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x+2*i, y - 4, z - 2, null, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x+2*i, y - 4, z + 4, null, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 2)
        {
          ExplosiveBox box1 = new ExplosiveBox(x+2*i, y - 4, z - 2, null, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x+2*i, y - 4, z - 5, null, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x+2*i, y - 4, z + 1, null, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x+2*i, y - 4, z + 4, null, "crate1");
          GameData.addGameObject(box1);
          GameData.addGameObject(box2);
          GameData.addGameObject(box3);
          GameData.addGameObject(box4);
        }
        if (row == 3)
        {
          ExplosiveBox box1 = new ExplosiveBox(x+2*i, y - 4, z - 5, null, "crate2");
          NonExplosiveBox box2 = new NonExplosiveBox(x+2*i, y - 4, z - 2, null, "crate1");
          NonExplosiveBox box3 = new NonExplosiveBox(x+2*i, y - 4, z + 4, null, "crate1");
          NonExplosiveBox box4 = new NonExplosiveBox(x+2*i, y - 4, z + 1, null, "crate1");
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
