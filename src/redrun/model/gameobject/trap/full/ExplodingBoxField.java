package redrun.model.gameobject.trap.full;

import java.util.ArrayList;

import redrun.model.constants.Constants;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.ExplosiveBox;
import redrun.model.gameobject.trap.piece.NonExplosiveBox;
import redrun.model.physics.PhysicsWorld;

public class ExplodingBoxField extends Trap
{

  private ArrayList<ExplosiveBox> explosiveBoxes = new ArrayList<ExplosiveBox>(220);
  int size = 0;
  int pit_height = 1;

  public ExplodingBoxField(float x, float y, float z, String textureName, String type)
  {
    super(x, y, z, null, textureName);

    if (type.equals("pit"))
    {
      size = 3;
      pit_height = 7;
    }
    else if (type.equals("corridor"))
    {
      size = 3;
    }
    else if (type.equals("field"))
    {
      size = 10;
    }

    for (int y_axis = 0; y_axis < pit_height; y_axis++)
    {
      for (int x_axis = -size; x_axis < size; x_axis++)
      {
        for (int z_axis = -size; z_axis < size; z_axis++)
        {
          int rand = Constants.random.nextInt(3);
          if (rand == 0)
          {
            ExplosiveBox box1 = new ExplosiveBox(x + 2 * x_axis, y + 2 * y_axis, z + 2 * z_axis, null, "crate1", this);
            explosiveBoxes.add(box1);
            GameData.addGameObject(box1);
          }
          else
          {
            NonExplosiveBox box2 = new NonExplosiveBox(x + 2 * x_axis, y + 2 * y_axis, z + 2 * z_axis, null, "crate1");
            GameData.addGameObject(box2);
          }
        }
      }
    }
  }

  public void explode()
  {
    for (ExplosiveBox box : explosiveBoxes)
    {
      PhysicsWorld.removePhysicsBody(box.getBody());
      box.explode();
    }
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
